package com.app.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Managed_item {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Managed_item(Connection conn) {
    this.conn = conn;
  }

  public int INSERT(String Item_id, String Manager_id, String Date_time) throws IOException {
    int res = 0;
    try {
      sql = "INSERT INTO Managed_item(Item_id, Manager_id) VALUES(?, ?, TO_DATE(?, 'yyyy-mm-dd'))";
      ps = conn.prepareStatement(sql);
      ps.setString(1, Item_id);
      ps.setString(2, Manager_id);
      ps.setString(3, Date_time);

      res = ps.executeUpdate();

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }
}
