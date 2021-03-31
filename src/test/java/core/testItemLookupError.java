package core;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class testItemLookupError {
	
	String environment 	= null;		
	String config 		= "config.properties";
	String path  		= "itemlookup_error";
	String api 	 		= "itemlookup";
	
	Utilities u = new Utilities();
	
	Properties p = new Properties();
	Response response = null;
	static String url;
	static String port;
	static String env;
	static String retail_env;
	static String timeout;
	
	private void setup(String config, String environment) throws FileNotFoundException, IOException {

		if (System.getProperty("env") == null) environment = "s_p_env_qa";
		else if(System.getProperty("env").equalsIgnoreCase("prod")) environment = "s_p_env_prod"; 
		else if(System.getProperty("env").equalsIgnoreCase("qa")) environment = "s_p_env_qa"; 
		
		p.load(new FileInputStream(config));
		 
			url 					= p.getProperty("url");
			port 	  				= p.getProperty("port");
			env 					= p.getProperty(environment);
			retail_env 				= p.getProperty("retail_env");
			timeout 				= p.getProperty("timeout");
	
			RestAssured.baseURI 	= url;
			RestAssured.basePath	= env;
			RestAssured.port 		= Integer.parseInt(port);
	}

	@Test(enabled = true, priority = 1, description = "API Validation ItemLookup Error")
	@Description(
					"<b>URI: </b> https://retail-api.azure-api.net/ <br />" + 
					"<b>Port: </b> 443 <br />" +
					"<b>Environment: </b> QA [scanandgoQaJS] <br />"
				)
	public void schemaItemLookup() throws Exception {
		setup(config, environment);
	    try {
		response = given()
				.headers(u.readJSONFileAsMap(path, "header"))
				.queryParams(u.readJSONFileAsMap(path, "params"))
				.filter(new AllureRestAssured())
		
		.when()
				.get("/" + api);

		response.then()
				.log().headers()
				.log().body()
				.assertThat()
				.time(lessThan(Long.valueOf(timeout)), TimeUnit.MILLISECONDS)
				.and()
				.statusCode(200)
				.and()
				.header("Content-Length", Integer::parseInt, lessThan(1000))
				.and()
				.header("Content-Type", "application/json; charset=utf-8")
				.and()
				.header("Content-Encoding", "gzip")
				.and()
				.header("Request-Context", containsString("appId=cid-v1:"))
				.and()
				.body(matchesJsonSchemaInClasspath(path + "/schema.json"))
				.extract().response();
	    }
    	catch (Exception e) {e.printStackTrace();}
	    
    	finally {
    	    u.writeConfig(url, port, env, timeout, response.getStatusCode());
    	}
}
}