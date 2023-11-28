<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu</title>
</head>
<body>
	<form action="ShowMenu.jsp" method="post">
		Date: <select name="start_date">
			<%
			MenuDML mDML = new MenuDML();
			List<Menu> DateList = mDML.showMenu_v2();
			if (DateList != null) {
			  for (int i = 0; i < DateList.size(); i++) {
			    out.println("<option value=\"" + DateList.get(i).getStart_date() + "\">"
			    + DateList.get(i).getStart_date() + " ~ " + DateList.get(i).getEnd_date() + "</option>");
			  }
			}
			%>
		</select><br> <input type="submit" name="Submit" />
	</form>
	<h2>Menu</h2>
	<%
	String start_date = "";
	if (request.getParameter("start_date") != null) {
	  start_date = request.getParameter("start_date");
	  List<Menu_item> rs = mDML.showMenu_item_v2(start_date);
	  if (rs == null) {
	    out.println("No result.");
	  } else if (rs != null) {
	    out.println("<table border=\"1\">");
	    out.println("<th>" + "ITEM_ID" + "</th>");
	    out.println("<th>" + "NAME" + "</th>");
	    out.println("<th>" + "UNIT_PRICE" + "</th>");
	    out.println("<th>" + "ITEM_QUANTITY" + "</th>");
	    out.println("<th>" + "CATEGORY" + "</th>");
	    out.println("<th>" + "SOLDOUT" + "</th>");

	    for (int i = 0; i < rs.size(); i++) {
	  out.println("<tr>");
	  out.println("<td>" + rs.get(i).getItem_id() + "</td>");
	  out.println("<td>" + rs.get(i).getName() + "</td>");
	  out.println("<td>" + Double.toString(rs.get(i).getUnit_price()) + "</td>");
	  out.println("<td>" + Double.toString(rs.get(i).getItem_quantity()) + "</td>");
	  out.println("<td>" + rs.get(i).getCategory() + "</td>");
	  out.println("<td>" + rs.get(i).getSoldout() + "</td>");
	  out.println("</tr>");
	    }

	    out.println("</table>");
	  }
	}

	// 메뉴 조회 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>