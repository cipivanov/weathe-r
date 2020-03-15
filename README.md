# weathe/r/ Test Automation Framework

[![CircleCI](https://circleci.com/gh/cipivanov/weathe-r.svg?style=svg&circle-token=706f1d79f8ac8a3d964fa816630d6b13fd250775)](https://circleci.com/gh/cipivanov/weathe-r)

> This small piece of code represents the incipient stage of a test automation framework based on the [Screenplay pattern](https://www.infoq.com/articles/Beyond-Page-Objects-Test-Automation-Serenity-Screenplay) but slghtly adapted to API testing. It is capable of basic interactions with [Open Weather Map API](https://openweathermap.org/api) and [Reddit API](​https://www.reddit.com/dev/api).

## Languages, frameworks and libraries used

* *Java 11:* programming language of choice
* *Gradle:* dependency management and build tool
* *JUnit:* testing framework driver
* *REST-assured:* REST services testing framework
* *jackson-databind:* complementary library for REST-assured to allow JSON messages unmarshalling
* *dotenv:* simplistic configuration loading library

## Project structure

The ATF core has the following structure:
src\main\java\com\openplatform\atf

```bash
src
└─main
   └─java
      └─com
         └─curve
            └─weather
               └─core
               |  └─api         - abstracted API level interactions
               |  └─screenplay  - screenplay pattern building blocks
               └─domain
                  └─reddit      - Reddit specific API adapters and screenplay interactions
                  └─openweather - Open Weather Map specific API adapters and screenplay interactions
```

*.circleci/config.yml* - CI configuration-as-code file
*gradle.build* - handles all the dependencies and information about the build process
*gradle* - Gradle wrapper folder, allows to run the tests without installing gradle

*src\test\java\com\curve\weather\WeatherFlowsTest.java* - test cases location

## Setup

Not much in the way of setting up the project.

The only requirement is to have Java 11 SDK installed.

## Capabilities

Bare-bones test framework structure created and is includes perform the following:

1. Define BDD style scenarios using basic Screenplay DSL
2. Make REST service API request
3. Un-marshal and check response obtained
4. Allows integration into CI/CD pipeline

## Things That Never May (TODOs/Future Capabilities)

* Custom reporting capabilities
  * Showcase what is being tested and the value of the tests being run to non-technical people.
  * Test evidence (more in-depth extensive test run logs) gathering is also being developed
* Extension and re-factoring of the checks
  * The goal is allow a multitude of validation capabilities coupled with reduction in boilerplate code.
  * eager test failures, i.e. actions failing after first failed check
* Refactoring of the Adapter layer (currently hides some actions to avoid a complex screenplay layer)
* Sane approach to context sharing (instead of using memory as context)
* Improved logging, which is rather simplisting currently
* Unit tests for core framework functionalities
* More extensive modeling of responses/requests
* Parallel execution capabilities
* Code quality metrics
