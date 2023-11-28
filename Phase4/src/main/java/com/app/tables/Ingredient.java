package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ingredient {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  private String name;
  private int unit_price;
  private int quantity;

  public Ingredient(Connection conn) {
    this.conn = conn;
  }

  public Ingredient(String name, int unit_price, int quantity) {
    this.name = name;
    this.unit_price = unit_price;
    this.quantity = quantity;
  }

  public String getName() {
    return this.name;
  }

  public int getUnit_price() {
    return this.unit_price;
  }

  public int getQuantity() {
    return this.quantity;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUnit_price(int unit_price) {
    this.unit_price = unit_price;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int UPDATE(String id, String attribute, String update_value) throws IOException {
    int res = 0;
    try {
      sql = "UPDATE INGREDIENT " + "SET " + attribute + " = ? " + "WHERE ingredient_id = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, update_value);
      ps.setString(2, id);

      res = ps.executeUpdate();
      // conn.commit();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }

  public List<Ingredient> SELECT(String id) throws IOException {
    List<Ingredient> Ingredients = new ArrayList<>();
    ResultSet rs = null;
    try {
      sql = "SELECT * FROM Menu WHERE ingredient_id LIKE ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, "%" + id + "%");

      rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          Ingredients.add(new Ingredient(rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }
      }
      // conn.commit();
      rs.close();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return Ingredients;
  }
}
