package com.example.jdbc.magazine.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

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

	@Test
	public void checkConnection() {
		assertNotNull(toOrderManager.getConnection());
	}

	@Test
	public void checkUpdatingFK() throws SQLException {

		toOrderManager.removeOrders();

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		assertEquals(1, toOrderManager.addOrder(order));

		toOrderManager.updateForeignKeyToNull(order);

		List<ToOrder> orders = toOrderManager.getAllOrders();
		ToOrder orderRetrieved = orders.get(0);

		assertNotNull(orderRetrieved.getMagazineId());
	}

	@Test
	public void checkUpdatingFK2() throws SQLException {

		toOrderManager.removeOrders();
		magazineManager.addPosition(position);

		ToOrder order = new ToOrder(ORDEREDAMOUNT_2, PRICE_2);

		assertEquals(1, toOrderManager.addOrder(order));

		toOrderManager.updateForeignKey(order, position);

		assertEquals(order.getMagazineId(), position.getId());
	}

	@Test
	public void checkAdding() throws SQLException {

		toOrderManager.removeOrders();
		magazineManager.addPosition(position);

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		assertEquals(1, toOrderManager.addOrder(order));

		List<ToOrder> orders = toOrderManager.getAllOrders();
		ToOrder orderRetrieved = orders.get(0);

		assertEquals(ORDEREDAMOUNT_1, orderRetrieved.getOrderedAmount());
		assertEquals(PRICE_1, orderRetrieved.getPrice(), 0.0d);
	}

	@Test
	public void checkRemovingOneElement() throws SQLException {

		toOrderManager.removeOrders();

		ToOrder order = new ToOrder(ORDEREDAMOUNT_1, PRICE_1);

		assertEquals(1, toOrderManager.addOrder(order));
		assertEquals(0, toOrderManager.removeOneOrder(order));
	}

}
