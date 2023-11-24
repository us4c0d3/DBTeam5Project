package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Menu(Connection conn) {
    this.conn = conn;
  }

  public String getlastId() {
    String lastId = "ME000000";
    try {
      String query = "SELECT c.menu_id\r\n" + "FROM menu c\r\n" + "order by c.menu_id DESC\r\n"
          + "FETCH FIRST 1 ROWS ONLY";
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
    return String.format("ME%06d", numPart);
  }


  public String INSERT(String start_date, String end_date) throws IOException {
    String id = "";
    try {
      id = generateNextId(getlastId());
      sql = "INSERT INTO MENU VALUES(?, TO_DATE(?, 'yyyy-mm-dd'), TO_DATE(?, 'yyyy-mm-dd'))";
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      ps.setString(2, start_date);
      ps.setString(3, end_date);

      int res = ps.executeUpdate();
      // conn.commit();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return id;
  }

  public int UPDATE(String id, String attribute, String update_value) throws IOException {
    int res = 0;
    try {
      sql = "UPDATE MENU " + "SET " + attribute + " = ? " + "WHERE Menu_id = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, update_value);
      ps.setString(2, id);

      res = ps.executeUpdate();
      // if (res == 0) {
      // System.out.println("Can't update");
      // }
      // else {
      // System.out.println(id + " Menu update");
      // System.out.println(attribute + ": " + update_value);
      // }

      // conn.commit();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }

  public ResultSet SELECT(String start_date, String end_date) throws IOException {
    ResultSet rs = null;
    try {
      sql =
          "SELECT * FROM Menu WHERE start_date = TO_DATE(?, 'yyyy-mm-dd') AND end_date = TO_DATE(?, 'yyyy-mm-dd')";
      ps = conn.prepareStatement(sql);
      ps.setString(1, start_date);
      ps.setString(2, end_date);

      rs = ps.executeQuery();
      // conn.commit();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rs;
  }
}
