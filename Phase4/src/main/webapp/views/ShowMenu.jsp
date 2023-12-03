<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu</title>
</head>
<body>
	<form action="ShowMenu.jsp" method="post">
		Date: <select name="menu_id">
			<%
			MenuDML mDML = new MenuDML();
			List<Menu> DateList = mDML.showMenu_v2();
			if (DateList != null) {
				for (int i = 0; i < DateList.size(); i++) {
					out.println("<option value=\"" + DateList.get(i).getMenu_id() + "\">"
				+ DateList.get(i).getStart_date() + " ~ " + DateList.get(i).getEnd_date() + "</option>");
				}
			}
			%>
		</select><br> <input type="submit" name="Submit" />
	</form>
	<h2>Menu</h2>
	<%
	String menu_id = "";
	if (request.getParameter("menu_id") != null) {
		menu_id = request.getParameter("menu_id");
		List<Menu_item> rs = mDML.showMenu_item_v2(menu_id);
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