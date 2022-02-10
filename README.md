# api-param

## Dependencies
1. Java: 	java version "1.8.0_321"
2. Maven: 	Apache Maven 3.8.3  

## Running Tests
The following steps should get you set up for running tests locally on your machine:

1. Clone this repository to your local machine.<br/>
2. All commands must be run from the `\api-param` directory cloned during setup process above.<br/>


### Run Test Suite
`mvn site && open ./target/site/allure-maven-plugin/index.html`
<br>

# Reports
In project exist 2 kind of reports:

- [SureFire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build lifecycle to execute the unit tests of an application.

- [Allure](http://allure.qatools.ru/) report. An open-source framework designed to create test execution reports clear to everyone in the team.

about:config 
security.fileuri.strict_origin_policy
