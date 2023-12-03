package com.app.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	Connection conn;
	String sql = "";
	PreparedStatement ps;

	private String customer_id;
	private String name;
	private String phonenumber;
	private String password;

	public Customer(Connection conn) {
		this.conn = conn;
	}

	public Customer(String userId, String name, String phonenumber) {
		this.customer_id = userId;
		this.name = name;
		this.phonenumber = phonenumber;
	}

	public String getCustomerId() {
		return this.customer_id;
	}

	public String getName() {
		return this.name;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public String getlastId() {
		String lastId = "CU000000";
		try {
			String query = "SELECT c.customer_id\r\n" + "FROM customer c\r\n"
				+ "order by c.customer_id DESC\r\n" + "FETCH FIRST 1 ROWS ONLY";
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
		return String.format("CU%06d", numPart);
	}

	public String INSERT(String Name, String Password, String Phone_number) {
		String id = null;
		try {
			id = generateNextId(getlastId());
			sql = "INSERT INTO CUSTOMER VALUES(?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, Name);
			ps.setString(3, Password);
			ps.setString(4, Phone_number);

			int res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't insert"); } else { System.out.println(id +
			 * " customer insert"); }
			 */

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public int UPDATE(String id, String name, String password, String phone_nubmer) {
		int res = 0;
		try {
			sql = "UPDATE CUSTOMER\r\n"
				+ "SET name = ?,\r\n"
				+ "	password = ?,\r\n"
				+ "	phone_number = ?\r\n"
				+ "WHERE customer_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, phone_nubmer);
			ps.setString(4, id);

			res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't update"); } else { System.out.println(id +
			 * " customer update"); System.out.println(Attribute + ": " + update_value); }
			 */

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public int DELETE(String id) {
		int res = 0;
		try {
			sql = "DELETE FROM CUSTOMER " + "WHERE customer_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't delete"); } else { System.out.println(id +
			 * " customer delete"); }
			 */

			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public List<Customer> SELECT(String userId, String password) {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM CUSTOMER WHERE customer_id = ? AND password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(4)));
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	public List<Customer> SELECTbyID(String userId) {
		List<Customer> customers = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM CUSTOMER WHERE customer_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(4)));
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}
}
