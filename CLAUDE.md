# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Test Commands

### Building the Project
```bash
# Build the project
./gradlew build

# Build without running tests
./gradlew build -x test

# Clean and build
./gradlew clean build
```

### Running Tests
```bash
# Run all tests
./gradlew test

# Run a specific test class
./gradlew test --tests com.schematic.api.SchematicTest

# Run a specific test method
./gradlew test --tests com.schematic.api.SchematicTest.checkFlag_HandlesNullData
```

### Code Quality
```bash
# Format code according to project conventions
./gradlew spotlessApply

# Check code formatting without making changes
./gradlew spotlessCheck
```

### Publishing
```bash
# Generate POM file for Maven publication
./gradlew generatePomFileForMavenPublication

# Create sources and javadoc JARs
./gradlew sourcesJar javadocJar
```

## Architecture Overview

This repository contains the official Schematic Java SDK, supporting Java 8+. Schematic is a feature flag and product analytics service.

### Key Components

1. **Schematic Client**
   - Main entry point to the SDK (`Schematic.java`)
   - Builder pattern for configuration (`SchematicBuilder.java`)
   - Provides methods for flag checking, sending identify/track events, etc.

2. **Event Buffer System**
   - Buffers and batches events before sending them to the API
   - Handles automatic retries, thread safety, and resource management
   - Configurable batch size and flush intervals
   - See `EventBuffer.java`

3. **Caching Layer**
   - Provides caching capabilities for flag checking
   - Local memory-based implementation (`LocalCache.java`)
   - Extensible via `CacheProvider` interface

4. **Webhook Verification**
   - Provides utilities for verifying webhook signatures 
   - HMAC-SHA256 based verification
   - See `WebhookVerifier.java`

5. **Resource Clients**
   - Individual clients for different API resources (features, companies, events, etc.)
   - Auto-generated from API definition
   - Generated clients handle serialization/deserialization

### Key Concepts

1. **Feature Flag Checking**
   - Method: `checkFlag(flagKey, company, user)`
   - Supports local caching and offline mode
   - Falls back to configured defaults when API is unavailable

2. **Event Tracking**
   - Non-blocking event processing
   - Events are buffered and sent in batches
   - Two main event types: `identify` and `track`

3. **Error Handling**
   - SDK throws `SchematicException` subclasses for API errors
   - Automatic retries for retriable errors (408, 429, 5xx)

4. **Configuration Options**
   - API key setting
   - Flag defaults
   - Caching configuration
   - Offline mode for testing
   - Event buffer configuration

## Working with the Codebase

### Adding New Features

When adding new features:
1. Check if the feature should be part of the auto-generated code
2. If it's a core infrastructure component, ensure thread safety
3. Add appropriate tests for new functionality
4. Follow the existing code style (enforced by Spotless)

### Testing Strategies

1. Unit tests for SDK components
   - Extensive mocking for external dependencies
   - Tests both success and error paths
   - Special attention to thread safety in concurrent scenarios

2. Test classes follow a pattern:
   - `@ExtendWith(MockitoExtension.class)` for mock support
   - `@BeforeEach` setup methods to create test fixtures
   - `@AfterEach` teardown when needed (especially for resources)

### Note on Code Generation

Most of the resource clients and data types are generated. The repository documentation notes:

> While we value open-source contributions to this SDK, this library is generated programmatically. Additions made directly to this library would have to be moved over to our generation code, otherwise they would be overwritten upon the next generated release.

Focus non-generated contributions on:
- Core infrastructure improvements (EventBuffer, caching, etc.)
- Documentation improvements
- Test coverage