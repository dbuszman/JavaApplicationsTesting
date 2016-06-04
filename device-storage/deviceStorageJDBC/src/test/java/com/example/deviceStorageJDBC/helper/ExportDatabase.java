package com.example.deviceStorageJDBC.helper;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;

public class ExportDatabase {

	private static IDatabaseConnection connection ;

	public static void main(String[] args) throws Exception {
		Connection jdbcConnection;
		jdbcConnection = DriverManager.getConnection(
				"jdbc:hsqldb:hsql://localhost/workdb", "sa", "");
		
		connection = new DatabaseConnection(jdbcConnection);
		
		DatabaseConfig dbconfig = connection.getConfig();
		dbconfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
		
		FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream(
				"src/test/resources/dataSet.dtd"));

		FlatXmlDataSet.write(connection.createDataSet(), new FileOutputStream(
				"src/test/resources/fullData.xml"));

	}
}
