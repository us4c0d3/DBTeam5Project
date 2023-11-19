package phase3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Edited_menu {
	Connection conn;
	Scanner sc;
	String sql = "", line = "", Menu_id = "", Manager_id = "";
	PreparedStatement ps;
	
	public Edited_menu(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void getMenu_id(String Menu_id) {
		this.Menu_id = Menu_id;
	}
	
	public void getManager_id(String Manager_id) {
		this.Manager_id = Manager_id;
	}
	
	public void INSERT() throws IOException {
		if (Menu_id == "") {
			System.out.println("Menu_id has not been inserted.");
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
			
			sql = "INSERT INTO Edited_menu(Menu_id, Manager_id) VALUES(?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Menu_id);
			ps.setString(2, Manager_id);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't insert");
			}
			else {
				System.out.println(Menu_id + " " + Manager_id + " Edited_menu insert");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

