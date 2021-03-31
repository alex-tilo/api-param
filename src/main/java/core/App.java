package core;
// mvn clean site -Dtest=testSchemaItemLookup,testSchemaGetProfile && open ./target/site/allure-maven-plugin/index.html
// mvn clean site -Dtest=testSchema* && open ./target/site/allure-maven-plugin/index.html 
// mvn -Dtest=TestCircle#mytest test
public class App {

	public static void main(String[] args) {
		System.out.println("USAGE: " +
				"mvn site && open ./target/site/allure-maven-plugin/index.html" +
				"mvn clean site -Dtest=testSchemaItemLookup && open ./target/site/allure-maven-plugin/index.html" +
				"");
	}
}
