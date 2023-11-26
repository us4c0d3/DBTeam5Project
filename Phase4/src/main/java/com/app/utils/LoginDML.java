package com.app.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.tables.TableMain;

public class LoginDML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;
	String sql = "";

	public LoginDML(Connection conn) {
		tm = new TableMain();
		this.conn = conn;
	}

	boolean LoginCustomerValidate(String id, String password) throws IOException {
		ResultSet rs = null;
		String customerId = "";

		rs = tm.customer.SELECT(id, password);

		try {
			if (rs.next()) {
				customerId = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (customerId != "") {
			return true;
		} else {
			return false;
		}
	}

	boolean LoginManagerValidate(String id, String password) throws IOException {
		ResultSet rs = null;
		String managerId = "";

		rs = tm.manager.SELECT(id, password);

		try {
			if (rs.next()) {
				managerId = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (managerId != "") {
			return true;
		} else {
			return false;
		}
	}

}
