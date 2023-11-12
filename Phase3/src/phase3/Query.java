package phase3;

import java.io.*;
import java.util.Scanner;
import java.sql.*; // import JDBC package

public class Query {
	Connection conn;
	Scanner sc;
	String sql = "", line = "";
	PreparedStatement ps;
	public Query(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	public void Q2() throws IOException {
		try {
			System.out.print("payment_type: ");
			String paymentType = sc.next();
		    
		    sql = "SELECT distinct cu.name, cu.phone_number " +
		                 "FROM customer cu, payment p " +
		                 "WHERE cu.customer_id = p.customer_id " +
		                 "AND p.payment_type = ?";
		    
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
			System.out.print("max_ingredient_Count: ");
			int ingredientCount = sc.nextInt();
		    
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
			System.out.print("soldoutValue: ");
			String soldoutValue = sc.next();

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
			System.out.print("startDate: ");
			String startDate = sc.next();
			System.out.print("endDate: ");
		    String endDate = sc.next();

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
			System.out.print("menu_item_name: ");
			String menu_item_name = sc.next();
			
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
			System.out.print("menu_item_name: ");
			String menu_item_name = sc.next();
			
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
	
	public void Q12() throws IOException {
		try {
			System.out.print("customer_id: ");
			String customerID = sc.next();
			
			String sql = "SELECT p.order_id, SUM(mi.unit_price * po.amount)\n"
		            + "FROM customer c\n"
		            + "JOIN payment p ON c.customer_id = p.customer_id\n"
		            + "JOIN part_of po ON p.order_id = po.order_id AND p.customer_id = po.customer_id\n"
		            + "JOIN menu_item mi ON po.item_id = mi.item_id\n"
		            + "WHERE c.customer_id = ?\n"
		            + "GROUP BY p.order_id\n"
		            + "ORDER BY p.order_id";
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, customerID);
	         
	         // System.out.println(sql);
	         
	         ResultSet rs = ps.executeQuery();
	         
	         System.out.println("<< query 12 result >>");
	         System.out.printf("%-12s | %-9s\n", "Order_id", "Total Price");
	         System.out.println("--------------------------");
	         while(rs.next()) {
	            System.out.printf("%-12s | %-9.2f\n", rs.getString(1), rs.getDouble(2));
	         }
	         rs.close();
	         System.out.println();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Q13() throws IOException {
		try {
			System.out.print("Category: ");
			String category = sc.next();
			
			String sql = "SELECT p.order_id, p.customer_id, p.total_price\n"
		            + "FROM PAYMENT p\n"
		            + "WHERE p.total_price > (\n"
		            + "    SELECT AVG(mi.unit_price)\n"
		            + "    FROM menu_item mi\n"
		            + "    WHERE mi.category = ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
	         ps.setString(1, category);
	         ResultSet rs = ps.executeQuery();
	         
	         System.out.println("<< query 13 result >>");
	         System.out.printf("%-12s | %-12s | %s\n", "Order_id", "Customer_id", "Total Price");
	         System.out.println("--------------------------------------");
	         while(rs.next()) {
	            System.out.printf("%-12s | %-12s | %.2f\n", rs.getString(1), rs.getString(2), rs.getDouble(3));
	         }
	         rs.close();
	         System.out.println();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void Q17() throws IOException {
		try {
			System.out.print("How many rows to print: ");
			int num = sc.nextInt();

			String sql = "SELECT inlview.item_name, inlview.order_count\r\n"
		            + "   FROM (\r\n"
		            + "       SELECT mi.name AS item_name, COUNT(p.item_id) AS order_count\r\n"
		            + "       FROM menu_item mi\r\n"
		            + "       LEFT JOIN part_of p ON mi.item_id = p.item_id\r\n"
		            + "       GROUP BY mi.name\r\n"
		            + "   ) inlview\r\n"
		            + "   ORDER BY inlview.order_count DESC\r\n"
		            + "   FETCH FIRST ? ROWS ONLY";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			ResultSet rs = ps.executeQuery();
			
			System.out.println("<< query 17 result >>");
			System.out.printf("%-12s | %s\n", "Item Name", "Order Count");
			System.out.println("--------------------------------------");
			while(rs.next()) {
				System.out.printf("%-12s | %d\n", rs.getString(1), rs.getInt(2));
			}
		         rs.close();
		         System.out.println();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void Q18() throws IOException {
		try {
			System.out.print("Total_price: ");
			int Total_price = sc.nextInt();
			
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
	
	public void Q19() throws IOException {
		try {
			System.out.print("How many items to print the manager who managed them? : ");
			int num = sc.nextInt();

			String sql = "SELECT ma.name, ma.phone_number\r\n"
		            + "FROM manager ma\r\n"
		            + "WHERE ma.manager_id IN (\r\n"
		            + "    SELECT mg.manager_id\r\n"
		            + "    FROM managed_item mg, menu_item mi\r\n"
		            + "    WHERE mi.item_id = mg.item_id\r\n"
		            + "    GROUP BY mg.manager_id, mi.category\r\n"
		            + "    HAVING COUNT(*) >= ?)\r\n"
		            + "ORDER BY ma.name";
			
			PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, num);
	        ResultSet rs = ps.executeQuery();
	        
	        System.out.println("<< Query 19 Result >>");
	        System.out.printf("%-30s | %s\n", "Manager Name", "Phone Number");
	        System.out.println("---------------------------------------------------------");
	        while(rs.next()) {
	        	System.out.printf("%-30s | %s\n", rs.getString(1), rs.getString(2));
	        }
	        rs.close(); 
	        System.out.println();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void Q20() throws IOException {
		try {
			System.out.print("customer_id: ");
			String customer_id = sc.next();
			
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