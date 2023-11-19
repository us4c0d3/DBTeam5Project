package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Menu {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Menu(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public String getlastId() {
		String lastId = "ME000000";
		try {
			String query = "SELECT c.menu_id\r\n"
					+ "FROM menu c\r\n"
					+ "order by c.menu_id DESC\r\n"
					+ "FETCH FIRST 1 ROWS ONLY";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) 
		        lastId = rs.getString(1);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastId;
	}
	
	public static String generateNextId(String lastId) {
        int numPart = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("ME%06d", numPart);
    }
	
	public String INSERT() throws IOException {
		String id = "";
		try {
			System.out.println();
			System.out.print("start_date(yyyy-mm-dd): ");
			sc.nextLine();
			String start_date = sc.next();
			
			System.out.print("end_date(yyyy-mm-dd): ");
			String end_date = sc.next();
			
			id = generateNextId(getlastId());
			sql = "INSERT INTO MENU VALUES(?, TO_DATE(?, 'yyyy-mm-dd'), TO_DATE(?, 'yyyy-mm-dd'))";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, start_date);
			ps.setString(3, end_date);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(id + " menu insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public void UPDATE() throws IOException {
		try {
			System.out.println();
			System.out.print("Menu id: ");
			String id = sc.next();

			System.out.println();
			System.out.println("Attribute: Start_date End_date");
			System.out.print("Select Attribute: ");
			line = sc.next();
			
			System.out.printf("%s(yyyy-mm-dd): ", line);
			sc.nextLine();
			String update_value = sc.nextLine();
			
			sql = "UPDATE MENU "
					+ "SET " + line + " = TO_DATE(?, 'yyyy-mm-dd') "
					+ "WHERE Menu_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, update_value);
			ps.setString(2, id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't update");
			}
			else {
				System.out.println(id + " Menu update");
				System.out.println(line + ": " + update_value);
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}