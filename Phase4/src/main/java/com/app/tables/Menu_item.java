package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu_item {
	Connection conn;
	String sql = "";
	PreparedStatement ps;

	private String item_id, name;
	private int unit_price, item_quantity;
	private String category, soldout;

	public Menu_item(Connection conn) {
		this.conn = conn;
	}

	public Menu_item(String item_id, String name, int unit_price, int item_quantity, String category,
		String soldout) {
		this.item_id = item_id;
		this.name = name;
		this.unit_price = unit_price;
		this.item_quantity = item_quantity;
		this.category = category;
		this.soldout = soldout;
	}

	public String getItem_id() {
		return this.item_id;
	}

	public String getName() {
		return this.name;
	}

	public int getUnit_price() {
		return this.unit_price;
	}

	public int getItem_quantity() {
		return this.item_quantity;
	}

	public String getCategory() {
		return this.category;
	}

	public String getSoldout() {
		return this.soldout;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnit_price(int unit_price) {
		this.unit_price = unit_price;
	}

	public void setItem_quantity(int item_quantity) {
		this.item_quantity = item_quantity;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSoldout(String soldout) {
		this.soldout = soldout;
	}

	public String getlastId() {
		String lastId = "IT000000";
		try {
			String query = "SELECT mi.Item_id\r\n" + "FROM Menu_item mi\r\n"
				+ "order by mi.Item_id DESC\r\n" + "FETCH FIRST 1 ROWS ONLY";
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
		return String.format("IT%06d", numPart);
	}

	public String INSERT(String name, String category, int Unit_price) throws IOException {
		String id = null;
		try {
			id = generateNextId(getlastId());

			sql = "INSERT INTO Menu_item(Item_id, Name, Category, Unit_price, Item_quantity) VALUES(?, ?, ?, ?, 0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, category);
			ps.setInt(4, Unit_price);

			int res = ps.executeUpdate();
			// conn.commit();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public List<Menu_item> SELECT(String name, String category, String soldout) throws IOException {
		List<Menu_item> Menu_items = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM Menu_item WHERE name LIKE ? AND category LIKE ? AND soldout LIKE ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + category + "%");
			if (soldout.length() == 0)
				ps.setString(3, "_");
			else {
				ps.setString(3, soldout);
			}
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Menu_items.add(new Menu_item(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getString(6)));
				}
			}
			// conn.commit();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Menu_items;
	}

	public List<Menu_item> SELECT2(String menu_id) throws IOException {
		List<Menu_item> Menu_items = new ArrayList<>();
		ResultSet rs = null;
		try {
			sql = "SELECT mi.item_id, mi.name, mi.unit_price, mi.item_quantity, mi.category, mi.soldout\r\n"
				+ "FROM menu me, menu_item mi, contains co\r\n" + "WHERE me.menu_id = ?\r\n"
				+ "AND me.menu_id = co.menu_id\r\n" + "AND mi.item_id = co.item_id";
			ps = conn.prepareStatement(sql);
			ps.setString(1, menu_id);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					Menu_items.add(new Menu_item(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
						rs.getString(5), rs.getString(6)));
				}
			}
			// conn.commit();
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Menu_items;
	}

}
