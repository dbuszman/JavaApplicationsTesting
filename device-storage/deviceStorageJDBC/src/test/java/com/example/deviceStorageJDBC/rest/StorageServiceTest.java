package com.example.deviceStorageJDBC.rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.example.deviceStorageJDBC.domain.Storage;
import com.jayway.restassured.RestAssured;

public class StorageServiceTest {

	private final static String NAME_1 = "Intel i7 2,8Ghz 4gen";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;
	
	private final static String NAME_2 = "Intel SSD 750";
	private final static int AMOUNT_2 = 1;
	private final static int MARGIN_2 = 8;
	
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
	
	private List<Storage> getAllDevices(){
		
		List<Storage> allDevices = new ArrayList<Storage>();
		
		String json = get("/storage/all").asString();
		
		
		JsonValue jsonValue = Json.parse(json);
		
		if(jsonValue.isArray()){
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
		
		List<Storage> allDevicesFromRestAfterAdd = getAllDevices();
		assertEquals(allDevicesFromRestAfterAdd.size(), 1);
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
		
		List<Storage> allDevicesFromRestAfterUpdate = getAllDevices();
		
		int marginAfterUpdate = allDevicesFromRestAfterUpdate.get(0).getMargin();
		
		assertEquals(DEFAULT_MARGIN, marginAfterUpdate);
	}
}
