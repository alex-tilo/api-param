package core;

import java.io.*;
import java.util.*;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class testParam2 {
	  @BeforeClass
	  public void beforeClass() throws IOException {
		  u.writeFile("log.txt", "");
	  }

	String config 			= "config.properties";
	String api 				= "posts";
	String param			= "test.csv";
	
	Utilities u = new Utilities();
	Properties p = new Properties();
	Response response = null;
	static String url;
	static String port;
	static String timeout;
	
	static String original_content;
	static String modified_content;
	
	private void setup(String config) throws FileNotFoundException, IOException {

		p.load(new FileInputStream(config));
		 
			url 					= p.getProperty("url");
			port 	  				= p.getProperty("port");
			timeout 				= p.getProperty("timeout");
	
			RestAssured.baseURI 	= url;
			RestAssured.port 		= Integer.parseInt(port);
	}
	
	@DataProvider(name = "param")
	public Iterator<String[]> data_provider() throws IOException {
		String csv = "";
		String[] a = null;
		List<String[]> al = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(param));
		while ((csv = br.readLine()) != null) {
				a = csv.split(",");
				al.add(a);}
		br.close();
		return al.iterator();
}

    @Test(dataProvider="param", enabled = true, priority = 1, description = "API Validation")
	@Description("<b>Test: </b><br />")
    public void schemaParam (String id, String name, String salary, String age) throws Exception {
		setup(config);
		
		original_content = u.readJSONFileAsString(api);	
		modified_content = original_content
				
				.replaceAll("\"name\":\"[A-Za-z]{1,20}\\s[A-Za-z]{1,20}\"",	"\"name\":\""		+ name +	"\"")
				.replaceAll("\"salary\":\"[0-9]{1,7}\"",					"\"salary\":\""		+ salary +	"\"")
				.replaceAll("\"age\":\"[0-9]{1,3}\"",						"\"age\":\""		+ age +		"\"");

	    try {
		response = given()
				.headers(u.readJSONFileAsMap(api, "header"))
				.body(modified_content)
//				.body(u.readJSONFileAsString(api))

				.filter(new AllureRestAssured())
		.when()
				.post(api);

		response.then()
				.log().headers()
				.log().body()
				.assertThat()
				.time(lessThan(Long.valueOf(timeout)), TimeUnit.MILLISECONDS)
				.and()
				.statusCode(201)
				.and()
				.header("Content-Type", "application/json; charset=utf-8")
				.and()
				.body(matchesJsonSchemaInClasspath(api + "/schema.json"))
				.extract().response();
				System.out.println(response.body().asString());
	        } 
	    	catch (Exception e) {e.printStackTrace();}
    		finally {u.writeConfig(url, port, timeout, response.getStatusCode());}
	    System.out.println("Actual: " + response.jsonPath().getString("name"));
	    System.out.println("Expected: " + name);
	    System.out.println("Actual: " + response.jsonPath().getString("salary"));
	    System.out.println("Expected: " + salary);
	    System.out.println("Actual: " +response.jsonPath().getString("age"));
	    System.out.println("Expected: " + age);
	    System.out.println("===============================================");

	    u.writeFileAppend("log.txt", 
	      "ID: " + id  + "; "
	    + "Actual: " + response.jsonPath().getString("name") + "; "
	    + "Expected: " + name + "; "
	    + "Actual: " + response.jsonPath().getString("salary") + "; " 
	    + "Expected: " + salary + "; "
	    + "Actual: " + response.jsonPath().getString("age") + "; "
	    + "Expected: " + age 
	    + "\n");

	    assertEquals(response.jsonPath().getString("name"), name);
	    assertEquals(response.jsonPath().getString("salary"), salary);
	    assertEquals(response.jsonPath().getString("age"), age);
	}
}
