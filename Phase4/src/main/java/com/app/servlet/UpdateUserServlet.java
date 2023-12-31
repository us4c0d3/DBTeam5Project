package com.app.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.tables.TableMain;
import com.app.utils.DBConnection;
import com.app.utils.LoginDML;

/**
 * Servlet implementation class UpdateUserServlet
 */
@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDML loginDML;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUserServlet() {
		super();
		this.loginDML = new LoginDML();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String phoneNumber = request.getParameter("phoneNumber");
		String currentPassword = request.getParameter("currentPassword");
		String changePassword = request.getParameter("changePassword");

		TableMain tm = TableMain.getInstance();
		boolean validPassword = false;

		try {
			DBConnection.beginTransaction();
			DBConnection.setReadCommited();

			if (userId.startsWith("CU")) {
				validPassword = this.loginDML.validateLoginCustomer(userId, currentPassword);
			} else if (userId.startsWith("MA")) {
				validPassword = this.loginDML.validateLoginManager(userId, currentPassword);
			}

			if (!validPassword) {
				// 잘못된 패스워드, Mypage로 리디렉션 및 오류 메시지 전달
				request.getSession().setAttribute("errorMessage", "Invalid current password.");
				response.sendRedirect("/Phase4/views/Mypage.jsp");
				return;
			}

			// DB 업데이트 로직
			if (userId.startsWith("CU")) {
				tm.customer.UPDATE(userId, name, (changePassword.equals("") ? currentPassword : changePassword),
					phoneNumber);
			} else if (userId.startsWith("MA")) {
				tm.manager.UPDATE(userId, name, (changePassword.equals("") ? currentPassword : changePassword),
					phoneNumber);
			}

			DBConnection.commit();
		} catch (SQLException e) {
			// 실패한 경우 롤백
			DBConnection.rollback();
			e.printStackTrace();
		}

		// 리디렉션
		request.getSession().setAttribute("successMessage", "Information updated successfully.");
		response.sendRedirect("/Phase4/views/Mypage.jsp");
	}

}
