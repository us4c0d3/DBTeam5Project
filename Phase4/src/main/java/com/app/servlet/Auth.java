package com.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
		// what should i do?
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * login do Post
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		boolean isAuthenticated = false;

		if (role.equals("customer")) {
			isAuthenticated = validateLoginCustomer(userId, password);
		} else if (role.equals("manager")) {
			isAuthenticated = validateLoginManager(userId, password);
		} else {
			isAuthenticated = false;
		}

		if (isAuthenticated) {
			// 쿠키 생성 및 설정
			Cookie loginCookie = new Cookie("userId", userId);
			loginCookie.setMaxAge(30 * 60); // 30분 동안 유효
			response.addCookie(loginCookie);

			// 다른 페이지로 리다이렉트 또는 메시지 표시
			response.sendRedirect("index.jsp");
		} else {
			response.sendError(0);
			System.out.println("Login Error");
		}
	}
}
