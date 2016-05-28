package com.example.deviceStorageJDBC.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.deviceStorageJDBC.domain.Storage;
import com.example.deviceStorageJDBC.domain.ToOrder;

public class ToOrderManagerTest {
	
	ToOrderManager toOrderManager = new ToOrderManager();
	StorageManager storageManager = new StorageManager();

	private final static int ORDEREDAMOUNT_2 = 5;
	private final static float PRICE_2 = 500;

	private final static int ORDEREDAMOUNT_1 = 5;
	private final static float PRICE_1 = 500;

	private final static String NAME_1 = "Intel SSD";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;

	Storage position = new Storage(NAME_1, AMOUNT_1, MARGIN_1);

	@Before
	public void prepareForeignKey() {
		storageManager.addPosition(position);
	}

	@Test
	public void checkConnection() {
		assertNotNull(toOrderManager.getConnection());
	}

	@Test
	public void checkUpdatingFK() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		assertEquals(1, toOrderManager.addOrder(order));

		toOrderManager.updateForeignKeyToNull(order);

		List<ToOrder> orders = toOrderManager.getAllOrders();
		ToOrder orderRetrieved = orders.get(0);

		assertNotNull(orderRetrieved.getIdStorage());
	}

	@Test
	public void checkUpdatingFK2() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);

		assertEquals(1, toOrderManager.addOrder(order));

		toOrderManager.updateForeignKey(order, position);

		assertEquals(order.getIdStorage(), position.getIdPosition());
	}

	@Test
	public void checkAdding() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		assertEquals(1, toOrderManager.addOrder(order));

		List<ToOrder> orders = toOrderManager.getAllOrders();
		ToOrder orderRetrieved = orders.get(0);

		assertEquals(ORDEREDAMOUNT_1, orderRetrieved.getOrderedAmount());
		assertEquals(PRICE_1, orderRetrieved.getPrice(), 0.0d);
	}

	@Test
	public void checkRemovingOneElement() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		toOrderManager.addOrder(order);
		order.setIdOrder(toOrderManager.getAllOrders().get(0).getIdOrder());
		
		int currentCountingResult = toOrderManager.getCount();
		
		toOrderManager.removeOneOrder(order);
		
		int countingResult = toOrderManager.getCount();
		
		assertEquals(1, currentCountingResult - countingResult);
		
	}

	@Test
	public void checkCountingElements() throws SQLException {

		int currentCountingResult = toOrderManager.getCount();
		
		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);
		ToOrder order2 = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);

		toOrderManager.addOrder(order);
		toOrderManager.addOrder(order2);

		int countingResult = toOrderManager.getCount();

		assertEquals(2, countingResult - currentCountingResult);
	}

	@Test
	public void checkRemovingAllElements() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);
		ToOrder order2 = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);

		toOrderManager.addOrder(order);
		toOrderManager.addOrder(order2);

		toOrderManager.removeOrders();

		int countingResult = toOrderManager.getCount();

		assertEquals(0, countingResult);
	}

}
