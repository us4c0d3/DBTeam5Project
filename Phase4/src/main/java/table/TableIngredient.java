package phase3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class TableIngredient {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public TableIngredient(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void UPDATE(String id, String attribute, String update_value) throws IOException {
		try {
//			System.out.println();
//			System.out.print("Ingredient id: ");
//			String id = sc.next();

//			System.out.println();
//			System.out.println("Attribute: Unit_price Quantity");
//			System.out.print("Select Attribute: ");
//			line = sc.next();
			
//			System.out.printf("%s: ", line);
//			sc.nextLine();
//			String update_value = sc.nextLine();
			
			sql = "UPDATE INGREDIENT "
					+ "SET " + attribute + " = ? "
					+ "WHERE ingredient_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, update_value);
			ps.setString(2, id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't update");
			}
			else {
				System.out.println(id + " ingredient update");
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
