# Leisure Link Backend

## Building the Project

### Standard Build

To build the project with Maven and install:

```bash
mvn clean install
```

### Native Build

#### Using Local GraalVM

To build a native executable (requires GraalVM installed):

```bash
mvn -Pnative clean package
```

The native executable will be created in the `target` directory and can be run directly without a JVM.

#### Using Docker

You can also build and run the native image using Docker without installing GraalVM locally:

```bash
# Build the native image
docker build -f Dockerfile.native -t leisure-link-native .

# Run the native image
docker run -p 8085:8085 leisure-link-native
```

#### Using Docker Compose with Native Image

To run the complete application stack with the native image and all required services:

```bash
# Build and start all services with the native image
docker-compose -f docker-compose-native.yml up --build
```

This will build the native image and start it along with PostgreSQL, Redis, and other required services.

## Running the Project

### Using Docker Compose

To run the project using Docker Compose and build the images:

```bash
docker-compose up
```

### Running the Native Executable

After building the native executable, you can run it directly:

```bash
target/leisure-link
```

## Code Quality

### Spotless

This project uses [Spotless](https://github.com/diffplug/spotless) to maintain consistent code formatting.

#### Running Spotless

To check if your code follows the formatting rules:

```bash
mvn spotless:check
```

To automatically fix formatting issues:

```bash
mvn spotless:apply
```

Spotless is configured to format:
- Java files (using Google Java Format)
- XML files
- Properties files
- Markdown files

#### CI Integration

Spotless checks are automatically run as part of the CI pipeline to ensure consistent code style across the project.

## API Documentation

Swagger link:
http://localhost:8085/swagger-ui/index.html#/User
