package core;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

public class DataFeed {

	@DataProvider(name = "scenarios")

	public Object[][] getDataFromDataprovider(Method m) {
		if (m.getName().equalsIgnoreCase("schemaGetProfileEH")) {
			return new Object[][] { { "eh_01" }, { "eh_02" }, { "eh_03" }, { "eh_04" }, { "eh_05" }, { "eh_06" } };} 
		else if (m.getName().equalsIgnoreCase("schemaAuditCarts")) {
			return new Object[][] { { "01" }, { "02" }, { "03" }, { "04" }, { "05" }};} 		
		else if(m.getName().equalsIgnoreCase("schemaClearCartEH") 
				|| m.getName().equalsIgnoreCase("schemaFeedBackEH")
				|| m.getName().equalsIgnoreCase("schemaRemoveItemEH")){
			return new Object[][] {{"01"}};}
		else if(m.getName().equalsIgnoreCase("schemaGetReceiptEH") ||
				m.getName().equalsIgnoreCase("schemaClubPriceEH")) {
			return new Object[][] { { "01" }, {"02"}};
		}
		else if(m.getName().equalsIgnoreCase("schemaClubPrice")) {
			return new Object[][] { { "happy_01" }, {"happy_02"}};
		}
		else {
			return new Object[][] { { "01" }};
		}
	}
}
