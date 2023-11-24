package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.app.tables.TableMain;

public class Mypage_DML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;

	public Mypage_DML(Connection conn) {
		tm = new TableMain();
		this.conn = conn;
	}

	public ResultSet Customer_show(String id, String password) {
		ResultSet rs = null;
		rs = tm.customer.SELECT(id, password);

		return rs;
	}

	public ResultSet Manager_show(String id, String password) {
		ResultSet rs = null;
		rs = tm.manager.SELECT(id, password);

		return rs;
	}

	public ResultSet Chef_show(String id) {
		ResultSet rs = null;
		rs = tm.chef.SELECT(id);

		return rs;
	}

	public int Customer_update(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.customer.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int Manager_update(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.manager.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int Cjef_update(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.chef.UPDATE(id, Attribute, update_value);

		return res;
	}
}
