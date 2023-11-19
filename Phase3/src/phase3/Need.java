package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Need {
	Connection conn;
	Scanner sc;
	String sql = "", line = "", Item_id = "";
	PreparedStatement ps;
	public Need(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void getItem_id(String Item_id) {
		this.Item_id = Item_id;
	}
	
	public void INSERT() throws IOException {
		if (Item_id == "") {
			System.out.println("Item_id has not been inserted.");
			return;
		}
		try {
			System.out.println();
			System.out.print("Ingredient id: ");
			sc.nextLine();
			String Ingredient_id = sc.nextLine();
			
			
			sql = "INSERT INTO Need(Item_id, Ingredient_id) VALUES(?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Item_id);
			ps.setString(2, Ingredient_id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(Item_id + " " + Ingredient_id + " Need insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

