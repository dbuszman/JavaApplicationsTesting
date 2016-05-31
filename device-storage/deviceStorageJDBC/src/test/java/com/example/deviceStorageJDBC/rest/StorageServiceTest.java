package com.example.deviceStorageJDBC.rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import com.example.deviceStorageJDBC.domain.Storage;
import com.jayway.restassured.RestAssured;

public class StorageServiceTest {

	private final static String NAME_1 = "Intel i7 2,8Ghz 4gen";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;
	
	private List<Storage> allDevicesFromRest;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
	}
	
	private List<Storage> getAllDevices(){
		
		long idPosition;
		String name;
		int amount;
		int margin;
		
		List<Storage> allDevices = new ArrayList<Storage>();
		
		String json = get("/storage/all").asString();
		
		JsonArray items = Json.parse(json).asObject().get("storage").asArray();
		
		for (JsonValue item : items) {
			
			idPosition = Long.parseLong(item.asObject().getString("idPosition", "Unknown Item"));
			name = item.asObject().getString("name", "Unknown Item");
			amount = Integer.parseInt(item.asObject().getString("amount", "Unknown Item"));
			margin = Integer.parseInt(item.asObject().getString("margin", "Unknown Item"));
			
			allDevices.add(new Storage(idPosition, name, amount, margin));
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
		
		expect().body("storage.size()", equalTo(4)).when().get("/storage/all");
	}
	
	@Test
	public void checkDeletingAllDevices(){
		
		delete("/storage").then().assertThat().statusCode(200);
		
		expect().body(equalToIgnoringCase("null")).when().get("/storage/all");
	}
	
	@Test
	public void checkUpdatingPositionInStorage() throws JSONException{
		
		Storage storage = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		Storage storage2 = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		given().contentType(MediaType.APPLICATION_JSON).body(storage).when().post("/storage/");
		given().contentType(MediaType.APPLICATION_JSON).body(storage2).when().post("/storage/");
		
		allDevicesFromRest = getAllDevices();
		
		
		
	}
}
