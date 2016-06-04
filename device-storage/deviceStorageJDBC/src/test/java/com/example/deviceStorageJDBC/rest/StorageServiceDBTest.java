package com.example.deviceStorageJDBC.rest;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.put;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.ws.rs.core.MediaType;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.deviceStorageJDBC.domain.Storage;
import com.example.deviceStorageJDBC.manager.StorageManager;
import com.jayway.restassured.RestAssured;

public class StorageServiceDBTest {
	
	private static IDatabaseConnection connection ;
	private static IDatabaseTester databaseTester;
	
	StorageManager storageManager = new StorageManager();
	
	private final static String NAME_1 = "Brother J100";
	private final static int AMOUNT_1 = 8;
	private final static int MARGIN_1 = 10;
	
	private final static int DEFAULT_MARGIN = 20;
	private final static int MIN_AMOUNT = 2;
	
	@Before
	public void setUp() throws Exception{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/deviceStorageJDBC/api";
		
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		
		connection = new DatabaseConnection(jdbcConnection);
		
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

		databaseTester = new JdbcDatabaseTester(
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/workdb", "sa", ""){
		 	 @Override
	         public IDatabaseConnection getConnection() throws Exception {
	                 IDatabaseConnection connection = super.getConnection();

	                         connection.getConfig().setProperty(
	                                         DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
	                                         new HsqldbDataTypeFactory());

	                 return connection;
	         }
		};
		
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(
				new FileInputStream(new File("src/test/resources/fullData.xml")));
		databaseTester.setDataSet(dataSet);

		databaseTester.onSetup();

	}

	@Test
	public void addDevice() throws Exception{
	
		Storage storage = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		given().contentType(MediaType.APPLICATION_JSON).body(storage)
				.when().post("/storage/").then().assertThat().statusCode(201);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("STORAGE");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable
				(actualTable, new String[]{"ID_POSITION"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/storageAfterAddData.xml"));
		ITable expectedTable = expectedDataSet.getTable("STORAGE");
		
		Assertion.assertEquals(expectedTable, filteredTable);
	}
	
	@Test
	public void removeDevice() throws Exception{
	
		delete("/storage/" + 1).then().assertThat().statusCode(200);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("STORAGE");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable
				(actualTable, new String[]{"ID_POSITION"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/storageAfterDeleteData.xml"));
		ITable expectedTable = expectedDataSet.getTable("STORAGE");
		
		Assertion.assertEquals(expectedTable, filteredTable);
	}
	
	@Test
	public void updateDevicesMargin() throws Exception{
	

	    put("/storage/" + DEFAULT_MARGIN + "/" + MIN_AMOUNT).then().assertThat().statusCode(200);
		
		IDataSet dbDataSet = connection.createDataSet();
		ITable actualTable = dbDataSet.getTable("STORAGE");
		ITable filteredTable = DefaultColumnFilter.excludedColumnsTable
				(actualTable, new String[]{"ID_POSITION"});
		
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(
				new File("src/test/resources/storageAfterUpdateData.xml"));
		ITable expectedTable = expectedDataSet.getTable("STORAGE");
		
		Assertion.assertEquals(expectedTable, filteredTable);
	}
	
	@After
	public void tearDown() throws Exception{
		databaseTester.onTearDown();
	}

}
