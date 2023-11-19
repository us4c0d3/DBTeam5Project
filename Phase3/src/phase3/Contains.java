package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Contains {
	Connection conn;
	Scanner sc;
	String sql = "", line = "", Menu_id = "";
	PreparedStatement ps;
	public Contains(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void getMenu_id(String Menu_id) {
		this.Menu_id = Menu_id;
	}
	
	public void INSERT() throws IOException {
		if (Menu_id == "") {
			System.out.println("Menu_id has not been inserted.");
			return;
		}
		try {
			System.out.println();
			System.out.print("Menu item id: ");
			sc.nextLine();
			String Item_id = sc.nextLine();
			
			
			sql = "INSERT INTO Contains(Menu_id, Item_id) VALUES(?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Menu_id);
			ps.setString(2, Item_id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(Menu_id + " " + Item_id + " Contains insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

