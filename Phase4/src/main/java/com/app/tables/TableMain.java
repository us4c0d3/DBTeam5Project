package com.app.tables;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 각 테이블 객체 생성
 * 
 * @filename TableMain.java
 * @author JangWooseok
 */

public class TableMain {
	// Declare Instances
	public Chef chef;
	public Customer customer;
	public Ingredient ingredient;
	public Manager manager;
	public Menu_item menu_item;
	public Menu menu;
	public Payment payment;
	public Contains contains;
	public Need need;
	public Edited_menu edited_menu;
	public Managed_item managed_item;

	Connection conn = null;

	public TableMain(Connection conn) {
		this.conn = conn;

		// Create Instances
		this.chef = new Chef(this.conn);
		this.customer = new Customer(this.conn);
		this.ingredient = new Ingredient(this.conn);
		this.manager = new Manager(this.conn);
		this.menu_item = new Menu_item(this.conn);
		this.menu = new Menu(this.conn);
		this.payment = new Payment(this.conn);
		this.contains = new Contains(this.conn);
		this.need = new Need(this.conn);
		this.edited_menu = new Edited_menu(this.conn);
		this.managed_item = new Managed_item(this.conn);
	}

	/**
	 * @description connection을 닫는 메소드
	 */
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

	/**
	 * 동시성 제어를 위한 AutoCommit off.
	 * 
	 * @throws SQLException
	 */
	public void beginTransaction() throws SQLException {
		conn.setAutoCommit(false);
	}

	/**
	 * commit 기능
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		conn.commit();
	}

	/**
	 * rollback 기능
	 */
	public void rollback() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
