package com.example.deviceStorageJDBC.rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Test;

import com.example.deviceStorageJDBC.domain.Storage;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import static com.jayway.restassured.path.json.JsonPath.from;

public class StorageServiceTest {

	private final static String NAME_1 = "Intel i7 2,8Ghz 4gen";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;
	
	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
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
	
	
}
