package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Login {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Login(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void Run() throws IOException {
		try {
			System.out.println();
			System.out.print("Customer Id: ");
			sc.nextLine();
			String id = sc.nextLine();
			
			System.out.print("Password: ");
			String password = sc.next();
			
			sql = "SELECT *\r\n"
					+ "FROM CUSTOMER\r\n"
					+ "WHERE customer_id = ?\r\n"
					+ "AND password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Login failed");
			}
			else {
				System.out.println("Login succeeded");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

