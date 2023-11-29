package com.app.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.tables.TableMain;

public class LoginDML {
	TableMain tm;
	private Connection conn = null;
	PreparedStatement ps;
	String sql = "";

	public LoginDML(Connection conn) {
		U
		this.conn = conn;
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

	boolean validateLoginCustomer(String id, String password) throws IOException {
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

	boolean validateLoginManager(String id, String password) throws IOException {
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		boolean isAuthenticated = false;

		switch (checkCusOrMan(userId)) {
			case 0:
				isAuthenticated = validateLoginCustomer(userId, password);
				break;
			case 1:
				isAuthenticated = validateLoginManager(userId, password);
				break;
			default:
				isAuthenticated = false;
		}

		if (isAuthenticated) {
			// 쿠키 생성 및 설정
			Cookie loginCookie = new Cookie("userId", userId);
			loginCookie.setMaxAge(30 * 60); // 30분 동안 유효
			response.addCookie(loginCookie);

			// 다른 페이지로 리다이렉트 또는 메시지 표시
			response.sendRedirect("home.jsp");
		} else {
			System.out.println("Login Error");
		}
	}

}
