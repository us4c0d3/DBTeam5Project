package com.app.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.tables.TableMain;

public class Login_DML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;
	String sql = "";

	public Login_DML(Connection conn) {
		tm = new TableMain();
		this.conn = conn;
	}

	boolean Login_customer_validate(String id, String password) throws IOException {
		ResultSet rs = null;
		String customer_id = "";

		rs = tm.customer.SELECT(id, password);

		try {
			if (rs.next()) {
				customer_id = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (customer_id != "") {
			return true;
		} else {
			return false;
		}
	}

	boolean Login_manager_validate(String id, String password) throws IOException {
		ResultSet rs = null;
		String manager_id = "";

		rs = tm.manager.SELECT(id, password);

		try {
			if (rs.next()) {
				manager_id = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (manager_id != "") {
			return true;
		} else {
			return false;
		}
	}

}
