package com.app.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.app.tables.Customer;
import com.app.tables.Manager;
import com.app.tables.TableMain;

public class LoginDML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;
	String sql = "";

	public LoginDML() {
		this.conn = DBConnection.getConnection();
		this.tm = TableMain.getInstance();
	}

	/**
	 * userId가 어떠한 유형의 유저인지 확인하는 메서드
	 * 
	 * @param userId
	 * @return 0: Customer
	 * @return 1: Manager
	 * @return -1: error
	 */
	int checkCusOrMan(String userId) {
		String token = userId.substring(0, 2);
		if (token.equals("CU")) {
			return 0;
		} else if (token.equals("MA")) {
			return 1;
		} else {
			return -1;
		}
	}

	public boolean validateLoginCustomer(String id, String password) throws IOException {
		List<Customer> customer = tm.customer.SELECT(id, password);

		if (!customer.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateLoginManager(String id, String password) throws IOException {
		List<Manager> manager = tm.manager.SELECT(id, password);

		if (!manager.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
