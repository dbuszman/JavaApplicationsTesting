package com.example.jdbc.magazine.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbc.magazine.domain.Magazine;
import com.example.jdbc.magazine.domain.ToOrder;

public class ToOrderManager {

private Connection connection;
	
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableToOrder = "CREATE TABLE ToOrder(id_order BIGINT UNIQUE " +
			"GENERATED BY DEFAULT AS IDENTITY, id_magazine BIGINT, amount_to_order INTEGER, " +
			"price DECIMAL, FOREIGN KEY (id_magazine) REFERENCES Magazine(id_position) ON DELETE CASCADE ON UPDATE CASCADE)";

	private PreparedStatement addOrderStmt;
	private PreparedStatement deleteOneOrderStmt;
	private PreparedStatement deleteAllOrdersStmt;
	private PreparedStatement updateOrderStmt;
	private PreparedStatement getAllOrdersStmt;
	private PreparedStatement countAllOrdersStmt;


	private Statement statement;

	public ToOrderManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			boolean firstTableExists = false;
			while (rs.next()) {
				if ("ToOrder".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
				if ("Magazine".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					firstTableExists = true;
				}
			}

			if (!tableExists && firstTableExists)
				statement.executeUpdate(createTableToOrder);

			addOrderStmt = connection
					.prepareStatement("INSERT INTO ToOrder (id_magazine, amount_to_order, price) VALUES (?, ?, ?)");
			deleteOneOrderStmt = connection
					.prepareStatement("DELETE FROM ToOrder WHERE id_order = ?");
			deleteAllOrdersStmt = connection
					.prepareStatement("DELETE FROM ToOrder");
			getAllOrdersStmt = connection
					.prepareStatement("SELECT id_order, id_magazine, amount_to_order, price FROM ToOrder");
			updateOrderStmt = connection
					.prepareStatement("UPDATE ToOrder SET id_magazine = ? WHERE id_order = ?");
			countAllOrdersStmt = connection
					.prepareStatement("SELECT COUNT(*) FROM ToOrder");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	MagazineManager magazineManager = new MagazineManager();
	
	Connection getConnection() {
		return connection;
	}
	
	int removeOneOrder(ToOrder order) {
		List<Magazine> positions = magazineManager.getAllPositions();
		Magazine positionRetrieved = positions.get(0);
		
		int count = 0;
		try {
			deleteOneOrderStmt.setLong(1, positionRetrieved.getId());
			count = deleteOneOrderStmt.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	void removeOrders() throws SQLException {
		
		try {
			connection.setAutoCommit(false);
			deleteAllOrdersStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
		}
		finally {
			connection.setAutoCommit(true);
	    }
	}
	
	void updateForeignKeyToNull(ToOrder order){
		
		try {
			updateOrderStmt.setObject(1, null);
			updateOrderStmt.setLong(2, order.getId());
			updateOrderStmt.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	void updateForeignKey(ToOrder order, Magazine position){
		
		try {
			updateOrderStmt.setLong(1, position.getId());;
			updateOrderStmt.setLong(2, order.getId());
			updateOrderStmt.executeUpdate();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public int addOrder(ToOrder order) {
		
		List<Magazine> positions = magazineManager.getAllPositions();
		Magazine positionRetrieved = positions.get(0);
		
		int count = 0;
		try {
			addOrderStmt.setLong(1, positionRetrieved.getId());
			addOrderStmt.setInt(2, order.getOrderedAmount());
			addOrderStmt.setDouble(3, order.getPrice());

			count = addOrderStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public List<ToOrder> getAllOrders() {
		List<ToOrder> orders = new ArrayList<ToOrder>();

		try {
			ResultSet rs = getAllOrdersStmt.executeQuery();

			while (rs.next()) {
				ToOrder tor = new ToOrder();
				tor.setId(rs.getInt("id_order"));
				tor.setMagazineId(rs.getLong("id_magazine"));
				tor.setOrderedAmount(rs.getInt("amount_to_order"));
				tor.setPrice(rs.getDouble("price"));
				orders.add(tor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	public int getCount() {

		try {
			ResultSet result = countAllOrdersStmt.executeQuery();
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
