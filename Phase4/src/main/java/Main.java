import java.sql.ResultSet;
import java.sql.SQLException;

import com.app.tables.TableMain;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Web Application Run");

		// test code
		TableMain tm = new TableMain();
		ResultSet rs = tm.payment.SELECT("CU000001");
		try {
			while (rs.next()) {
				String order_id = rs.getString(1);
				System.out.println(order_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
