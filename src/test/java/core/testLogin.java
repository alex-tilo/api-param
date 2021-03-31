package core;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class testLogin {
	
	String environment 	= null;
	String config 		= "config.properties";
	String api 			= "authn";
	String path 		= "authn";
	
	Utilities u = new Utilities();
	
	Properties p = new Properties();
	Response response = null;
	static String url;
	static String port;
	static String env;
	static String timeout;
	
	private void setup(String config, String environment) throws FileNotFoundException, IOException {
		
		if (System.getProperty("env") == null) environment = "retail_env_qa";
		else if(System.getProperty("env").equalsIgnoreCase("prod")) environment = "retail_env_prod"; 
		else if(System.getProperty("env").equalsIgnoreCase("qa")) environment = "retail_env_qa"; 
		  
		p.load(new FileInputStream(config));	 
			url 					= p.getProperty("url");
			port 	  				= p.getProperty("port");
			env 					= p.getProperty(environment);
			timeout 				= p.getProperty("timeout");
	
			RestAssured.baseURI 	= "https://abs-qa1.oktapreview.com/";
			RestAssured.basePath 	= "api/v1/";
			RestAssured.port 		= Integer.parseInt(port);
	}

	@Test(enabled = true, priority = 1, description = "API Validation Login")
	@Description(
					"<b>URI: </b> https://abs-qa1.oktapreview.com/ <br />" + 
					"<b>Port: </b> 443 <br />" +
					"<b>Environment: </b> QA [abs-qa1.oktapreview.com] <br />"
				)
	public void schemaGetProfile() throws Exception {
		
		setup(config, environment);
	    try {
		response = given()
				.headers(u.readJSONFileAsMap(api, "header"))
				.body(u.readJSONFileAsString(api))
				.filter(new AllureRestAssured())
		
		.when()
				.post(api);
//			     .post("https://abs-qa1.oktapreview.com/api/v1/authn");

		response.then()
				.log().headers()
				.log().body()
				.assertThat()
				.time(lessThan(Long.valueOf(timeout)), TimeUnit.MILLISECONDS)
				.and()
				.statusCode(200)
				.and()
				.header("Content-Type", "application/json")
				.and()
				.header("Content-Encoding", "gzip")
				.and()
				.body(matchesJsonSchemaInClasspath(path + "/schema.json"))
				.extract().response();
	    }
    	catch (Exception e) {e.printStackTrace();}
	    
    	finally {
    	    u.writeConfig(url, port, "https://abs-qa1.oktapreview.com/api/v1/", timeout, response.getStatusCode());
    	}
}
}
