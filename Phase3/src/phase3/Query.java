package phase3;

import java.io.*;
import java.sql.*; // import JDBC package

public class Query {
	Connection conn;
	BufferedReader br;
	String sql = "", line = "";
	PreparedStatement ps;
	public Query(Connection conn, BufferedReader br) {
		this.conn = conn;
		this.br = br;
	}
	
	public void Q2() throws IOException {
		try {
			System.out.println("payment_type: ");
			line = br.readLine();
			String paymentType = line.toString();
		    
		    sql = "SELECT distinct cu.name, cu.phone_number " +
		                 "FROM customer cu, payment p " +
		                 "WHERE cu.customer_id = p.customer_id " +
		                 "AND p.payment_type =  ?";
		    
		    ps = conn.prepareStatement(sql);
		    ps.setString(1, paymentType);
		    
		    ResultSet rs = ps.executeQuery();
		    
		    System.out.println("<< Query 2 Result >>");
		    System.out.println(" Name   |  Phone_number");
		    System.out.println("----------------------------------------------------------------------");
		    while (rs.next()) {
		        String name = rs.getString(1);
		        String phoneNumber = rs.getString(2);
		       
		        System.out.println(name
		        		+  "     |     " + phoneNumber);
		    }
		    ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Q3() throws IOException {
		try {
			System.out.println("max_ingredient_Count: ");
			line = br.readLine();
			int ingredientCount = Integer.parseInt(line);
		    
		    sql = "SELECT mi.name, COUNT(*) as count " +
	                 "FROM menu_item mi, ingredient i, need n " +
	                 "WHERE n.item_id = mi.item_id " +
	                 "AND n.ingredient_id = i.ingredient_id " +
	                 "GROUP BY mi.name, mi.item_id " +
	                 "HAVING COUNT(*) <= ?";
		    
		    ps = conn.prepareStatement(sql);
		    ps.setInt(1, ingredientCount);
		    ResultSet rs = ps.executeQuery();
		    
		    System.out.println("<< Query 3 Result >>");
		    System.out.println(" Name   |  count");
		    System.out.println("----------------------------------------------------------------------");
		    while (rs.next()) {
		        String name = rs.getString(1);
		        Integer count = rs.getInt(2);
		       
		        System.out.println(name
		        		+ "     |     " + count);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Q4() throws IOException {
		try {
			System.out.println("soldoutValue: ");
			line = br.readLine();
			String soldoutValue = line.toString();

		    String subquery = "SELECT po.customer_id " +
		                      "FROM menu_item mi, part_of po " +
		                      "WHERE mi.item_id = po.item_id " +
		                      "AND mi.soldout = ?";

		    sql = "SELECT cu.customer_id, cu.name " +
		                      "FROM customer cu " +
		                      "WHERE cu.customer_id IN (" + subquery + ")";

		    ps = conn.prepareStatement(sql);
		    ps.setString(1, soldoutValue);

		    ResultSet rs = ps.executeQuery();

		    System.out.println("<< Query 4 Result >>");
		    System.out.println("customer_id   |   name");
		    System.out.println("----------------------------------------------------------------------");
		    while (rs.next()) {
		        String customerId = rs.getString(1);
		        String name = rs.getString(2);

		        System.out.println(customerId
		        		+ "     |     " + name);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void Q6() throws IOException {
		try {
			System.out.println("startDate: ");
			line = br.readLine();
			String startDate = line.toString();
			System.out.println("endDate: ");
			line = br.readLine();
		    String endDate = line.toString();

		    sql = "SELECT mi.name " +
		                     "FROM menu_item mi " +
		                     "WHERE (mi.item_id) IN (" +
		                     "SELECT co.item_id " +
		                     "FROM contains co, menu me " +
		                     "WHERE co.menu_id = me.menu_id " +
		                     "AND me.start_date >= TO_DATE(?)" +
		                     "AND me.end_date <= TO_DATE(?))";
		    
		    
		    ps = conn.prepareStatement(sql);
		    ps.setString(1, startDate);
		    ps.setString(2, endDate);

		    ResultSet rs = ps.executeQuery();

		    System.out.println("<< Query 6 Result >>");
		    System.out.println("name");
		    System.out.println("----------------------------------------------------------------------");
		    while (rs.next()) {
		        String name = rs.getString(1);
		        System.out.println(name);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Q7() throws IOException {
		try {
			System.out.println("menu_item_name: ");
			line = br.readLine();
			String menu_item_name = line.toString();
			
			sql = "SELECT mi.name, mi.item_quantity, iv.total_ingredient_quantity\n"
					+ "FROM menu_item mi\n"
					+ "JOIN (\n"
					+ "    SELECT n.item_id, SUM(i.quantity) AS total_ingredient_quantity\n"
					+ "    FROM need n, ingredient i\n"
					+ "    WHERE n.ingredient_id = i.ingredient_id\n"
					+ "    GROUP BY n.item_id\n"
					+ ") iv ON mi.item_id = iv.item_id\n"
					+ "WHERE mi.name = ?";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, menu_item_name);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("<< query 7 result >>");
			System.out.println("menu_item_name     | item_quantity     | total_ingredient_quantity");
			System.out.println("----------------------------------------------------------------------");
			while(rs.next()) {
				String name = rs.getString(1);
				int item_quantity = rs.getInt(2);
				int total_ingredient_quantity = rs.getInt(3);
				System.out.println(name 
						+  "     |     " + item_quantity
						+  "     |     " + total_ingredient_quantity);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Q9() throws IOException {
		try {
			System.out.println("menu_item_name: ");
			line = br.readLine();
			String menu_item_name = line.toString();
			
			sql = "SELECT m.name AS manager_name, mi.name AS menu_item_name, SUM(po.amount) AS total_ordered\n"
					+ "FROM menu_item mi\n"
					+ "JOIN managed_item mani ON mi.item_id = mani.item_id\n"
					+ "JOIN manager m ON mani.manager_id = m.manager_id\n"
					+ "LEFT JOIN part_of po ON mi.item_id = po.item_id\n"
					+ "WHERE mi.name = ?\n"
					+ "GROUP BY m.name, mi.name\n"
					+ "ORDER BY m.name, total_ordered DESC";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, menu_item_name);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("<< query 9 result >>");
			System.out.println("manager_name     | menu_item_name     | total_ordered");
			System.out.println("----------------------------------------------------------------------");
			while(rs.next()) {
				String manager_name = rs.getString(1);
				String menu_item_name2 = rs.getString(2);
				int total_ordered = rs.getInt(3);
				System.out.println(manager_name 
						+  "     |     " + menu_item_name2
						+  "     |     " + total_ordered);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Q18() throws IOException {
		try {
			System.out.println("Total_price: ");
			line = br.readLine();
			int Total_price = Integer.parseInt(line);
			
			sql = "SELECT c.name AS customer_name, mi.name AS menu_item_name, m.name AS manager_name\n"
					+ "FROM customer c, payment p, part_of po, menu_item mi, managed_item mani, manager m\n"
					+ "WHERE c.customer_id = p.customer_id\n"
					+ "AND p.order_id = po.order_id\n"
					+ "AND po.item_id = mi.item_id\n"
					+ "AND mi.item_id = mani.item_id\n"
					+ "AND mani.manager_id = m.manager_id\n"
					+ "AND p.Total_price >= ?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Total_price);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("<< query 18 result >>");
			System.out.println("customer_name     | menu_item_name     | manager_name");
			System.out.println("----------------------------------------------------------------------");
			while(rs.next()) {
				String customer_name = rs.getString(1);
				String menu_item_name = rs.getString(2);
				String manager_name = rs.getString(3);
				System.out.println(customer_name 
						+  "     |     " + menu_item_name
						+  "     |     " + manager_name);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Q20() throws IOException {
		try {
			System.out.println("customer_id: ");
			line = br.readLine();
			String customer_id = line.toString();
			
			sql = "SELECT c.name AS customer_name, mi.name AS menu_item_name, ch.name AS chef_name\n"
					+ "FROM customer c, payment p, part_of po, menu_item mi, chef ch\n"
					+ "WHERE c.customer_id = p.customer_id\n"
					+ "AND p.order_id = po.order_id\n"
					+ "AND po.item_id = mi.item_id\n"
					+ "AND p.chef_id = ch.chef_id\n"
					+ "AND c.customer_id = ?\n"
					+ "ORDER BY ch.name";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, customer_id);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("<< query 20 result >>");
			System.out.println("customer_name     | menu_item_name     | chef_name");
			System.out.println("----------------------------------------------------------------------");
			while(rs.next()) {
				String customer_name = rs.getString(1);
				String menu_item_name = rs.getString(2);
				String chef_name = rs.getString(3);
				System.out.println(customer_name 
						+  "     |     " + menu_item_name
						+  "     |     " + chef_name);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
