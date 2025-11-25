# API Testing Framework - Hotel Booking System

A simple and clean API test automation framework built with Java, Rest-Assured, and Cucumber for testing hotel booking APIs.

## What's This?

This project tests the Hotel Booking API at `https://automationintesting.online`. It covers authentication and all booking operations (create, get, update, patch, delete) with both positive and negative test cases.

## What You Need

- Java 17 or higher
- Maven 3.6+

Check if you have them:
```bash
java -version
mvn -version
```

## Quick Start

1. **Clone and navigate to the project**
   ```bash
   cd API_Testing_Kata-main
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Run all tests**
   ```bash
   mvn test
   ```

That's it! The tests should run and generate reports in the `target` folder.

## Running Specific Tests

### Run by Tags

```bash
# Run only critical tests
mvn test -Dcucumber.filter.tags="@sanity"

# Run only positive tests
mvn test -Dcucumber.filter.tags="@positive"

# Run only negative tests
mvn test -Dcucumber.filter.tags="@negative"

# Run regression suite
mvn test -Dcucumber.filter.tags="@regression"

# Combine tags
mvn test -Dcucumber.filter.tags="@sanity and @positive"
```

### Run Specific Feature

```bash
mvn test -Dcucumber.features="src/test/resources/features/auth.feature"
```

### Run from IDE

Just right-click on `TestRunner.java` and run it.

## Test Tags Explained

We use tags to organize tests:

- **@sanity** - Quick smoke tests to verify basic functionality
- **@positive** - Tests with valid inputs expecting success
- **@negative** - Tests with invalid inputs expecting errors
- **@regression** - Full test suite for regression testing

Most tests have multiple tags like `@positive @sanity @regression`.

## What APIs Are Tested?

### Authentication (`/api/auth/login`)
- ✅ Login with valid credentials
- ✅ Login with invalid/empty credentials
- ✅ Missing request body

### Booking APIs (`/api/booking`)

**Create Booking (POST)**
- Valid booking creation
- Field validations (name length, email format, phone length, dates, etc.)
- Missing/empty required fields
- Duplicate booking handling

**Get Booking (GET `/api/booking/{id}`)**
- Get booking with authentication
- Get booking without auth (should fail)
- Invalid/missing token scenarios
- Non-existent booking

**Update Booking (PUT `/api/booking/{id}`)**
- Full update with valid data
- Field validations
- Authentication checks
- Non-existent booking

**Patch Booking (PATCH `/api/booking/{id}`)**
- Partial updates (firstname, lastname, depositpaid)
- Authentication checks
- Field validations

**Delete Booking (DELETE `/api/booking/{id}`)**
- Delete with authentication
- Delete without auth (should fail)
- Invalid/missing token scenarios
- Non-existent booking

## Project Structure

```
src/test/
├── java/com/booking/
│   ├── config/
│   │   └── ConfigManager.java          # API URLs and credentials
│   ├── model/                          # Request/Response models
│   ├── service/                        # API call logic
│   │   ├── AuthService.java
│   │   └── BookingService.java
│   ├── stepdefinitions/                # Cucumber step definitions
│   │   ├── CommonStepDefinitions.java  # Shared steps
│   │   ├── AuthStepDefinitions.java
│   │   └── Booking*StepDefinitions.java
│   └── util/
│       └── TestDataBuilder.java        # Test data generation
└── resources/
    ├── application.properties          # Configuration
    └── features/                       # Cucumber feature files
        ├── auth.feature
        └── booking-*.feature
```

## Configuration

Edit `src/test/resources/application.properties` if you need to change API URL or credentials:

```properties
api.base.url=https://automationintesting.online
api.booking.endpoint=/api/booking
api.auth.endpoint=/api/auth/login
api.username=admin
api.password=password
```

## How It Works

1. **Feature Files** - Written in plain English (Gherkin) describing test scenarios
2. **Step Definitions** - Java code that executes the steps in feature files
3. **Service Layer** - Handles all API calls using Rest-Assured
4. **Models** - Java classes representing request/response data
5. **TestDataBuilder** - Creates test data with unique values to avoid conflicts

The framework uses `ThreadLocal` to safely run tests in parallel, storing responses, tokens, and booking IDs per thread.

## Reports

After running tests, check:
- **HTML Report**: `target/cucumber-reports.html` (open in browser)
- **JSON Report**: `target/cucumber.json`
- **Surefire Reports**: `target/surefire-reports/`

## Technologies

- Java 17
- Maven
- Rest-Assured 5.5.2
- Cucumber 7.22.2
- JUnit 5.12.2
- Lombok (reduces boilerplate code)  1.18.38
- Jackson (JSON handling) 2.16.2

## API Documentation

- Booking API: https://automationintesting.online/booking/swagger-ui/index.html
- Auth API: https://automationintesting.online/auth/swagger-ui/index.html
- OpenAPI Spec: `src/test/resources/spec/booking.yaml` 
