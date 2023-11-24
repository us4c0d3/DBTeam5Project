package com.app.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Need {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Need(Connection conn) {
    this.conn = conn;
  }

  public int INSERT(String Item_id, String Ingredient_id) throws IOException {
    int res = 0;
    try {
      sql = "INSERT INTO Need(Item_id, Ingredient_id) VALUES(?, ?)";
      ps = conn.prepareStatement(sql);
      ps.setString(1, Item_id);
      ps.setString(2, Ingredient_id);

      res = ps.executeUpdate();

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }
}
