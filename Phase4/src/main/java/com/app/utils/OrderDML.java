package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.app.tables.TableMain;

public class OrderDML {
  TableMain tm;
  private Connection conn = null;
  PreparedStatement ps;

  public OrderDML(Connection conn) {
    tm = new TableMain();
    this.conn = conn;
  }

  public ResultSet showOrder(String id) {
    ResultSet rs = tm.payment.SELECT(id);

    return rs;
  }

  public ResultSet showOrder2(String id) {
    ResultSet rs = null;
    try {
      String sql =
          "SELECT p.customer_id, p.order_id, p.total_price, p.payment_type, p.card_info, mi.item_id, mi.name, po.amount, mi.unit_price, mi.item_quantity, mi.category, mi.soldout\r\n"
              + "FROM payment p, part_of po, menu_item mi\r\n" + "WHERE p.customer_id = ?\r\n"
              + "AND p.order_id = po.order_id\r\n" + "AND po.item_id = mi.item_id\r\n"
              + "ORDER BY p.order_id";
      ps = this.conn.prepareStatement(sql);
      ps.setString(1, id);
      rs = ps.executeQuery();

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return rs;
  }
}
