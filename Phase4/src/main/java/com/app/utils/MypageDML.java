package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.app.tables.TableMain;

public class MypageDML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;

	public MypageDML(Connection conn) {
		tm = new TableMain();
		this.conn = conn;
	}

	public ResultSet CustomerShow(String id, String password) {
		ResultSet rs = null;
		rs = tm.customer.SELECT(id, password);

		return rs;
	}

	public ResultSet ManagerShow(String id, String password) {
		ResultSet rs = null;
		rs = tm.manager.SELECT(id, password);

		return rs;
	}

	public ResultSet ChefShow(String id) {
		ResultSet rs = null;
		rs = tm.chef.SELECT(id);

		return rs;
	}

	public int CustomerUpdate(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.customer.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int ManagerUpdate(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.manager.UPDATE(id, Attribute, update_value);

		return res;
	}

	public int ChefUpdate(String id, String Attribute, String update_value) {
		int res = 0;
		res = tm.chef.UPDATE(id, Attribute, update_value);

		return res;
	}
}
