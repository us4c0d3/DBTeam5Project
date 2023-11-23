package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Manager(Connection conn) {
    this.conn = conn;
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

  public int UPDATE(String id, String Attribute, String update_value) {
    int res = 0;
    try {
      sql = "UPDATE MANAGER " + "SET " + Attribute + " = ? " + "WHERE manager_id = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, update_value);
      ps.setString(2, id);

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

  public ResultSet SELECT(String id, String password) {
    ResultSet res = null;
    try {
      sql = "SELECT * FROM MANAGER WHERE manager_id = ? AND password = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      ps.setString(2, password);

      res = ps.executeQuery();
      /*
       * if (res == 0) { System.out.println("Can't delete"); } else { System.out.println(id +
       * " customer delete"); }
       */

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }
}

