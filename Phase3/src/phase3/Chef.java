package phase3;

import java.io.*;
import java.sql.*; // import JDBC package
import java.util.Scanner;

public class Chef {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Chef(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public static String generateNextId(String lastId) {
		int numPart = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("CH%06d", numPart);
    }
	
	public String getlastId() {
		String lastId = "CH000000";
		try {
			String query = "SELECT c.chef_id\r\n"
					+ "FROM chef c\r\n"
					+ "order by c.chef_id DESC\r\n"
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
	
	public void INSERT() throws IOException {
	    try {
	        System.out.print("1. input chef's name: ");
	        sc.nextLine();
	        String chefName = sc.nextLine();
	        System.out.print("2. input chef's phoneNumber: ");
	        String chefPhone = sc.next();

	        // Move the cursor to the first row
	        String newId = generateNextId(getlastId());

	        sql = "INSERT INTO chef (chef_id, name, phone_number) VALUES (?, ?, ?)";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, newId);
	        ps.setString(2, chefName);
	        ps.setString(3, chefPhone);

	        // Execute the update for INSERT
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println(newId + " Chef inserted successfully.");
	            // Commit the changes
	            //conn.commit();
	        } else {
	            System.out.println("Failed to insert chef.");
	        }

	        ps.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void UPDATE() throws IOException {
		try {
			System.out.println();
			System.out.print("input chef's id: ");
			String chefId = sc.next();
			
			System.out.println();
			System.out.println("Attribute: Name Phone_number");
			System.out.print("Select Attribute: ");
			String line = sc.next();
			
			System.out.printf("%s: ", line);
			sc.nextLine();
			String update_value = sc.nextLine();
			
			sql = "UPDATE CHEF "
					+ "SET " + line + " = ? "
					+ "WHERE CHEF_id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, update_value);
			ps.setString(2, chefId);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't update");
			}
			else {
				System.out.println(chefId + " chef update");
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
			System.out.print("Chef id: ");
			String chefId = sc.next();
			
			sql = "DELETE FROM CHEF "
						+ "WHERE chef_id = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, chefId);
			
			int res = ps.executeUpdate();
			if (res == 0) {
				System.out.println("Can't delete");
			}
			else {
				System.out.println(chefId + " chef delete");
			}
			
			//conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
