package com.app.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.utils.LoginDML;

/**
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginDML loginDML;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Auth() {
		super();
		// TODO Auto-generated constructor stub
		this.loginDML = new LoginDML();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("\nrole: ").append(request.getParameter("role"));
		response.getWriter().append("\nID: ").append(request.getParameter("userId"));
		response.getWriter().append("\npassword: ").append(request.getParameter("password"));
	}

	/**
	 * login do Post
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// doGet(request, response);
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		boolean isAuthenticated = false;

		if (role.equals("customer")) {
			isAuthenticated = loginDML.validateLoginCustomer(userId, password);
		} else if (role.equals("manager")) {
			isAuthenticated = loginDML.validateLoginManager(userId, password);
		} else {
			isAuthenticated = false;
		}

		if (isAuthenticated) {
			// 쿠키 생성 및 설정
			Cookie loginCookie = new Cookie("userId", userId);
			loginCookie.setMaxAge(30 * 60); // 30분 동안 유효
			response.addCookie(loginCookie);

			// 다른 페이지로 리다이렉트 또는 메시지 표시
			response.sendRedirect("/Phase4/views/index.jsp");
		} else {
			response.sendRedirect("/Phase4/static/html/Login.html?error=true");
			System.out.println("Login Error");
		}
	}
}
