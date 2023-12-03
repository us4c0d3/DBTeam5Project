<%@ page
	import="com.app.utils.MenuDML,com.app.utils.DBConnection,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Insert Menu_item</title>
<script src="../js/navbarScript.js"></script>
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
	<form action="InsertMenu_item.jsp" method="post">
		name: <input type="text" name="name" /><br> unit price: <input
			type="text" name="unit_price" /><br> category: <select
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
		</select> <br> <br> <input type="submit" name="Submit" />
		<%
		MenuDML mDML = new MenuDML();
		List<Ingredient> IngredientList = mDML.showIngredient("");
		if (IngredientList != null) {
			out.println("<table border=\"1\">");
			out.println("<th>" + "NAME" + "</th>");
			out.println("<th>" + "UNIT_PRICE" + "</th>");
			out.println("<th>" + "QUANTITY" + "</th>");
			for (int i = 0; i < IngredientList.size(); i++) {
				out.println("<tr>");
				out.println("<td>" + IngredientList.get(i).getName() + "</td>");
				out.println("<td>" + Double.toString(IngredientList.get(i).getUnit_price()) + "</td>");
				out.println("<td>" + Double.toString(IngredientList.get(i).getQuantity()) + "</td>");
				out.println("<td><input type=\"checkbox\" name=\"selected_ingredient\" value=\""
			+ IngredientList.get(i).getName() + "\"></td>");
				out.println("</tr>");
			}
		}
		%>
	</form>
	<%
	//insertMenu_item(String name, String category, String manager_id)
	String name = "", category = "", manager_id = "MA000001"; //"MA000001"; // manager_id from Cookies
	int unit_price = -100000000;
	if (request.getParameter("name") != null)
		name = request.getParameter("name");
	if (request.getParameter("category") != null)
		category = request.getParameter("category");
	if (request.getParameter("unit_price") != null)
		unit_price = Integer.valueOf(request.getParameter("unit_price"));

	try {
		DBConnection.beginTransaction();
		DBConnection.setReadCommited();
		if (name != "" && category != "" && unit_price != -100000000 && manager_id != "") {
			String id = mDML.insertMenu_item(name, category, unit_price, manager_id);

			if (id == "" || id == null) {
		out.println("No result.");
			} else if (id != null) {
		out.println(id + " Menu_item insert successfully");
		String[] Ingredients = request.getParameterValues("selected_ingredient");
		for (int i = 0; i < Ingredients.length; i++) {
			mDML.insertNeed(id, Ingredients[i]);
		}
			}
		}
		DBConnection.commit();
	} catch (SQLException e) {
		// 실패한 경우 롤백
		DBConnection.rollback();
		e.printStackTrace();
	}

	// 메뉴 아이템 생성 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>