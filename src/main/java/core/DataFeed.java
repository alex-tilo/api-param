package core;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

public class DataFeed {

	@DataProvider(name = "scenarios")

	public Object[][] getDataFromDataprovider(Method m) {
		     if(m.getName().equalsIgnoreCase("schemaClearCartEH"))		{return new Object[][] 	{{"eh_01"}};}
		else if(m.getName().equalsIgnoreCase("schemaFeedBackEH"))		{return new Object[][] 	{{"eh_01"}};}	
		else if(m.getName().equalsIgnoreCase("schemaRemoveItemEH"))		{return new Object[][] 	{{"eh_01"}};}	
		else if(m.getName().equalsIgnoreCase("schemaGetReceiptEH")) 	{return new Object[][] 	{{"eh_01"},{"eh_02"}};}
		else if(m.getName().equalsIgnoreCase("schemaClubPriceEH")) 		{return new Object[][] 	{{"eh_01"},{"eh_02"}};}		
		else if(m.getName().equalsIgnoreCase("schemaViewCartEH"))       {return new Object[][] 	{{"eh_01"},{"eh_02"}};}
		else if (m.getName().equalsIgnoreCase("schemaAddItemToCartEH"))	{return new Object[][] 	{{"eh_01"},{"eh_02"},{"eh_03"},{"eh_04"},{"eh_05"},{"eh_06"}};} 
		
		else if(m.getName().equalsIgnoreCase("schemaClubPrice")) 		{return new Object[][] 	{{"01"},{"02"}};}
		else if (m.getName().equalsIgnoreCase("schemaAuditCarts")) 		{return new Object[][] 	{{"01"},{"02"},{"03"},{"04"},{"05"}};} 		
		
		else {return new Object[][] {{"01"}};}
	}
}
