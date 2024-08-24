# Java Behavior-Driven Development Test Client Template

This repository is a template project for building a Java test client that can be run independently from its test target.  This pattern of testing is typically referred to as end-to-end (E2E) integration testing.  This test client uses the JUnit 5 (Jupiter) test engine to run Cucumber-defined tests.  Cucumber is a behavior-driven development (BDD) testing framework that allows tests to be written in plain English.  This allows non-developers to write tests that can be run by developers.  The Cucumber tests are written in the Gherkin language, which is a plain-text language that is easy to understand and write.  The build of this project is built for Github and Github Actions CI/CD pipeline.  If you use this project as a template and you want to use a different CI/CD pipeline, you will need to modify the `build.gradle.kts` file to accommodate your pipeline.  All the Github Actions workflows are in the `.github/workflows` directory so if you are not using Github Actions, you can delete this directory when you create your own repository from this template.

## Why Cucumber and Why Using this Pattern?

The purpose of this test client is to implement end-to-end tests that exercise APIs with tests developed from a "black-box" perspective.  This means that the only knowledge the test client has about it's target is the APIs and/or other external interfaces that the target makes public.  These kinds of tests not only test from the end-user's perspective, but they also can be used to valid the target and all its run-time dependencies regardless of environments (pre-production and/or production).  In addition, the tests needed to be packaged in a fully-standalone client that can be run in a headless environment (e.g., CI/CD pipeline).  This requires a testing framework that can be run in a headless environment and is as light-weight as possible.

After looking at many testing frameworks, we decided to use Cucumber for the following reasons:

1. **Open Source**: Cucumber is an open-source testing framework that is widely used in the industry.
2. **Behavior-Driven Development (BDD)**: Cucumber is a BDD testing framework that allows tests to be written in plain English.  This allows non-developers to write tests that can be run by developers.
3. **Light-Weight**: Cucumber is a light-weight testing framework that is easy to use and understand by developers and doesn't require a large number of dependencies to be packaged with it.
4. **Integration with JUnit 5**: Cucumber integrates with JUnit 5, which is the latest version of the JUnit testing framework.  You can use the JUnit Platform Launcher to run Cucumber tests in a headless environment.

Other evaluated frameworks such as TestNG and Karate were too heavy-weight for our needs with too many dependencies making the final test client package too large.

## How the End-to-End Tests are Packaged

Normally, when using Cucumber in a Java project, the Cucumber tests are run as part of the Maven or Gradle build process, are located in the `src/test` directory structure, and are integrated with the IDE.  Because the E2E tests in this repository are separately maintained from the target, they are found in the `src/main` directory of the project instead of the normal `src/test` location.  This approach makes it easier for Gradle to find all the dependencies and package them into a single JAR file that can be run in a headless environment.  While there are unit tests (JUnit 5) in the `src/test` directory, their target is the test client itself while the Cucumber tests in the `src/main` directory target the external APIs for their tests.  So all the `feature` files and `step definition` classes for the E2E tests should be in the `src/main` directory.

## Getting Started

### Installing and Running a Pre-Built Test Client

#### Prerequisites for Running a Pre-Built Test Client

- Java 17 or later

#### Steps for Installing and Running a Pre-Built Test Client

1. Download the latest version of the released test client from the [releases page](https://github.com/tspauld98/java-test-client-template/releases)
2. Create a personal access token (classic) in Github by following the instructions [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic)

  > **NOTE:** The tests call the Github GraphQL API, which requires an access token to authenticate the user.  For some reason, API access requires the *classic* version of a personal access token.  Make sure you are careful about storing and using your access token.  Do **NOT** save it in configuration files or hard-code it in your code.  That is a security issue (compromised secrets).  Instead, use environment variables or a secret manager to store and retrieve the access token and pass it into the test client as a command-line argument.

3. Store the access token locally in a secure manner so that it can be retrieved when running the test client.
4. Open a terminal window and navigate to the directory where the test client was downloaded
5. Since the test client is an executable JAR file, it can be run using the following command:

```bash
  java -jar java-test-client-template-<version>-shaded.jar --token=<Github Personal Access Token>
```

### Installing and Running the Test Client from Source

#### Prerequisites for Building from Source

- Java 17 or later
- Gradle 8.10 or later

#### Steps for Building and Running from Source Locally

1. Clone the repository to your local machine
2. Open a terminal window and navigate to the root directory of the cloned repository
3. Build the test client using the following command:

```bash
  ./gradlew clean build
```

4. If you want `gradle` to run the test client as part of it's execution, you can use the following command:

```bash
  ./gradlew clean build testClient -PtestClientArgs="--token=<Github Personal Access Token>"
```

### Developing Your Own Tests and Test Client from the Template

#### Prerequisites for Developing Your Own Tests and Test Client

- Java 17 or later
- Gradle 8.10 or later
- A recent version of Git
- An IDE that supports Java development (we like VSCode but you can use others like IntelliJ IDEA, Eclipse, etc.)

#### Steps for Developing Your Own Tests and Test Client

1. Click on the `Use this template` button on the Github repository page to create a new repository based on this template in your own organization or personal account.
2. Follow the instructions in the previous section to clone the repository to your local machine but clone your new repository instead of the template repository.
3. Refactor the package names of the code to match your organization's naming conventions.
4. Open the cloned repository in your IDE and start developing your own tests in the test client.  Start by adding new `feature` files in new subdirectories under the `src/main/resources/<your package path>/features` directory and adding new `step definition` classes in a matching subdirectory of the `feature` files in the `src/main/java/<your package path>/test/client/tests` directory.  Utility classes that help call APIs and parse responses should be added to the `src/main/java/<your package path>/test/client/utils` directory.  Always work in this order: 1. `feature` files, 2. `step definition` classes, and 3. utility classes.  This is the Cucumber BDD development cycle.
5. Add the `@Timed` annotation to any `step definition` methods that you want to monitor the performance in addition to the success of the test.
6. Lather, rinse and repeat until you have all the tests you need to validate your target.

## JAR Usage

```bash
  java -jar [PATH to JAR/]test-client-shadow-<version>.jar [--run-quiet] [--iso-feature <iso-feature>] [--token <access-token>] [--help]
```

### Command-Line Options

`--run-quiet`

  > Run in quiet mode. The client will not print any output to standard out.

`--iso-feature <iso-feature>`

  > Run only the feature in the package indicated by the `<iso-feature>` argument.

`--token <access-token>`

  > Use the `<access-token>` to authenticate with the system under test for certain scenarios.

`--help`

  > Print the help message.

## Dependencies

### Build Dependencies

#### Gradle Plugins

- io.freefair.aspectj.post-compile-weaving
- com.github.johnrengelman.shadow
- jacoco

#### Test Dependencies

- org.junit.jupiter:junit-jupiter

### Run-Time Dependencies

#### Packages Included in Shaded JAR

- org.junit.jupiter:junit-jupiter
- org.junit.jupiter:junit-jupiter-api
- org.junit.platform:junit-platform-launcher
- org.junit.platform:junit-platform-suite-api
- io.cucumber:cucumber-java
- io.cucumber:cucumber-junit-platform-engine
- org.apache.httpcomponents.client5:httpclient5-fluent
- org.slf4j:slf4j-api
- org.slf4j:slf4j-simple
- com.google.code.gson:gson
- commons-validator:commons-validator
- org.aspectj:aspectjrt
- org.aspectj:aspectjweaver
- com.graphql-java:graphql-java-extended-validation

<br/><br/>
<div align=center>Copyright &#169; 2024 Battle Road Consulting. All rights reserved.</div>
