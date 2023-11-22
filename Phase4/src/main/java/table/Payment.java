package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class Payment {
	private Connection conn;
	PreparedStatement ps;
	String sql = "";

	private Payment(Connection conn) {
		this.conn = conn;
	}

	public String getLastId() {
		String lastId = "OR000000";
		ResultSet rs = null;
		try {
			String query = "SELECT order_id\r\n"
				+ "FROM payment \r\n"
				+ "order by order_id DESC\r\n"
				+ "FETCH FIRST 1 ROWS ONLY";
			ps = this.conn.prepareStatement(query);
			rs = ps.executeQuery();
			if (rs.next())
				lastId = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastId;
	}

	public static String generateNextId(String lastId) {
		int numPart = Integer.parseInt(lastId.substring(2)) + 1;
		return String.format("OR%06d", numPart);
	}

	public String INSERT(String customerId, String paymentType, String cardInfo, String chefId) {
		String orderId = "";
		try {
			sql = "INSERT PAYMENT (order_id, customer_id, total_price, payment_type, card_info, chef_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

			orderId = generateNextId(getLastId());

			ps.setString(1, orderId);
			ps.setString(2, customerId);
			ps.setInt(3, 0);
			ps.setString(4, paymentType);
			if (paymentType.equals("Credit Card")) {
				ps.setString(5, cardInfo);
			} else {
				ps.setNull(5, Types.VARCHAR);
			}
			ps.setString(6, chefId);

			ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return "error";
		}

		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderId;
	}

	public int UPDATE(String updateAttribute, String updateValue, String updateId) {
		int res = -1;
		try {
			sql = "UPDATE PAYMENT SET ? = ? WHERE order_id = ?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, updateAttribute);
			ps.setString(2, updateValue);
			ps.setString(3, updateId);

			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public int DELETE(String deleteId) {
		int res = -1;
		try {
			sql = "DELETE FROM Payment WHERE order_id = ?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, deleteId);

			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public ResultSet SELECT(String customerId) {
		ResultSet rs = null;
		try {
			sql = "SELECT * FROM PAYMENT WHERE customer_id = ?";
			ps = this.conn.prepareStatement(sql);
			ps.setString(1, customerId);

			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}
}
