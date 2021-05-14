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
import static io.restassured.RestAssured.*;

public class testDigitalReceiptsIDParam {

	String environment 	= null;
	String config 		= "config.properties";
	String api 			= "receipts";
	String path 		= "receipts";
	String digitalreceiptsParam	= "digitalreceipts.csv";
	
	Utilities u = new Utilities();
	
	Properties p = new Properties();
	Response response = null;
	static String url;
	static String port;
	static String env;
	static String retail_env;
	static String timeout;
	
	private void setup(String config, String environment) throws FileNotFoundException, IOException {

		if (System.getProperty("env") == null) environment = "ztp_env_prod";
		else if(System.getProperty("env").equalsIgnoreCase("prod")) environment = "ztp_env_prod"; 
		else if(System.getProperty("env").equalsIgnoreCase("qa")) environment = "ztp_env_prod"; 

		p.load(new FileInputStream(config));
		 
			url 					= p.getProperty("url_ztp");
			port 	  				= p.getProperty("port");
			env 					= p.getProperty(environment);
			retail_env 				= p.getProperty("retail_env");
			timeout 				= p.getProperty("timeout");
	
			RestAssured.baseURI 	= url;
			RestAssured.basePath 	= env;
			RestAssured.port 		= Integer.parseInt(port);
	}
	
	@DataProvider(name = "itemLookup")
	public Iterator<String[]> data_provider() throws IOException {
		String csv = "";
		String[] a = null;
		List<String[]> al = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(digitalreceiptsParam));
		while ((csv = br.readLine()) != null) {
				a = csv.split(",");
				al.add(a);}
		br.close();
		return al.iterator();
}

	@Test(enabled = true, priority = 1, description = "API Validation Digital Receipts ID Param")
	@Description(
					"<b>URI: </b> https://www-qa2.safeway.com/ <br />" + 
					"<b>Port: </b> 443 <br />" +
					"<b>Environment: </b> QA<br />"
				)
	public void schemaDigitalReceiptsID() throws Exception {
		setup(config, environment);
		System.out.println(u.getBearerToken("access_token_ztp"));
	    try {
		response = given()
				.headers(u.readJSONFileAsMap(path, "header"))
				.header("Ocp-Apim-Subscription-key", u.getKey("Ocp-Apim-Subscription-key_ztp"))	
				.header("Authorization", u.getBearerToken("access_token_ztp"))
				.queryParams(u.readJSONFileAsMap(path, "params_id"))
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
				.header("Content-Length", Integer::parseInt, lessThan(2000))
				.and()
				.header("Content-Type", "application/json; charset=utf-8")
				.and()
				.header("Content-Encoding", "gzip")
				.and()
				.header("Request-Context", containsString("appId=cid-v1:1bc0e5cb-3220-453c-97d2-9cb5a25d0de8"))
				.and()
				.body(matchesJsonSchemaInClasspath(path + "/schema_id.json"))
				.extract().response();
	        } 
    	catch (Exception e) {e.printStackTrace();}
	    
    	finally {
    	    u.writeConfig(url, port, env, timeout, response.getStatusCode());
    	}
}
}
