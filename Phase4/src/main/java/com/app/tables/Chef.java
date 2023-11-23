package com.app.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chef {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Chef(Connection conn) {
    this.conn = conn;
  }

  public static String generateNextId(String lastId) {
    int numPart = Integer.parseInt(lastId.substring(2)) + 1;
    return String.format("CH%06d", numPart);
  }

  public String getlastId() {
    String lastId = "CH000000";
    try {
      String query = "SELECT c.chef_id\r\n" + "FROM chef c\r\n" + "order by c.chef_id DESC\r\n"
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
      System.exit(1);
    }
    return lastId;
  }

  public String INSERT(String Name, String Phone_number) {
    String id = null;
    try {
      id = generateNextId(getlastId());

      sql = "INSERT INTO chef (chef_id, name, phone_number) VALUES (?, ?, ?)";
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      ps.setString(2, Name);
      ps.setString(3, Phone_number);

      // Execute the update for INSERT
      int res = ps.executeUpdate();
      /*
       * if (res > 0) { System.out.println(id + " Chef inserted successfully."); // Commit the
       * changes // conn.commit(); } else { System.out.println("Failed to insert chef."); }
       */
      ps.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  public int UPDATE(String id, String Attribute, String update_value) {
    int res = 0;
    try {
      sql = "UPDATE CHEF " + "SET " + Attribute + " = ? " + "WHERE CHEF_id = ?";

      ps = conn.prepareStatement(sql);
      ps.setString(1, update_value);
      ps.setString(2, id);

      res = ps.executeUpdate();
      /*
       * if (res == 0) { System.out.println("Can't update"); } else { System.out.println(id +
       * " chef update"); System.out.println(Attribute + ": " + update_value); }
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
      sql = "DELETE FROM CHEF " + "WHERE chef_id = ?";

      ps = conn.prepareStatement(sql);
      ps.setString(1, id);

      res = ps.executeUpdate();
      /*
       * if (res == 0) { System.out.println("Can't delete"); } else { System.out.println(id +
       * " chef delete"); }
       */
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }


}
