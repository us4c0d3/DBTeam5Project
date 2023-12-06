<%@ page
	import="com.app.utils.MenuDML,com.app.utils.DBConnection,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Insert Menu item</title>
<script src="../static/js/navbarScript.js"></script>
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
        
        <a href="./OrderSearch.jsp" class="SearchOrder">Search Order</a>
        <a href="./ShowMenu.jsp" class="ShowMenu">Show Menu</a>
        <a href="./ShowMenu_item.jsp" class="ShowMenu_item">Show Menu Item</a>
        <a href="./InsertMenu.jsp">Insert Menu</a>
        <a href="./ModifyMenu.jsp">Modify Menu</a>
        <a href="./InsertMenu_item.jsp">Insert Menu Item</a>
        <a href="./ModifyIngredient.jsp">Modify Ingredient</a>
        
        <div class="right-links">
            <a href="./../static/html/Login.html">Login</a>
            <a href="./Mypage.jsp">Mypage</a>
        </div>
    </nav>

	<!-- body -->
	<div class="d-flex justify-content-center align-items-center">
		<div class="text-start">
			<div>
				<br>
				<form action="InsertMenu_item.jsp" method="post">
					name: <input type="text" name="name" />  unit price: <input
						type="text" name="unit_price" />  category: <select
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
					</select>   <input type="submit" name="Submit" /> <br> <br>
					<h2>Ingredient</h2>
					<%
					MenuDML mDML = new MenuDML();
					List<Ingredient> IngredientList = mDML.showIngredient("");
					if (IngredientList != null) {
						out.println("<table class=\"table table-success table-striped\" border=\"1\">");
						out.println("<th>" + "NAME" + "</th>");
						out.println("<th>" + "UNIT_PRICE" + "</th>");
						out.println("<th>" + "QUANTITY" + "</th>");
						out.println("<th>" + "Check" + "</th>");
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
			</div>
		</div>
	</div>
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
		DBConnection.setSerializable();
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
	} finally {
		DBConnection.setReadCommited();
	}

	// 메뉴 아이템 생성 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>