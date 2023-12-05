package com.app.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_NAME = "teamproject";
	public static final String USER_PASSWD = "comp322";

	private static Connection conn = null;

	private DBConnection() {
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object
			System.out.println("Success!");
		} catch (ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try {
			conn = DriverManager.getConnection(URL, USER_NAME, USER_PASSWD);
			System.out.println("Connected.");
			conn.setAutoCommit(false);
			DatabaseMetaData dbmd = conn.getMetaData();
			if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED)) {
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
	}

	public static Connection getConnection() {
		if (conn == null) {
			synchronized (DBConnection.class) {
				if (conn == null) {
					new DBConnection();
				}
			}
		}
		return conn;
	}

	/**
	 * 동시성 제어를 위한 AutoCommit off.
	 * 
	 * @throws SQLException
	 */
	public static void beginTransaction() throws SQLException {
		if (conn != null) {
			conn.setAutoCommit(false);
		}
	}

	/**
	 * commit 기능
	 * 
	 * @throws SQLException
	 */
	public static void commit() throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/**
	 * rollback 기능
	 */
	public static void rollback() {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void setReadUncommited() throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();
		if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED)) {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		}
	}

	public static void setReadCommited() throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();
		if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED)) {
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		}
	}

	public static void setRepeatableRead() throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();
		if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ)) {
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		}
	}

	public static void setSerializable() throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();
		if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE)) {
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		}
	}

	public static void closeConnection() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
}
