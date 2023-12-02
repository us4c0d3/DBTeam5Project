package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.app.tables.Customer;
import com.app.tables.Manager;
import com.app.tables.TableMain;

public class MypageDML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;

	public MypageDML(Connection conn) {
		this.conn = DBConnection.getConnection();
		tm = TableMain.getInstance();
	}

	public List<Customer> showCustomer(String id, String password) {
		List<Customer> customer = tm.customer.SELECT(id, password);

		return customer;
	}

	public List<Manager> showManager(String id, String password) {
		List<Manager> manager = tm.manager.SELECT(id, password);

		return manager;
	}

	public ResultSet showChef(String id) {
		ResultSet rs = null;
		rs = tm.chef.SELECT(id);

		return rs;
	}

	public int updateCustomer(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.customer.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int updateManager(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.manager.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int updateChef(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.chef.UPDATE(id, Attribute, update_value);

		return res;
	}
}
