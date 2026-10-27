package com.schematic.contextsig;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Signs the context a browser SDK sends with a publishable key, for environments that require
 * context signatures.
 * <p>
 * A publishable key is public, so the API can't trust the company and user a browser names with
 * it. When the environment requires signatures, your backend, which holds the secret API key, signs
 * the logged-in user's own company and user, and the browser SDK sends that signature in the
 * X-Schematic-Context-Sig header. Sign the context from your own authenticated session, never from
 * what the browser asks for.
 * <p>
 * The signature is {@code iat.exp.sig}, where {@code sig} is the unpadded base64url HMAC-SHA256 of
 * {@code canonical(context).iat.exp} keyed with the secret API key, and {@code canonical} is the
 * context's JSON with keys sorted by code point and no whitespace.
 */
public final class ContextSignature {

    /**
     * Request header the browser SDK sends the signature in.
     */
    public static final String CONTEXT_SIGNATURE_HEADER = "X-Schematic-Context-Sig";

    /**
     * How long a signature is valid for unless {@link Options#lifetimeSeconds(long)} says otherwise.
     * It matches how long the browser SDK reuses one.
     */
    public static final long DEFAULT_LIFETIME_SECONDS = 600;

    /**
     * The longest the API accepts a signature as valid for.
     */
    public static final long MAX_LIFETIME_SECONDS = 3600;

    private static final String HMAC_SHA256 = "HmacSHA256";

    // The API sorts keys by their UTF-8 bytes, which is code point order.
    // String.compareTo compares UTF-16 code units, which puts a character
    // outside the BMP (a surrogate pair) before U+E000 to U+FFFF.
    private static final Comparator<String> CODE_POINT_ORDER = ContextSignature::compareCodePoints;

    private ContextSignature() {}

    /**
     * Options for {@link #sign(String, Map, Map, Options)}.
     */
    public static final class Options {
        private Instant issuedAt;
        private long lifetimeSeconds = DEFAULT_LIFETIME_SECONDS;

        /**
         * Sets when the signature is valid from, in place of now, for a caller that keeps its own
         * clock. It's counted in whole seconds.
         *
         * @param issuedAt when the signature is valid from
         * @return these options
         */
        public Options issuedAt(Instant issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        /**
         * Sets how long the signature is valid for, from 1 up to {@link #MAX_LIFETIME_SECONDS}.
         *
         * @param lifetimeSeconds the lifetime in seconds
         * @return these options
         */
        public Options lifetimeSeconds(long lifetimeSeconds) {
            this.lifetimeSeconds = lifetimeSeconds;
            return this;
        }
    }

    /**
     * Returns new options: valid from now, for {@link #DEFAULT_LIFETIME_SECONDS}.
     *
     * @return the default options
     */
    public static Options options() {
        return new Options();
    }

    /**
     * Returns the X-Schematic-Context-Sig value for a context, valid from now for
     * {@link #DEFAULT_LIFETIME_SECONDS}.
     *
     * @param secret the secret API key for the environment the browser SDK's publishable key
     *     belongs to
     * @param company the logged-in user's company keys, or null to leave the company out
     * @param user the logged-in user's keys, or null to leave the user out
     * @return the signature
     * @throws ContextSignatureException if the secret is missing or a key's value is null
     */
    public static String sign(String secret, Map<String, String> company, Map<String, String> user) {
        return sign(secret, company, user, options());
    }

    /**
     * Returns the X-Schematic-Context-Sig value for a context. The company and user keys must
     * match the context the browser SDK is given exactly; an empty map is signed as empty.
     *
     * @param secret the secret API key for the environment the browser SDK's publishable key
     *     belongs to
     * @param company the logged-in user's company keys, or null to leave the company out
     * @param user the logged-in user's keys, or null to leave the user out
     * @param options when the signature is valid from, and for how long
     * @return the signature
     * @throws ContextSignatureException if the secret is missing, the lifetime is out of range, or
     *     a key's value is null
     */
    public static String sign(String secret, Map<String, String> company, Map<String, String> user, Options options) {
        Options opts = options == null ? options() : options;
        if (opts.lifetimeSeconds < 1 || opts.lifetimeSeconds > MAX_LIFETIME_SECONDS) {
            throw new ContextSignatureException("lifetimeSeconds must be from 1 to " + MAX_LIFETIME_SECONDS + ".");
        }

        Instant issuedAt = opts.issuedAt == null ? Instant.now() : opts.issuedAt;
        long iat = issuedAt.getEpochSecond();
        return signAt(secret, company, user, iat, iat + opts.lifetimeSeconds);
    }

    static String signAt(String secret, Map<String, String> company, Map<String, String> user, long iat, long exp) {
        if (secret == null || secret.isEmpty()) {
            throw new ContextSignatureException("A secret API key is required to sign a context.");
        }

        String payload = canonicalize(company, user) + "." + iat + "." + exp;
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            byte[] digest = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String sig = Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
            return iat + "." + exp + "." + sig;
        } catch (GeneralSecurityException e) {
            throw new ContextSignatureException("Could not compute the context signature.", e);
        }
    }

    /**
     * Encodes a context the way the API does before verifying it: keys sorted by code point, no
     * whitespace, {@code <}, {@code >}, {@code &} and {@code /} left literal, U+2028 and U+2029
     * escaped, and a lone surrogate read as U+FFFD.
     */
    static String canonicalize(Map<String, String> company, Map<String, String> user) {
        List<String> parts = new ArrayList<>(2);
        // "company" sorts before "user".
        if (company != null) {
            parts.add("\"company\":" + canonicalizeKeys(company));
        }
        if (user != null) {
            parts.add("\"user\":" + canonicalizeKeys(user));
        }
        return "{" + String.join(",", parts) + "}";
    }

    private static String canonicalizeKeys(Map<String, String> keys) {
        List<String> names = new ArrayList<>(keys.keySet());
        for (String name : names) {
            if (name == null) {
                throw new ContextSignatureException("Context keys can't be null.");
            }
        }
        names.sort(CODE_POINT_ORDER);

        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String value = keys.get(name);
            if (value == null) {
                throw new ContextSignatureException("The value of context key \"" + name + "\" must be a string.");
            }
            if (i > 0) {
                sb.append(',');
            }
            appendString(sb, name);
            sb.append(':');
            appendString(sb, value);
        }
        return sb.append('}').toString();
    }

    private static int compareCodePoints(String a, String b) {
        int i = 0;
        int j = 0;
        while (i < a.length() && j < b.length()) {
            int ca = a.codePointAt(i);
            int cb = b.codePointAt(j);
            if (ca != cb) {
                return Integer.compare(ca, cb);
            }
            i += Character.charCount(ca);
            j += Character.charCount(cb);
        }
        return Integer.compare(a.length() - i, b.length() - j);
    }

    // Matches Go's encoding/json with HTML escaping off: quotes and
    // backslashes escaped, \b \f \n \r \t by name, other control characters
    // as six-character escapes with lowercase hex, U+2028 and U+2029 escaped,
    // and everything else raw.
    private static void appendString(StringBuilder sb, String value) {
        sb.append('"');
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isHighSurrogate(c)
                    && i + 1 < value.length()
                    && Character.isLowSurrogate(value.charAt(i + 1))) {
                sb.append(c).append(value.charAt(++i));
                continue;
            }
            if (Character.isSurrogate(c)) {
                sb.append('\uFFFD');
                continue;
            }
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\u2028':
                    sb.append("\\u2028");
                    break;
                case '\u2029':
                    sb.append("\\u2029");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }
}
