package lab7;

/**************************************************
 * Copyright (c) 2023 KNU DACS Lab. To Present
 * All rights reserved. 
 **************************************************/

import java.sql.*; // import JDBC package
/**
 * Sample Code for JDBC Practice
 * @author yksuh
 */
public class TestJDBC {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_UNIVERSITY ="university"; 
	public static final String USER_PASSWD ="comp322";
	public static final String TABLE_NAME = "TEST";
		
	public static void main(String[] args) {
		Connection conn = null; // Connection object
		Statement stmt = null;	// Statement object
		String sql = ""; // an SQL statement 
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}

		// Make a connection
		try{
			conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD); 
			System.out.println("Connected.");
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		// Execute an SQL statement for CREATE TABLE
		try {
			conn.setAutoCommit(false); // auto-commit disabled  
			// Create a statement object
			stmt = conn.createStatement();
			// Let's execute an SQL statement.
			int res = 0;
			sql = "DROP TABLE " + TABLE_NAME + " CASCADE CONSTRAINT";
			res = stmt.executeUpdate(sql); 
			try {
				 res = stmt.executeUpdate(sql); 
			}catch(Exception ex) {
				// in most cases, you'll see "table or view does not exist"
				System.out.println(ex.getMessage());
			}
			if(res == 0) 
				System.out.println("Table TEST was successfully dropped.");
			StringBuffer sb = new StringBuffer();
			sb.append("CREATE TABLE " + TABLE_NAME + " (Id INT, ");
			sb.append("				      Name VARCHAR(10), ");
			sb.append("				      Address VARCHAR(20))");
			sql = sb.toString();
System.out.println(sql); // print create TABLE statement 
			// Try 'CREATE TABLE ...'
			res = stmt.executeUpdate(sql); 
			if(res == 0) 
				System.out.println("Table TEST was successfully created.");
			// Make the table permanently stored by a commit.
			conn.commit();			

		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		// Execute an SQL statement for INSERT
		try {
			// Let's execute an SQL statement.
			sql = "INSERT INTO TEST VALUES (10, 'SUH', 'Daegu')";
			// Try 'INSERT INTO ...' for the first time
			int res = stmt.executeUpdate(sql);
			System.out.println(res + " row inserted.");
			// Let's do more.
			sql = "INSERT INTO TEST VALUES (20, 'PARK', 'Busan')";
			// Add above SQL statement in the batch.
			stmt.addBatch(sql);
			sql = "INSERT INTO TEST VALUES (30, 'Rivera', 'New York')";
			// Add above SQL statement in the batch.
			stmt.addBatch(sql);
			sql = "INSERT INTO TEST VALUES (40, 'Ryu', 'Los Angeles')";
			// Add above SQL statement in the batch.
			stmt.addBatch(sql);
			// Create an int[] to hold returned values
			int[] count = stmt.executeBatch();
			System.out.println(count.length + " row inserted.");
			// Make the changes permanent 
			conn.commit();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		// Execute an SELECT statement 
		try {
			// Let's execute an SQL statement.
			sql = "SELECT * from TEST";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs != null) {
				while(rs.next()) {
					// no impedance mismatch in JDBC
					int id 		= rs.getInt(1);
					String name = rs.getString(2);
					String addr = rs.getString(3);
					System.out.println("ID = " + id 
									+  ", Name = " + name 
									+  ", Address = " + addr);
				}
			}else {
				System.out.println("no rows returned.");
			}
			//conn.commit();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		// Execute an SQL statement for INSERT
		try {
			// Let's execute an SQL statement.
			sql = "UPDATE TEST SET Name = 'Oh' WHERE Id = 40";
			// Try 'UPDATE ...' for the first time
			int res = stmt.executeUpdate(sql);
		System.out.println(res + " row updated.");
			// Let's do DELETE.
			sql = "DELETE FROM TEST WHERE Id = 30";
			// Add above SQL statement in the batch.
		stmt.addBatch(sql);
			int[] count = stmt.executeBatch();
			System.out.println(count.length + " row deleted.");
			// Make the changes permanent 
			conn.commit();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		// Execute the same SELECT statement with a PreparedStatement  
		// to confirm the modification
		try {
			// Let's execute an SQL statement.
			sql = "SELECT * from TEST WHERE Id = ?";
//			sql = "SELECT * from TEST WHERE Id = ? and name = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			//ResultSet rs = stmt.executeQuery(sql);
			ps.setInt(1, 40);
//			ps.setString(2, "PARK");
System.out.println(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// no impedance mismatch in JDBC
				int id 		= rs.getInt(1);
				String name = rs.getString(2);
				String addr = rs.getString(3);
				System.out.println("ID = " + id 
								+  ", Name = " + name 
								+  ", Address = " + addr);
			}
			ps.close();
			rs.close();
			//conn.commit();			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
		
		// Release database resources.
		try {
			// Close the Statement object.
			stmt.close(); 
			// Close the Connection object.
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

