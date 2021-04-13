package core;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.io.*;
import java.util.Properties;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class testCheckOutCart {

	String environment 	= null;
	String config 		= "config.properties";
	String api 			= "checkOutCart";
	String path 		= "checkOutCart";
	
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
		else if(System.getProperty("env").equalsIgnoreCase("qa")) environment = "s_p_env_qa"; 
		else if(System.getProperty("env").equalsIgnoreCase("prod")) environment = "s_p_env_prod"; 
		
		p.load(new FileInputStream(config));
		 
			url 					= p.getProperty("url");
			port 	  				= p.getProperty("port");
			env 					= p.getProperty(environment);
			retail_env 				= p.getProperty("retail_env");
			timeout 				= p.getProperty("timeout");
	
			RestAssured.baseURI 	= url;
			RestAssured.basePath 	= "scanandgoqa";
			RestAssured.port 		= Integer.parseInt(port);
	}

	@Test(enabled = true, priority = 1, description = "API Validation checkOutCart")
	@Description(
						"<b>URI: </b> https://retail-api.azure-api.net/ <br />" + 
						"<b>Port: </b> 443 <br />" +
						"<b>Environment: </b> QA [scanandgoqa] <br />"
				)
	public void schemaItemLookup() throws Exception {
		setup(config, environment);
	    try {
		response = given()
				.headers(u.readJSONFileAsMap(path, "header"))
				.header("Ocp-Apim-Subscription-key", u.getKey("Ocp-Apim-Subscription-key"))	
				.header("oktatoken", u.getToken("access_token"))
				.filter(new AllureRestAssured())
		
		.when()
				.get("/" + api);

		response.then()
				.log().headers()
				.log().body()
				.assertThat()
//				.time(lessThan(Long.valueOf(timeout)), TimeUnit.MILLISECONDS)
//				.and()
				.statusCode(200)
				.and()
				.header("Content-Length", Integer::parseInt, lessThan(1000))
				.and()
				.header("Content-Type", "text/plain; charset=utf-8")
				.and()
				.header("Content-Encoding", "gzip")
				.and()
				.header("Vary", containsString("Accept-Encoding"))
				.and()
//				.body(matchesJsonSchemaInClasspath(path + "/schema.json"))
				.body(matchesJsonSchemaInClasspath(path + "/schema_eh.json"))
				.extract().response();
	        } 
    	catch (Exception e) {e.printStackTrace();}
	    
    	finally {
    	    u.writeConfig(url, port, env, timeout, response.getStatusCode());
    	}
	}
}
