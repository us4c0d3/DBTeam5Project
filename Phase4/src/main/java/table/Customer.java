package table;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
  Connection conn;
  String sql = "";
  PreparedStatement ps;

  public Customer(Connection conn) {
    this.conn = conn;
  }

  public String getlastId() {
    String lastId = "CU000000";
    try {
      String query = "SELECT c.customer_id\r\n" + "FROM customer c\r\n"
          + "order by c.customer_id DESC\r\n" + "FETCH FIRST 1 ROWS ONLY";
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
    return String.format("CU%06d", numPart);
  }

  public String INSERT(String Name, String Password, String Phone_number) throws IOException {
    String id = null;
    try {
      id = generateNextId(getlastId());
      sql = "INSERT INTO CUSTOMER VALUES(?, ?, ?, ?)";
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);
      ps.setString(2, Name);
      ps.setString(3, Password);
      ps.setString(4, Phone_number);

      int res = ps.executeUpdate();
      /*
      if (res == 0) {
        System.out.println("Can't insert");
      } else {
        System.out.println(id + " customer insert");
      }
      */
      
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return id;
  }

  public int UPDATE(String id, String Attribute, String update_value) throws IOException {
    int res = 0;
    try {
      sql = "UPDATE CUSTOMER " + "SET " + Attribute + " = ? " + "WHERE customer_id = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, update_value);
      ps.setString(2, id);

      res = ps.executeUpdate();
      /*
      if (res == 0) {
        System.out.println("Can't update");
      } else {
        System.out.println(id + " customer update");
        System.out.println(Attribute + ": " + update_value);
      }
      */
      
      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }

  public int DELETE(String id) throws IOException {
    int res = 0;
    try {
      sql = "DELETE FROM CUSTOMER " + "WHERE customer_id = ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, id);

      res = ps.executeUpdate();
      /*
      if (res == 0) {
        System.out.println("Can't delete");
      } else {
        System.out.println(id + " customer delete");
      }
      */

      ps.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return res;
  }
}

