package com.example.deviceStorageJDBC.rest;

import org.junit.BeforeClass;

import com.jayway.restassured.RestAssured;

public class StorageServiceTest {

	@BeforeClass
	public static void setUp(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
	}
}
