package com.example.deviceStorageJDBC.manager;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.deviceStorageJDBC.domain.Storage;


public class StorageManagerTest {
	
	StorageManager storageManager = new StorageManager();

	private final static String NAME_1 = "Intel i7 2,8Ghz 4gen";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;

	private final static String NAME_2 = "Nvidia GF 950 GTX";
	private final static int AMOUNT_2 = 1;
	private final static int MARGIN_2 = 10;

	private final static String NAME_3 = "WD BLUE 500 GB";
	private final static int AMOUNT_3 = 2;
	private final static int MARGIN_3 = 11;

	private final static String NAME_4 = "TP Link WRL-8424ND";
	private final static int AMOUNT_4 = 1;
	private final static int MARGIN_4 = 5;

	private final static int DEFAULT_MARGIN = 15;
	private final static int MIN_AMOUNT = 2;


	@Test
	public void checkConnection() {
		assertNotNull(storageManager.getConnection());
	}

	@Test
	public void checkAdding() throws SQLException {

		Storage position = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		Storage position2 = new Storage(NAME_2, AMOUNT_2, MARGIN_2);

		assertEquals(1, storageManager.addPosition(position));
		assertEquals(1, storageManager.addPosition(position2));

		List<Storage> positions = storageManager.getAllPositions();
		Storage positionRetrieved = positions.get(positions.size() - 1);

		assertEquals(NAME_2, positionRetrieved.getName());
		assertEquals(AMOUNT_2, positionRetrieved.getAmount());
		assertEquals(MARGIN_2, positionRetrieved.getMargin());

	}

	@Test
	public void checkRemovingOneElement() throws SQLException {

		Storage position = new Storage(NAME_1, AMOUNT_1, MARGIN_1);

		storageManager.addPosition(position);
		
		List<Storage> positions = storageManager.getAllPositions();
		Storage positionRetrieved = positions.get(positions.size() - 1);
		
		int currentCountingResult = storageManager.getCount();
		
		storageManager.removeOnePosition(positionRetrieved);
		
		int countingResult = storageManager.getCount();
		
		assertEquals(1, currentCountingResult - countingResult);
	}

	@Test
	public void checkUpdating() throws SQLException {

		storageManager.removePositions();
		
		Storage positionToUpdate = new Storage(NAME_2, AMOUNT_2, MARGIN_2);

		assertEquals(1, storageManager.addPosition(positionToUpdate));

		storageManager.updatePositions(DEFAULT_MARGIN, MIN_AMOUNT);

		List<Storage> positions = storageManager.getAllPositions();
		Storage positionRetrieved = positions.get(0);

		assertTrue(positionRetrieved.getAmount() < MIN_AMOUNT);
		assertEquals(positionRetrieved.getMargin(), DEFAULT_MARGIN);
	}

	@Test
	public void checkCountingRecords() throws SQLException {
		
		int currentCountingResult = storageManager.getCount();

		Storage position1 = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		Storage position2 = new Storage(NAME_2, AMOUNT_2, MARGIN_2);
		Storage position3 = new Storage(NAME_3, AMOUNT_3, MARGIN_3);
		Storage position4 = new Storage(NAME_4, AMOUNT_4, MARGIN_4);

		storageManager.addPosition(position1);
		storageManager.addPosition(position2);
		storageManager.addPosition(position3);
		storageManager.addPosition(position4);
		
		int countingResult = storageManager.getCount();

		assertEquals(4, countingResult - currentCountingResult);
	}

	@Test
	public void checkRemovingAllPositions() throws SQLException {

		Storage position1 = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		Storage position2 = new Storage(NAME_2, AMOUNT_2, MARGIN_2);
		Storage position3 = new Storage(NAME_3, AMOUNT_3, MARGIN_3);
		Storage position4 = new Storage(NAME_4, AMOUNT_4, MARGIN_4);

		storageManager.addPosition(position1);
		storageManager.addPosition(position2);
		storageManager.addPosition(position3);
		storageManager.addPosition(position4);

		storageManager.removePositions();

		int countingResult = storageManager.getCount();

		assertEquals(0, countingResult);
	}
	
	@Test
	public void checkFindingRecordsByAmount() {
		
		int currentNumberOfPositions = storageManager.getPositionsWithLowAmount(MIN_AMOUNT).size();
		
		Storage position1 = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		Storage position2 = new Storage(NAME_2, AMOUNT_2, MARGIN_2);
		
		storageManager.addPosition(position1);
		storageManager.addPosition(position2);
		
		int numberOfPositions = storageManager.getPositionsWithLowAmount(MIN_AMOUNT).size();
		
		assertEquals(1, numberOfPositions - currentNumberOfPositions);
	}
	
	@Test
	public void checkFindingDeviceById() {
		
		Storage position = new Storage(NAME_1, AMOUNT_1, MARGIN_1);
		
		storageManager.addPosition(position);
		
		List<Storage> positions = storageManager.getAllPositions();
		Storage positionRetrieved = positions.get(positions.size() - 1);
		
		Storage positionById = storageManager.getPositionById(positionRetrieved.getIdPosition());
		
		assertEquals(positionRetrieved.getIdPosition(), positionById.getIdPosition());
		assertEquals(positionRetrieved.getAmount(), positionById.getAmount());
		assertEquals(positionRetrieved.getName(), positionById.getName());
		
	}

}
