package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Managed_item {
	Connection conn;
	Scanner sc;
	String sql = "", line = "", Item_id = "", Manager_id = "";
	PreparedStatement ps;
	
	public Managed_item(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void getItem_id(String Item_id) {
		this.Item_id = Item_id;
	}
	
	public void getManager_id(String Manager_id) {
		this.Manager_id = Manager_id;
	}
	
	public void INSERT() throws IOException {
		if (Item_id == "") {
			System.out.println("Item_id has not been inserted.");
			return;
		}
		/*if (Manager_id == "") {
			System.out.println("Manager_id has not been inserted");
		}*/
		try {
			System.out.println();
			System.out.print("Manager_id: ");
			sc.nextLine();
			Manager_id = sc.nextLine();
			
			System.out.println();
			System.out.print("Datetime(yyyy-mm-dd): ");
			sc.nextLine();
			String Date_time = sc.nextLine();
			
			sql = "INSERT INTO Managed_item(Item_id, Manager_id) VALUES(?, ?, TO_DATE(?, 'yyyy-mm-dd'))";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Item_id);
			ps.setString(2, Manager_id);
			ps.setString(3, Date_time);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(Item_id + " " + Manager_id + " Managed_item insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

