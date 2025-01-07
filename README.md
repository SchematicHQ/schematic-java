# Schematic Java Library

The official Schematic Java library, supporting Java 8+.

## Installation and Setup

1. Add the dependency using your build tool of choice:

Using Gradle in `build.gradle`:
```groovy
dependencies {
    implementation 'com.schematichq:schematic-java:0.x.x'
}
```

Using Maven in `pom.xml`:
```xml
<dependency>
    <groupId>com.schematichq</groupId>
    <artifactId>schematic-java</artifactId>
    <version>0.x.x</version>
</dependency>
```

2. [Issue an API key](https://docs.schematichq.com/quickstart#create-an-api-key) for the appropriate environment using the [Schematic app](https://app.schematichq.com/settings/api-keys).

3. Using this secret key, initialize a client in your application:

```java
import com.schematic.api.Schematic;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .build();
```

## Usage

### Sending identify events

Create or update users and companies using identify events.

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.Schematic;
import com.schematic.api.core.ObjectMappers;
import java.util.HashMap;
import java.util.Map;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .build();

Map<String, String> keys = new HashMap<>();
keys.put("email", "wcoyote@acme.net");
keys.put("user_id", "your-user-id");

EventBodyIdentifyCompany company = EventBodyIdentifyCompany.builder()
    .name("Acme Widgets, Inc.")
    .build();

Map<String, JsonNode> traits = new HashMap<>();
traits.put("city", ObjectMappers.JSON_MAPPER.valueToTree("Atlanta"));
traits.put("high_score", ObjectMappers.JSON_MAPPER.valueToTree(25));
traits.put("is_active", ObjectMappers.JSON_MAPPER.valueToTree(true));

schematic.identify(keys, company, "Wile E. Coyote", traits);
```

This call is non-blocking and there is no response to check.

### Sending track events

Track activity in your application using track events; these events can later be used to produce metrics for targeting.

```java
import com.schematic.api.Schematic;
import java.util.HashMap;
import java.util.Map;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .build();

Map<String, String> company = new HashMap<>();
company.put("id", "your-company-id");

Map<String, String> user = new HashMap<>();
user.put("user_id", "your-user-id");

Map<String, Object> traits = new HashMap<>();

schematic.track("some-action", company, user, traits);
```

This call is non-blocking and there is no response to check.

### Creating and updating companies

Although it is faster to create companies and users via identify events, if you need to handle a response, you can use the companies API to upsert companies. Because you use your own identifiers to identify companies, rather than a Schematic company ID, creating and updating companies are both done via the same upsert operation:

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.schematic.api.Schematic;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.types.UpsertCompanyRequestBody;
import java.util.HashMap;
import java.util.Map;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .build();

Map<String, String> keys = new HashMap<>();
keys.put("id", "your-company-id");

Map<String, JsonNode> traits = new HashMap<>();
traits.put("city", ObjectMappers.JSON_MAPPER.valueToTree("Atlanta"));
traits.put("high_score", ObjectMappers.JSON_MAPPER.valueToTree(25));
traits.put("is_active", ObjectMappers.JSON_MAPPER.valueToTree(true));

UpsertCompanyRequestBody request = UpsertCompanyRequestBody.builder()
    .keys(keys)
    .name("Acme Widgets, Inc.")
    .traits(traits)
    .build();

var response = schematic.companies.upsertCompany(request);
System.out.println("Company upserted: " + response.getData().getName());
```

### Checking flags

When checking a flag, you'll provide keys for a company and/or keys for a user. You can also provide no keys at all, in which case you'll get the default value for the flag.

```java
import com.schematic.api.Schematic;
import java.util.HashMap;
import java.util.Map;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .build();

Map<String, String> company = new HashMap<>();
company.put("id", "your-company-id");

Map<String, String> user = new HashMap<>();
user.put("user_id", "your-user-id");

boolean flagValue = schematic.checkFlag("some-flag-key", company, user);
```

## Configuration Options

There are a number of configuration options that can be specified using the builder when instantiating the Schematic client.

### Flag Check Options

By default, the client will do some local caching for flag checks. If you would like to change this behavior, you can do so using initialization options to specify the cache providers:

```java
import com.schematic.api.Schematic;
import com.schematic.api.cache.LocalCache;
import java.time.Duration;
import java.util.Collections;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .cacheProviders(Collections.singletonList(new LocalCache<>()))
    .build();
```

You can also disable local caching entirely; bear in mind that, in this case, every flag check will result in a network request:

```java
import com.schematic.api.Schematic;
import java.util.Collections;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .cacheProviders(Collections.emptyList())
    .build();
```

You may want to specify default flag values for your application, which will be used if there is a service interruption or if the client is running in offline mode:

```java
import com.schematic.api.Schematic;
import java.util.HashMap;
import java.util.Map;

Map<String, Boolean> flagDefaults = new HashMap<>();
flagDefaults.put("some-flag-key", true);

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .flagDefaults(flagDefaults)
    .build();
```

### Offline Mode

In development or testing environments, you may want to avoid making network requests when checking flags or submitting events. You can run Schematic in offline mode:

```java
import com.schematic.api.Schematic;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .offline(true)
    .build();
```

While in offline mode, flag checks will return the default value for the flag being checked, and events will no-op. Other API calls will still be attempted as normal; offline mode does not affect these.

Offline mode works well with flag defaults:

```java
import com.schematic.api.Schematic;
import java.util.HashMap;
import java.util.Map;

Map<String, Boolean> flagDefaults = new HashMap<>();
flagDefaults.put("some-flag-key", true);

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .offline(true)
    .flagDefaults(flagDefaults)
    .build();

boolean flagValue = schematic.checkFlag("some-flag-key", null, null); // Returns true
```

### Event Buffer
Schematic API uses an Event Buffer to batch Identify and Track requests and avoid multiple API calls. You can set the event buffer flush period:

```java
import com.schematic.api.Schematic;
import java.time.Duration;

Schematic schematic = Schematic.builder()
    .apiKey("YOUR_API_KEY")
    .eventBufferInterval(Duration.ofSeconds(5))
    .build();
```

## Exception Handling

When the API returns a non-success status code (4xx or 5xx response), a subclass of `SchematicException`
will be thrown.

```java
import com.schematic.api.core.SchematicException;

try {
    schematic.companies.getCompany(...);
} catch (SchematicException e) {
    System.out.println(e.message());
}
```

The SDK also supports error handling for first class exceptions with strongly typed body fields.

```java
import com.schematic.api.errors.InvalidRequestError;

try {
    schematic.companies.getCompany(...);
} catch (InvalidRequestError e) {
    System.out.println(e.message());
    System.out.println(e.getBody().getMissingField());
}
```

## Retries

The SDK is instrumented with automatic retries with exponential backoff. A request will be retried as long
as the request is deemed retriable and the number of retry attempts has not grown larger than the configured
retry limit (default: 2).

A request is deemed retriable when any of the following HTTP status codes is returned:

- [408](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/408) (Timeout)
- [429](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/429) (Too Many Requests)
- [5XX](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500) (Internal Server Errors)

## Contributing

While we value open-source contributions to this SDK, this library is generated programmatically.
Additions made directly to this library would have to be moved over to our generation code,
otherwise they would be overwritten upon the next generated release. Feel free to open a PR as
a proof of concept, but know that we will not be able to merge it as-is. We suggest opening
an issue first to discuss with us!

On the other hand, contributions to the README are always very welcome!
