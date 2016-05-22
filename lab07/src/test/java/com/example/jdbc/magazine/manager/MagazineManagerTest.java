package com.example.jdbc.magazine.manager;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.jdbc.magazine.domain.Magazine;

public class MagazineManagerTest {

	MagazineManager magazineManager = new MagazineManager();

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
		assertNotNull(magazineManager.getConnection());
	}

	@Test
	public void checkAdding() throws SQLException {

		Magazine position = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);
		Magazine position2 = new Magazine(NAME_2, AMOUNT_2, MARGIN_2);

		assertEquals(1, magazineManager.addPosition(position));
		assertEquals(1, magazineManager.addPosition(position2));

		List<Magazine> positions = magazineManager.getAllPositions();
		Magazine positionRetrieved = positions.get(positions.size() - 1);

		assertEquals(NAME_2, positionRetrieved.getName());
		assertEquals(AMOUNT_2, positionRetrieved.getAmount());
		assertEquals(MARGIN_2, positionRetrieved.getMargin());

	}

	@Test
	public void checkRemovingOneElement() throws SQLException {

		Magazine position = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);

		magazineManager.addPosition(position);
		
		position.setId(magazineManager.getAllPositions().get(0).getId());
		
		int currentCountingResult = magazineManager.getCount();
		
		magazineManager.removeOnePosition(position);
		
		int countingResult = magazineManager.getCount();
		
		assertEquals(1, currentCountingResult - countingResult);
	}

	@Test
	public void checkUpdating() throws SQLException {

		magazineManager.removePositions();
		
		Magazine positionToUpdate = new Magazine(NAME_2, AMOUNT_2, MARGIN_2);

		assertEquals(1, magazineManager.addPosition(positionToUpdate));

		magazineManager.updatePositions(DEFAULT_MARGIN, MIN_AMOUNT);

		List<Magazine> positions = magazineManager.getAllPositions();
		Magazine positionRetrieved = positions.get(0);

		assertTrue(positionRetrieved.getAmount() < MIN_AMOUNT);
		assertEquals(positionRetrieved.getMargin(), DEFAULT_MARGIN);
	}

	@Test
	public void checkCountingRecords() throws SQLException {
		
		int currentCountingResult = magazineManager.getCount();

		Magazine position1 = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);
		Magazine position2 = new Magazine(NAME_2, AMOUNT_2, MARGIN_2);
		Magazine position3 = new Magazine(NAME_3, AMOUNT_3, MARGIN_3);
		Magazine position4 = new Magazine(NAME_4, AMOUNT_4, MARGIN_4);

		magazineManager.addPosition(position1);
		magazineManager.addPosition(position2);
		magazineManager.addPosition(position3);
		magazineManager.addPosition(position4);
		
		int countingResult = magazineManager.getCount();

		assertEquals(4, countingResult - currentCountingResult);
	}

	@Test
	public void checkRemovingAllPositions() throws SQLException {

		Magazine position1 = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);
		Magazine position2 = new Magazine(NAME_2, AMOUNT_2, MARGIN_2);
		Magazine position3 = new Magazine(NAME_3, AMOUNT_3, MARGIN_3);
		Magazine position4 = new Magazine(NAME_4, AMOUNT_4, MARGIN_4);

		magazineManager.addPosition(position1);
		magazineManager.addPosition(position2);
		magazineManager.addPosition(position3);
		magazineManager.addPosition(position4);

		magazineManager.removePositions();

		int countingResult = magazineManager.getCount();

		assertEquals(0, countingResult);
	}
	
	@Test
	public void checkFindingRecordsByAmount() {
		
		int currentNumberOfPositions = magazineManager.getPositionsWithLowAmount(MIN_AMOUNT).size();
		
		Magazine position1 = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);
		Magazine position2 = new Magazine(NAME_2, AMOUNT_2, MARGIN_2);
		
		magazineManager.addPosition(position1);
		magazineManager.addPosition(position2);
		
		int numberOfPositions = magazineManager.getPositionsWithLowAmount(MIN_AMOUNT).size();
		
		assertEquals(1, numberOfPositions - currentNumberOfPositions);
	}

}
