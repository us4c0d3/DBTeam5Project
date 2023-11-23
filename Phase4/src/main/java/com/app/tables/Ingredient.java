package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ingredient {
	Connection conn;
	String sql = "";
	PreparedStatement ps;

	public Ingredient(Connection conn) {
		this.conn = conn;
	}

	public int UPDATE(String id, String attribute, String update_value) throws IOException {
		int res = 0;
		try {
			sql = "UPDATE INGREDIENT "
				+ "SET " + attribute + " = ? "
				+ "WHERE ingredient_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, update_value);
			ps.setString(2, id);

			res = ps.executeUpdate();
			//			if (res == 0) {
			//				System.out.println("Can't update");
			//			}
			//			else {
			//				System.out.println(id + " ingredient update");
			//				System.out.println(attribute + ": " + update_value);
			//			}

			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}