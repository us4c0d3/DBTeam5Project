package com.app.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.tables.Menu_item;
import com.app.tables.Payment;
import com.app.tables.TableMain;

public class OrderDML {
	TableMain tm;
	private Connection conn = null; // 다른 table과 join된 쿼리를 처리하기 위한 connection
	PreparedStatement ps;

	public OrderDML(Connection conn) {
		tm = TableMain.getInstance();
		this.conn = conn;
	}

	// id를 이용하여 customer_id, order_id, total_price, payment_type, card_info를 가져오는 쿼리
	public List<Payment> showOrder(String id) {
		List<Payment> rs = tm.payment.SELECT(id);

		return rs;
	}

	// id를 이용하여 order table의 속성 말고도 각 order의 menu_item에 대한 정보(id, name, amount, unit_price,
	// item_quantity, category, soldout)까지 모두 가져오는 쿼리
	public Object[] showOrder2(String id) {
		List<String> customerIds = new ArrayList<>();
		List<String> orderIds = new ArrayList<>();
		List<Payment> Payments = new ArrayList<>();
		List<Menu_item> Menu_items = new ArrayList<>();
		List<Integer> Amounts = new ArrayList<>();
		ResultSet rs = null;
		try {
			String sql = "SELECT p.customer_id, p.order_id, p.total_price, p.payment_type, p.card_info, mi.item_id, mi.name, po.amount, mi.unit_price, mi.item_quantity, mi.category, mi.soldout\r\n"
				+ "FROM payment p, part_of po, menu_item mi\r\n" + "WHERE p.customer_id = ?\r\n"
				+ "AND p.order_id = po.order_id\r\n" + "AND po.item_id = mi.item_id\r\n"
				+ "ORDER BY p.order_id";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					customerIds.add(rs.getString(1));
					orderIds.add(rs.getString(2));
					Payments.add(new Payment(rs.getString(3), rs.getString(4), rs.getString(5)));
					Menu_items.add(new Menu_item(rs.getString(6), rs.getString(7), rs.getInt(9),
						rs.getInt(10), rs.getString(11), rs.getString(12)));
					Amounts.add(rs.getInt(8));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object[] objects = new Object[] {customerIds, orderIds, Payments, Menu_items, Amounts};
		return objects;
	}
}
