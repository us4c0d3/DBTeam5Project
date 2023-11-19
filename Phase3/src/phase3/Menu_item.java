package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Menu_item {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Menu_item(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public String getlastId() {
		String lastId = "IT000000";
		try {
			String query = "SELECT c.Item_id\r\n"
					+ "FROM customer c\r\n"
					+ "order by c.Item_id DESC\r\n"
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
			System.exit(1);
		}
		return lastId;
	}
	
	public static String generateNextId(String lastId) {
        int numPart = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("IT%06d", numPart);
    }	
	
	public String INSERT() throws IOException {
		String id = "";
		try {
			System.out.println();
			System.out.print("Name: ");
			sc.nextLine();
			String Name = sc.nextLine();
			
			System.out.print("Category: ");
			String Category = sc.next();
			
			id = generateNextId(getlastId());
			sql = "INSERT INTO Menu_item(Item_id, Name, Category) VALUES(?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, Name);
			ps.setString(3, Category);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(id + " Menu_item insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}

