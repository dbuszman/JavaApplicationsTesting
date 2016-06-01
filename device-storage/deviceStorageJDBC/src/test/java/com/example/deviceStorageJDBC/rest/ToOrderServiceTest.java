package com.example.deviceStorageJDBC.rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.example.deviceStorageJDBC.domain.Storage;
import com.example.deviceStorageJDBC.domain.ToOrder;
import com.jayway.restassured.RestAssured;

public class ToOrderServiceTest {

	private final static int ORDEREDAMOUNT_2 = 10;
	private final static float PRICE_2 = 1000;

	private final static int ORDEREDAMOUNT_1 = 5;
	private final static float PRICE_1 = 500;

	private final static String NAME_1 = "Intel SSD";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
	}
	
	Storage position = new Storage(NAME_1, AMOUNT_1, MARGIN_1);

	@Before
	public void prepareForeignKey() {
		given().contentType(MediaType.APPLICATION_JSON).body(position).when().post("/storage/");
	}
	
	private ToOrder prepareToOrder(JsonValue item){
		
		long idOrder;
		Long idStorage;
		int orderedAmount;
		double price;
		
		idOrder = Long.parseLong(item.asObject().getString("idOrder", "Unknown Item"));
		idStorage = Long.parseLong(item.asObject().getString("idStorage", "Unknown Item"));
		orderedAmount = Integer.parseInt(item.asObject().getString("orderedAmount", "Unknown Item"));
		price = Double.parseDouble(item.asObject().getString("price", "Unknown Item"));
		
		return new ToOrder(idOrder, idStorage, orderedAmount, price);
	}
	
	public List<ToOrder> getOrders(String restrict){
		
		List<ToOrder> allOrders = new ArrayList<ToOrder>();
		
		String json = get("/toorder/" + restrict).asString();
		
		JsonValue jsonValue = Json.parse(json);
		boolean jsonType = jsonValue.asObject().get("toOrder").isArray();
		
		if(jsonType){
			JsonArray items = jsonValue.asObject().get("toOrder").asArray();
			
			for (JsonValue item : items) {
				
				allOrders.add(prepareToOrder(item));
			}
		}
		else{
			JsonObject item = jsonValue.asObject().get("toOrder").asObject();
			
			allOrders.add(prepareToOrder(item));
		}
		
		return allOrders;
	}
	
	@Test
	public void checkAddingOrder(){
		
		delete("/toorder").then().assertThat().statusCode(200);
		
		ToOrder toorder = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(toorder).
	    when().	     
	    	post("/toorder/").
	    then().
	    	assertThat().statusCode(201);
		
		List<ToOrder> allOrdersFromRestAfterAdd = getOrders("all");
		assertEquals(allOrdersFromRestAfterAdd.size(), 1);
	}
	
	@Test
	public void checkGettingOrderById(){
		
		ToOrder toorder = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);
		given().contentType(MediaType.APPLICATION_JSON).body(toorder).when().post("/toorder/");
		
		List<ToOrder> allOrdersFromRest = getOrders("all");
		
		long orderId = allOrdersFromRest.get(allOrdersFromRest.size() - 1).getIdOrder();
		
		ToOrder retrievedToOrder = get("/toorder/" + orderId).as(ToOrder.class);
		
		assertEquals(ORDEREDAMOUNT_2, retrievedToOrder.getOrderedAmount());
		assertEquals(PRICE_2, retrievedToOrder.getPrice(), 0.0d);
	}
	
	@Test
	public void checkDeletingAllDevices(){
		
		delete("/toorder").then().assertThat().statusCode(200);
		
		expect().body(equalToIgnoringCase("null")).when().get("/toorder/all");
	}
	
	@Test
	public void checkUpdatingFK() throws SQLException {

		StorageServiceTest storageHelper = new StorageServiceTest();
		
		ToOrder toorder = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);
		given().contentType(MediaType.APPLICATION_JSON).body(toorder).when().post("/toorder/");

		List<ToOrder> allOrdersFromRest = getOrders("all");
		ToOrder orderRetrieved = allOrdersFromRest.get(allOrdersFromRest.size() - 1);
		
		List<Storage> positions = storageHelper.getDevices("all");
		Storage positionRetrieved = positions.get(positions.size() - 1);

		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(toorder).
	    when().	     
	    	put("/toorder/" + orderRetrieved.getIdOrder() + "/" + positionRetrieved.getIdPosition()).
	    then().
	    	assertThat().statusCode(200);
		
		allOrdersFromRest = getOrders("all");
		orderRetrieved = allOrdersFromRest.get(allOrdersFromRest.size() - 1);

		assertEquals((long)orderRetrieved.getIdStorage(), positionRetrieved.getIdPosition());
	}
	
	@Test
	public void checkGettingAllOrdersByPositionId() throws SQLException {
		
		StorageServiceTest storageHelper = new StorageServiceTest();
		
		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);
		ToOrder order2 = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);
		
		given().contentType(MediaType.APPLICATION_JSON).body(order).when().post("/toorder/");
		given().contentType(MediaType.APPLICATION_JSON).body(order2).when().post("/toorder/");
		
		List<ToOrder> allOrdersFromRest = getOrders("all");
		ToOrder orderRetrieved = allOrdersFromRest.get(allOrdersFromRest.size() - 1);
		ToOrder orderRetrieved2 = allOrdersFromRest.get(allOrdersFromRest.size() - 2);
		
		List<Storage> positions = storageHelper.getDevices("all");
		Storage positionRetrieved = positions.get(positions.size() - 1);
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(order).
	    when().put("/toorder/" + orderRetrieved.getIdOrder() + "/" + positionRetrieved.getIdPosition());
		
		given().
	       contentType(MediaType.APPLICATION_JSON).
	       body(order2).
	    when().put("/toorder/" + orderRetrieved2.getIdOrder() + "/" + positionRetrieved.getIdPosition());
		
		List<ToOrder> ordersByPositionInStorage = getOrders("ordersForDevice/" + positionRetrieved.getIdPosition());
		
		int ordersWithPositonAmount = ordersByPositionInStorage.size();
		
		assertEquals(2, ordersWithPositonAmount);
		
	}
	
	
}
