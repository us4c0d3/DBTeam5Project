package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu_item {
	Connection conn;
	String sql = "";
	PreparedStatement ps;

	public Menu_item(Connection conn) {
		this.conn = conn;
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

	public String INSERT(String name, String category) throws IOException {
		String id = null;
		try {
			id = generateNextId(getlastId());

			sql = "INSERT INTO Menu_item(Item_id, Name, Category) VALUES(?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, category);

			int res = ps.executeUpdate();
			//			if (res == 0) {
			//				System.out.println("Can't insert");
			//			}
			//			else {
			//				System.out.println(id + " Menu_item insert");
			//			}

			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
