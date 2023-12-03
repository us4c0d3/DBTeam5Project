<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu_item</title>
<!-- bootstrap css -->
<link rel="stylesheet" href="./../static/css/bootstrap.min.css">
</head>
<body>
	<!-- header -->
	<nav class="navbar navbar-dark bg-primary">
		<div class="container-fluid">
			<a class="nav-link" href="./../static/html/index.html"> <span
				class="navbar-brand mb-0 h1">Restaurant</span>
			</a>
			<div>
				<a class="nav-link" href="./Mypage.jsp"> <span
					class="navbar-brand mb-0 h">Mypage</span>
				</a> <a class="nav-link" href="./../static/html/Login.html"> <span
					class="navbar-brand mb-0 h">Login</span></a>
			</div>
		</div>
		<ul class="nav" id="bar">
			<li class="SearchOrder"><a class="nav-link"
				href="./OrderSearch.jsp"> <span class="navbar-brand mb-0 h">Search
						Order</span></a></li>
			<li class="ShowMenu"><a class="nav-link" href="./ShowMenu.jsp">
					<span class="navbar-brand mb-0 h">Show Menu</span>
			</a></li>
			<li class="InsertMenu"><a class="nav-link"
				href="./InsertMenu.jsp"> <span class="navbar-brand mb-0 h">Insert
						Menu</span></a></li>
			<li class="ModifyMenu"><a class="nav-link"
				href="./ModifyMenu.jsp"> <span class="navbar-brand mb-0 h">Modify
						Menu</span></a></li>
			<li class="ShowMenu_item"><a class="nav-link"
				href="./ShowMenu_item.jsp"> <span class="navbar-brand mb-0 h">Show
						Menu item</span></a></li>
			<li class="InsertMenu_item"><a class="nav-link"
				href="./InsertMenu_item.jsp"> <span class="navbar-brand mb-0 h">Insert
						Menu item</span></a></li>
			<li class="ModifyIngredient"><a class="nav-link"
				href="./ModifyIngredient.jsp"> <span class="navbar-brand mb-0 h">Modify
						Ingredient</span></a></li>
		</ul>
	</nav>

	<!-- body -->

	<div class="d-flex justify-content-center align-items-center">
		<div class="text-center">
			<div>
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
					out.println("<table class=\"table table-success table-striped\" border=\"1\">");
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
			</div>
		</div>
	</div>
</body>
</html>