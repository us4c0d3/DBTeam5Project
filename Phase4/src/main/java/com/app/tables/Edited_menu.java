package com.app.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Edited_menu {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Edited_menu(Connection conn) {
    this.conn = conn;
  }

  public int INSERT(String Menu_id, String Manager_id) throws IOException {
    int res = 0;
    try {
      sql = "INSERT INTO Edited_menu(Menu_id, Manager_id) VALUES(?, ?)";
      ps = conn.prepareStatement(sql);
      ps.setString(1, Menu_id);
      ps.setString(2, Manager_id);

      res = ps.executeUpdate();

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }
}
