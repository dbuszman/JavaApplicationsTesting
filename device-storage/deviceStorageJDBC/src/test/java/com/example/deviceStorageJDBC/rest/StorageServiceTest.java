package com.example.deviceStorageJDBC.rest;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.example.deviceStorageJDBC.domain.Storage;
import com.example.deviceStorageJDBC.domain.ToOrder;
import com.jayway.restassured.RestAssured;

public class StorageServiceTest {

	private final static String NAME_1 = "Intel i7 2,8Ghz 4gen";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;
	
	private final static String NAME_2 = "Intel SSD 750";
	private final static int AMOUNT_2 = 1;
	private final static int MARGIN_2 = 8;
	
	private final static int ORDEREDAMOUNT_2 = 10;
	private final static float PRICE_2 = 1000;

	private final static int ORDEREDAMOUNT_1 = 5;
	private final static float PRICE_1 = 500;
	
	private final static int DEFAULT_MARGIN = 15;
	private final static int MIN_AMOUNT = 2;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
	}
	
	private Storage prepareDevice(JsonValue item){
		
		long idPosition;
		String name;
		int amount;
		int margin;
		
		idPosition = Long.parseLong(item.asObject().getString("idPosition", "Unknown Item"));
		name = item.asObject().getString("name", "Unknown Item");
		amount = Integer.parseInt(item.asObject().getString("amount", "Unknown Item"));
		margin = Integer.parseInt(item.asObject().getString("margin", "Unknown Item"));
		
		return new Storage(idPosition, name, amount, margin);
	}
	
	public List<Storage> getDevices(String restrict){
		
		List<Storage> allDevices = new ArrayList<Storage>();
		
		String json = get("/storage/" + restrict).asString();
		
		
		JsonValue jsonValue = Json.parse(json);
		boolean jsonType = jsonValue.asObject().get("storage").isArray();
		
		if(jsonType){
			JsonArray items = jsonValue.asObject().get("storage").asArray();
			
			for (JsonValue item : items) {
				
				allDevices.add(prepareDevice(item));
			}
		}
		else{
			JsonObject item = jsonValue.asObject().get("storage").asObject();
			
			allDevices.add(prepareDevice(item));
		}
		
		return allDevices;
	}
	
	@Test
	public void checkAddingDevice(){
		
		delete("/storage").then().assertThat().statusCode(200);
		
		Storage storage = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(storage).
	    when().	     
	    	post("/storage/").
	    then().
	    	assertThat().statusCode(201);
		
		List<Storage> allDevicesFromRestAfterAdd = getDevices("all");
		assertEquals(allDevicesFromRestAfterAdd.size(), 1);
	}
	
	@Test
	public void checkGettingPositionById(){
		
		Storage storage = new Storage(NAME_2, AMOUNT_2, MARGIN_2);
		given().contentType(MediaType.APPLICATION_JSON).body(storage).when().post("/storage/");
		
		List<Storage> allDevicesFromRest = getDevices("all");
		
		long positionId = allDevicesFromRest.get(allDevicesFromRest.size() - 1).getIdPosition();
		
		Storage retrievedPosition = get("/storage/" + positionId).as(Storage.class);
		
		assertEquals(NAME_2, retrievedPosition.getName());
	}
	
	@Test
	public void checkDeletingAllDevices(){
		
		delete("/storage").then().assertThat().statusCode(200);
		
		expect().body(equalToIgnoringCase("null")).when().get("/storage/all");
	}
	
	@Test
	public void checkUpdatingPositionInStorage() throws JSONException{
		
		delete("/storage").then().assertThat().statusCode(200);
		
		Storage storage = new Storage(NAME_2, AMOUNT_2, MARGIN_2);
		given().contentType(MediaType.APPLICATION_JSON).body(storage).when().post("/storage/");
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(storage).
	    when().	     
	    	put("/storage/" + DEFAULT_MARGIN + "/" + MIN_AMOUNT).
	    then().
	    	assertThat().statusCode(200);
		
		List<Storage> allDevicesFromRestAfterUpdate = getDevices("all");
		
		int marginAfterUpdate = allDevicesFromRestAfterUpdate.get(0).getMargin();
		
		assertEquals(DEFAULT_MARGIN, marginAfterUpdate);
	}
	
	@Test
	public void checkGettingOrderedPositions() throws SQLException {
		
		ToOrderServiceTest toOrderHelper = new ToOrderServiceTest();
		
		delete("/toorder").then().assertThat().statusCode(200);
		
		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);
		ToOrder order2 = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);
		given().contentType(MediaType.APPLICATION_JSON).body(order).when().post("/toorder/");
		given().contentType(MediaType.APPLICATION_JSON).body(order2).when().post("/toorder/");
		
		List<ToOrder> allOrdersFromRest = toOrderHelper.getOrders("all");
		ToOrder orderRetrieved = allOrdersFromRest.get(allOrdersFromRest.size() - 1);
		ToOrder orderRetrieved2 = allOrdersFromRest.get(allOrdersFromRest.size() - 2);
		
		List<Storage> positions = getDevices("all");
		Storage positionRetrieved = positions.get(positions.size() - 1);

		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(orderRetrieved).
	    when().put("/toorder/" + orderRetrieved.getIdOrder() + "/" + positionRetrieved.getIdPosition());
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(orderRetrieved2).
	    when().put("/toorder/" + orderRetrieved2.getIdOrder() + "/" + positionRetrieved.getIdPosition());
		
		List<Storage> orderedPositions = getDevices("orderedPositions");
		
		assertEquals(1, orderedPositions.size());
	}
}
