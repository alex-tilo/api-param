# scgo-api-automation

## Dependencies
1. Java: 	java version "1.8.0_281"
2. Maven: 	Apache Maven 3.6.3  

## Running Tests
The following steps should get you set up for running tests locally on your machine:

1. Clone this repository to your local machine.<br/>
2. All commands must be run from the `\scgo-api-automation` directory cloned during setup process above.<br/>


### Run Test Suite 'Happy Path'
`mvn site -Denv=qa -Dsuite=happy_path.xml && open ./target/site/allure-maven-plugin/index.html`<br>
`mvn clean site -Dtest=testSchemaItemLookup,testSchemaGetProfile && open ./target/site/allure-maven-plugin/index.html`<br>
`mvn clean site -Dtest=testSchema* && open ./target/site/allure-maven-plugin/index.html`<br>
`mvn -Dtest=TestCircle#mytest test`

# Reports
In project exist 2 kind of reports:

- [SureFire](http://maven.apache.org/surefire/maven-surefire-plugin/) report. The Surefire Plugin is used during the test phase of the build lifecycle to execute the unit tests of an application.

- [Allure](http://allure.qatools.ru/) report. An open-source framework designed to create test execution reports clear to everyone in the team.

