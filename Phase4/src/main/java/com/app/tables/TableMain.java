package com.app.tables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TableMain {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_NAME = "teamproject";
	public static final String USER_PASSWD = "comp322";

	// Declare Instances
	public Chef chef;
	public Customer customer;
	public Ingredient ingredient;
	public Manager manager;
	public Menu_item menu_item;
	public Menu menu;
	public Payment payment;

	Connection conn = null;

	public TableMain() {
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		} catch (ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			System.out.println("Connected.");
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}

		// Create Instances
		this.chef = new Chef(this.conn);
		this.customer = new Customer(this.conn);
		this.ingredient = new Ingredient(this.conn);
		this.manager = new Manager(this.conn);
		this.menu_item = new Menu_item(this.conn);
		this.menu = new Menu(this.conn);
		this.payment = new Payment(this.conn);
	}

	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println("Connection closed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void beginTransaction() throws SQLException {
		conn.setAutoCommit(false);
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
