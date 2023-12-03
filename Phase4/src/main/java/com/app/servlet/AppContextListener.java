package com.app.servlet;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.app.tables.TableMain;
import com.app.utils.DBConnection;

@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DBConnection.getConnection(); // 애플리케이션 시작 시 DB 연결 초기화
		TableMain.getInstance();
		System.out.println("Context Initialized.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			DBConnection.closeConnection(); // 애플리케이션 종료 시 DB 연결 닫기
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
