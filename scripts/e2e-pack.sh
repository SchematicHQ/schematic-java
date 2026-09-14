#!/usr/bin/env bash
set -euo pipefail

# Setup for the SDK E2E "pack" install mode (SDK_SOURCE=pack).
#
# Unlike "local", which wires the sample app straight to the source tree, this
# builds the real publishable artifact from the working tree, publishes it to
# the local Maven repo, checks the rules engine WASM actually made it into the
# jar, and installs the sample app resolving com.schematichq:schematic-java
# from mavenLocal() the way an end user would. A jar that is missing the WASM
# or fails to assemble therefore breaks the build before merge, not after
# release.
#
# Start command afterwards (unchanged):
#   ./sample-app/build/install/sample-app/bin/sample-app

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$REPO_ROOT"

# Deliberately not the real version: keeps an E2E build out of the way of any
# real release sitting in a developer's ~/.m2.
PACK_VERSION="${SCHEMATIC_PACK_VERSION:-0.0.0-e2e}"

INIT_SCRIPT="$SCRIPT_DIR/e2e-pack.init.gradle"
WASM_SOURCE="$REPO_ROOT/src/main/resources/wasm/rulesengine.wasm"
WASM_RESOURCE="wasm/rulesengine.wasm"
M2_DIR="$HOME/.m2/repository/com/schematichq/schematic-java/$PACK_VERSION"
JAR="$M2_DIR/schematic-java-$PACK_VERSION.jar"

if [ -f "$WASM_SOURCE" ]; then
    echo "==> rules engine WASM already present at $WASM_SOURCE, skipping download"
else
    echo "==> downloading rules engine WASM"
    ./scripts/download-wasm.sh
fi

echo "==> publishing com.schematichq:schematic-java:$PACK_VERSION to the local Maven repo"
rm -rf "$M2_DIR"
./gradlew publishToMavenLocal --no-daemon \
    -I "$INIT_SCRIPT" \
    -PschematicVersion="$PACK_VERSION"

if [ ! -f "$JAR" ]; then
    echo "ERROR: expected published jar not found at $JAR" >&2
    ls -la "$M2_DIR" >&2 || true
    exit 1
fi

echo "==> checking $WASM_RESOURCE is inside $JAR"
if ! unzip -l "$JAR" | grep -q "$WASM_RESOURCE"; then
    echo "ERROR: $WASM_RESOURCE is missing from the published jar." >&2
    echo "The rules engine would fail at runtime for anyone consuming this release." >&2
    echo "Jar contents:" >&2
    unzip -l "$JAR" >&2
    exit 1
fi

echo "==> installing sample-app against the published artifact"
./gradlew :sample-app:installDist --no-daemon \
    -PsdkSource=pack \
    -PschematicVersion="$PACK_VERSION"

echo "==> pack setup complete: sample-app resolves com.schematichq:schematic-java:$PACK_VERSION from mavenLocal()"
