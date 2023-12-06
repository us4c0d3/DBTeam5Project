<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%><html>
<head>
<meta charset="EUC-KR">
<title>Main page</title>
<script src="./../static/js/navbarScript.js"></script>
<!-- bootstrap css -->
<link rel="stylesheet" href="./../static/css/bootstrap.min.css">
<link rel="stylesheet" href="./../static/css/navbarStyle.css">
</head>

<body>
	<!-- header -->
	<nav class="navbar">
		<div class="logo">
			<a href="./index.jsp">Restaurant</a>
		</div>

		<a href="./OrderSearch.jsp" class="SearchOrder">Search Order</a> <a
			href="./ShowMenu.jsp" class="ShowMenu">Show Menu</a> <a
			href="./ShowMenu_item.jsp" class="ShowMenu_item">Show Menu Item</a> <a
			href="./InsertMenu.jsp">Insert Menu</a> <a href="./ModifyMenu.jsp">Modify
			Menu</a> <a href="./InsertMenu_item.jsp">Insert Menu Item</a> <a
			href="./ModifyIngredient.jsp">Modify Ingredient</a>

		<div class="right-links">
			<a href="./../static/html/Login.html">Login</a> <a
				href="./Mypage.jsp">Mypage</a>
		</div>
	</nav>
	<div class="d-flex justify-content-center align-items-center">
		<div class="text-start">
			<br>
			<h2>Today's recommended dish</h2>
			<br>
			<%
			MenuDML mDML = new MenuDML();
			String name = "", category = "", soldout = "F";
			List<Menu_item> rs = mDML.showMenu_item(name, category, soldout);
			if (rs.size() > 0) {
				int randomNumber = (int)(Math.random() * (rs.size() - 1));
				String randomNumber_id = String.format("ME%06d", randomNumber);
				if (rs.size() > randomNumber) {
					out.println("<table class=\"table table-success table-striped\" border=\"1\">");
					out.println("<th width=\"300\">" + "NAME" + "</th>");
					out.println("<th width=\"50\">" + "UNIT_PRICE" + "</th>");
					out.println("<th width=\"50\">" + "SOLDOUT" + "</th>");

					Menu_item getItem = rs.get(randomNumber);
					out.println("<tr>");
					out.println("<td>" + getItem.getName() + "</td>");
					out.println("<td>" + Integer.toString(getItem.getUnit_price()) + "</td>");
					out.println("<td>" + getItem.getSoldout() + "</td>");
					out.println("</tr>");
					out.println("</table>");
				}
			}

			// 메뉴 아이템 조회 로직
			// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
			// 메뉴 아이템을 화면에 표시
			%>
			
			<button onClick="window.location.reload()">Recommend Again!</button>
		</div>
	</div>
</body>
</html>