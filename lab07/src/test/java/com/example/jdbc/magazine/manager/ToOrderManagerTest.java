package com.example.jdbc.magazine.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.example.jdbc.magazine.domain.Magazine;
import com.example.jdbc.magazine.domain.ToOrder;

public class ToOrderManagerTest {

	ToOrderManager toOrderManager = new ToOrderManager();
	MagazineManager magazineManager = new MagazineManager();

	private final static int ORDEREDAMOUNT_2 = 5;
	private final static float PRICE_2 = 500;

	private final static int ORDEREDAMOUNT_1 = 5;
	private final static float PRICE_1 = 500;

	private final static String NAME_1 = "Intel SSD";
	private final static int AMOUNT_1 = 5;
	private final static int MARGIN_1 = 10;

	Magazine position = new Magazine(NAME_1, AMOUNT_1, MARGIN_1);

	@Before
	public void prepareForeignKey() {
		magazineManager.addPosition(position);
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

		assertNotNull(orderRetrieved.getMagazineId());
	}

	@Test
	public void checkUpdatingFK2() throws SQLException {

		ToOrder order = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);

		assertEquals(1, toOrderManager.addOrder(order));

		toOrderManager.updateForeignKey(order, position);

		assertEquals(order.getMagazineId(), position.getId());
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

		assertEquals(1, toOrderManager.addOrder(order));
		assertEquals(0, toOrderManager.removeOneOrder(order));
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
