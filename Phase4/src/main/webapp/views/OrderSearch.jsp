<%@ page import="com.app.utils.OrderDML,java.sql.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu</title>
</head>
<body>
<%
	String serverIP = "localhost";
	String strSID = "orcl";
	String portNum = "1521";
	String user = "teamproject";
	String pass = "comp322";
	String url = "jdbc:oracle:thin:@" + serverIP + ":" + portNum + ":" + strSID;
	
	Connection conn = null;
	PreparedStatement pstmt;

	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn = DriverManager.getConnection(url, user, pass);
%>

	<form action="OrderSearch.jsp" method="post">
		<label for="id">Customer ID:</label>
  		<input type="text" id="id" name="id">
  		<input type="submit" value="Search">
	</form>
	<h2>Search Order</h2>
	<%
	OrderDML orderDML = new OrderDML(conn);
	String id = "";
	if (request.getParameter("id") != null)
	  id = request.getParameter("id");
	ResultSet rs = orderDML.showOrder2(id);
	if (rs == null) {
	  out.println("No result.");
	} else if (rs != null) {
	  out.println("<table border=\"1\">");
	  ResultSetMetaData rsmd = rs.getMetaData();
	  int cnt = rsmd.getColumnCount();
	  for (int i = 1; i <= cnt; i++) {
	    out.println("<th>" + rsmd.getColumnName(i) + "</th>");
	  }

	  while (rs.next()) {
	    out.println("<tr>");
	    out.println("<td>" + rs.getString(1) + "</td>");
	    out.println("<td>" + rs.getString(2) + "</td>");
	    out.println("<td>" + rs.getString(3) + "</td>");
	    out.println("<td>" + rs.getString(4) + "</td>");
	    out.println("<td>" + rs.getString(5) + "</td>");
	    out.println("<td>" + rs.getString(6) + "</td>");
	    out.println("<td>" + rs.getString(7) + "</td>");
	    out.println("<td>" + rs.getString(8) + "</td>");
	    out.println("<td>" + rs.getString(9) + "</td>");
	    out.println("<td>" + rs.getString(10) + "</td>");
	    out.println("<td>" + rs.getString(11) + "</td>");
	    out.println("<td>" + rs.getString(12) + "</td>");
	    out.println("</tr>");
	  }

	  out.println("</table>");
	  rs.close();
	}

	// 메뉴 아이템 조회 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>