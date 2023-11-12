package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Manager {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Manager(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public String getlastId() {
		String lastId = "MA000000";
		try {
			String query = "SELECT c.manager_id\r\n"
					+ "FROM manager c\r\n"
					+ "order by c.manager_id DESC\r\n"
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
        return String.format("MA%06d", numPart);
    }
	
	public void INSERT() throws IOException {
		try {
			System.out.println();
			System.out.print("Name: ");
			sc.nextLine();
			String Name = sc.nextLine();
			
			System.out.print("Password: ");
			String Password = sc.next();
			
			System.out.print("Phone_number: ");
			String Phone_number = sc.next();
			
			String id = generateNextId(getlastId());
			sql = "INSERT INTO MANAGER VALUES(?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, Name);
			ps.setString(3, Password);
			ps.setString(4, Phone_number);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(id + " manager insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UPDATE() throws IOException {
		try {
			System.out.println();
			System.out.println("Manager id: ");
			String id = sc.next();
			
			System.out.println();
			System.out.println("Attribute: Name Password Phone_number");
			System.out.print("Select Attribute: ");
			line = sc.next();
			
			System.out.printf("%s: ", line);
			sc.nextLine();
			String update_value = sc.nextLine();
			
			sql = "UPDATE MANAGER "
					+ "SET " + line + " = ? "
					+ "WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, update_value);
			ps.setString(2, id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't update");
			}
			else {
				System.out.println(id + " manager update");
				System.out.println(line + ": " + update_value);
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DELETE() throws IOException {
		try {
			System.out.println();
			System.out.println("Manager id: ");
			String id = sc.next();
			
			sql = "DELETE FROM MANAGER "
					+ "WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't delete");
			}
			else {
				System.out.println(id + " manager delete");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

