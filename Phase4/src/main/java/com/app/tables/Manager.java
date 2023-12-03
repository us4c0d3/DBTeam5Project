package com.app.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager {
	Connection conn;
	String sql = "";
	PreparedStatement ps;

	private String manager_id;
	private String name;
	private String phonenumber;
	private String password;

	public Manager(Connection conn) {
		this.conn = conn;
	}

	public Manager(String userId, String name, String phonenumber) {
		this.manager_id = userId;
		this.name = name;
		this.phonenumber = phonenumber;
	}

	public String getManagerId() {
		return this.manager_id;
	}

	public String getName() {
		return this.name;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public String getlastId() {
		String lastId = "MA000000";
		try {
			String query = "SELECT c.manager_id\r\n" + "FROM manager c\r\n"
				+ "order by c.manager_id DESC\r\n" + "FETCH FIRST 1 ROWS ONLY";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				lastId = rs.getString(1);
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastId;
	}

	public static String generateNextId(String lastId) {
		int numPart = Integer.parseInt(lastId.substring(2)) + 1;
		return String.format("MA%06d", numPart);
	}

	public String INSERT(String Name, String Password, String Phone_number) {
		String id = null;
		try {
			id = generateNextId(getlastId());
			sql = "INSERT INTO MANAGER VALUES(?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, Name);
			ps.setString(3, Password);
			ps.setString(4, Phone_number);

			int res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't insert"); } else { System.out.println(id +
			 * " manager insert"); }
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
			sql = "UPDATE MANAGER\r\n"
				+ "SET name = ?,\r\n"
				+ "	password = ?,\r\n"
				+ "	phone_number = ?\r\n"
				+ "WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setString(3, phone_nubmer);
			ps.setString(4, id);

			res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't update"); } else { System.out.println(id +
			 * " manager update"); System.out.println(Attribute + ": " + update_value); }
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
			sql = "DELETE FROM MANAGER " + "WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			res = ps.executeUpdate();
			/*
			 * if (res == 0) { System.out.println("Can't delete"); } else { System.out.println(id +
			 * " manager delete"); }
			 */
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public List<Manager> SELECT(String id, String password) {
		List<Manager> managers = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM MANAGER WHERE manager_id = ? AND password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, password);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					managers.add(new Manager(rs.getString(1), rs.getString(2), rs.getString(4)));
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managers;
	}

	public List<Manager> SELECTbyID(String id) {
		List<Manager> managers = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM MANAGER WHERE manager_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					managers.add(new Manager(rs.getString(1), rs.getString(2), rs.getString(4)));
				}
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return managers;
	}
}
