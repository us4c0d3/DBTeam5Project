package table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Payment {
	private Connection conn;
	PreparedStatement ps;
	String sql = "";

	private Payment(Connection conn) {
		this.conn = conn;
	}

	public ResultSet INSERT() {

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

		ps.close();
		return res;
	}
}
