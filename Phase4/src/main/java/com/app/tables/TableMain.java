package com.app.tables;

import java.sql.Connection;

import com.app.utils.DBConnection;

/**
 * 각 테이블 객체 생성
 * 
 * @filename TableMain.java
 * @author JangWooseok
 */

public class TableMain {
	// singleton instance
	private static TableMain instance;

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

	public TableMain() {
		this.conn = DBConnection.getConnection();

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

	public static synchronized TableMain getInstance() {
		if (instance == null) {
			instance = new TableMain();
		}
		return instance;
	}
}
