package com.app.tables;

import java.io.IOException;
// import JDBC package
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
  Connection conn;
  String sql = "";
  PreparedStatement ps;
  private String menu_id;
  private Date start_date;
  private Date end_date;

  public Menu(Connection conn) {
    this.conn = conn;
  }

  public Menu(String menu_id, Date start_date, Date end_date) {
    this.menu_id = menu_id;
    this.start_date = start_date;
    this.end_date = end_date;
  }

  public String getMenu_id() {
    return this.menu_id;
  }

  public Date getStart_date() {
    return this.start_date;
  }

  public Date getEnd_date() {
    return this.end_date;
  }

  public void setMenu_id(String menu_id) {
    this.menu_id = menu_id;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
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

  public List<Menu> SELECT(String start_date, String end_date) throws IOException { // start_date,
                                                                                    // end_date로
                                                                                    // tuple 추출
    List<Menu> Menus = new ArrayList<>();
    ResultSet rs = null;
    try {
      sql =
          "SELECT * FROM Menu WHERE start_date = TO_DATE(?, 'yyyy-mm-dd') AND end_date = TO_DATE(?, 'yyyy-mm-dd')";
      ps = conn.prepareStatement(sql);
      ps.setString(1, start_date);
      ps.setString(2, end_date);

      rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          Menus.add(new Menu(rs.getString(1), rs.getDate(2), rs.getDate(3)));
        }
      }
      // conn.commit();
      rs.close();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return Menus;
  }

  public List<Menu> SELECT2() throws IOException { // 모든 tuple 추출
    List<Menu> Menus = new ArrayList<>();
    ResultSet rs = null;
    try {
      sql = "SELECT * FROM Menu ORDER BY start_date";
      ps = conn.prepareStatement(sql);

      rs = ps.executeQuery();
      if (rs != null) {
        while (rs.next()) {
          Menus.add(new Menu(rs.getString(1), rs.getDate(2), rs.getDate(3)));
        }
      }
      // conn.commit();
      rs.close();
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return Menus;
  }
}
