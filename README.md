```markdown
# Java Cucumber Maven Project

This project demonstrates automated testing using [Cucumber](https://cucumber.io/) with Java and Maven. It includes basic sanity scenarios, step definitions, and hooks for scenario lifecycle events.

## Project Structure

```
src/
main/
java/
test/
java/
org/
example/
runner/
CucumberRunnerTest.java
stepdefs/
SanityStepDefinitions.java
myHooks/
ScenarioHooks.java
resources/
features/
initial_dummy_feature.feature
```

- **Features**: Gherkin feature files are in `src/test/resources/features`.
- **Step Definitions**: Java step definitions are in `org.example.stepdefs`.
- **Hooks**: Scenario hooks are in `myHooks`.
- **Runner**: The test runner is `CucumberRunnerTest.java`.

## Prerequisites

- Java 11 or higher
- Maven 3.6+

## Running Tests

To execute the Cucumber tests and generate an HTML report:

```sh
mvn clean test
```

The report will be available at `target/cucumber-report.html`.

## Custom Hooks

Scenario hooks print the scenario name before and after execution for better traceability.

## Adding Scenarios

Add new `.feature` files in `src/test/resources/features` and implement corresponding step definitions in `org.example.stepdefs`.

## License

This project is for demonstration purposes.
```
