<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu_item</title>
</head>
<body>
	<form action="ShowMenu_item.jsp" method="post">
		name: <input type="text" name="name" /><br> category: <select
			name="category">
			<option value="" selected></option>
			<option value="Condiments Menu">Condiments Menu</option>
			<option value="Regular Menu">Regular Menu</option>
			<option value="Breakfast Menu">Breakfast Menu</option>
			<option value="McCafe Menu">McCafe Menu</option>
			<option value="Desserts Menu">Desserts Menu</option>
			<option value="Gourmet Menu">Gourmet Menu</option>
			<option value="Desserts Menu">Desserts Menu</option>
			<option value="Beverages Menu">Beverages Menu</option>
			<option value="Condiments Menu">Condiments Menu</option>
		</select><br> soldout: <select name="soldout">
			<option value="" selected></option>
			<option value="T">T</option>
			<option value="F">F</option>
		</select><br> <input type="submit" name="Submit" />
	</form>
	<h2>Menu Items</h2>
	<%
	MenuDML mDML = new MenuDML();
	String name = "", category = "", soldout = "";
	if (request.getParameter("name") != null)
	  name = request.getParameter("name");
	if (request.getParameter("category") != null)
	  category = request.getParameter("category");
	if (request.getParameter("soldout") != null)
	  soldout = request.getParameter("soldout");
	List<Menu_item> rs = mDML.showMenu_item(name, category, soldout);
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

	// 메뉴 아이템 조회 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>