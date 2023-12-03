<%@ page
	import="com.app.utils.*,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Modify Ingredient</title>
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
    <form action="ModifyIngredient.jsp" method="post">
        <label for="ingredient_id">Ingredient ID:</label>
        <input type="text" id="ingredient_id" name="ingredient_id">

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity">

        <input type="submit" value="Submit">
    </form>
    <%
    MenuDML mDML = new MenuDML();
    String ingredient_id = "", attribute = "Quantity", ingredient_quentity = "";
	if (request.getParameter("ingredient_id") != null)
		ingredient_id = request.getParameter("ingredient_id");
	if (request.getParameter("quantity") != null)
		ingredient_quentity = request.getParameter("quantity");
	try {
		DBConnection.beginTransaction();
		if (ingredient_id != "" && ingredient_quentity != "") {
	    	mDML.modifyIngredient(ingredient_id, attribute, ingredient_quentity);
		}
		DBConnection.commit();
	} catch (SQLException e) {
	    // 실패한 경우 롤백
	    DBConnection.rollback();
	    e.printStackTrace();
	}
    List<Ingredient> result = mDML.showIngredient(ingredient_id);
	if (result != null) {
		out.println("<table border=\"1\">");
		out.println("<th>" + "INGREDIENT_ID" + "</th>");
		out.println("<th>" + "NAME" + "</th>");
		out.println("<th>" + "UNIT_PRICE" + "</th>");
		out.println("<th>" + "QUANTITY" + "</th>");
	}
	for (int i = 0; i < result.size(); i++) {
		out.println("<tr>");
		Ingredient ingredient = result.get(i);
		out.println("<th>" + ingredient.getId() + "</th>");
		out.println("<th>" + ingredient.getName() + "</th>");
		out.println("<th>" + ingredient.getUnit_price() + "</th>");
		out.println("<th>" + ingredient.getQuantity() + "</th>");
		out.println("</tr>");
	}    
    
    %>
</body>
</html>