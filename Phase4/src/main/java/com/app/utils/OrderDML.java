package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.app.tables.TableMain;

public class OrderDML {
  TableMain tm;
  private Connection conn = null; // 다른 table과 join된 쿼리를 처리하기 위한 connection
  PreparedStatement ps;

  public OrderDML(Connection conn) {
    tm = new TableMain();
    this.conn = conn;
  }

  // id를 이용하여 customer_id, order_id, total_price, payment_type, card_info를 가져오는 쿼리
  public ResultSet showOrder(String id) {
    ResultSet rs = tm.payment.SELECT(id);

    return rs;
  }

  // id를 이용하여 order table의 속성 말고도 각 order의 menu_item에 대한 정보(id, name, amount, unit_price,
  // item_quantity, category, soldout)까지 모두 가져오는 쿼리
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
