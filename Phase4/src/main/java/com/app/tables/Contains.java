package com.app.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contains {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Contains(Connection conn) {
    this.conn = conn;
  }

  public int INSERT(String Menu_id, String Item_id) throws IOException {
    int res = 0;
    try {
      sql = "INSERT INTO Contains(Menu_id, Item_id) VALUES(?, ?)";
      ps = conn.prepareStatement(sql);
      ps.setString(1, Menu_id);
      ps.setString(2, Item_id);

      res = ps.executeUpdate();

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return res;
  }
}
