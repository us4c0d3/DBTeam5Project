<%@ page
	import="com.app.utils.*,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Modify Ingredient</title>
<script src="../static/js/navbarScript.js"></script>
<!-- bootstrap css -->
<link rel="stylesheet" href="./../static/css/bootstrap.min.css">
<link rel="stylesheet" href="./../static/css/navbarStyle.css">
</head>
<body>
	<!-- header -->
	<nav class="navbar">
		<div class="logo">
			<a href="./../static/html/index.html">Restaurant</a>
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
				<form action="ModifyIngredient.jsp" method="post">
					<label for="ingredient_id">Ingredient ID:</label> <input
						type="text" id="ingredient_id" name="ingredient_id"> <label
						for="quantity">Quantity:</label> <input type="number"
						id="quantity" name="quantity"> <input type="submit"
						value="Submit">
				</form>
				<h2>Ingredient</h2>
				<%
				MenuDML mDML = new MenuDML();
				String ingredient_id = "", attribute = "Quantity", ingredient_quentity = "";
				if (request.getParameter("ingredient_id") != null)
					ingredient_id = request.getParameter("ingredient_id");
				if (request.getParameter("quantity") != null)
					ingredient_quentity = request.getParameter("quantity");
				try {
					DBConnection.beginTransaction();
					DBConnection.setReadCommited();
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
					out.println("<table class=\"table table-success table-striped\" border=\"1\">");
					out.println("<th>" + "INGREDIENT_ID" + "</th>");
					out.println("<th>" + "NAME" + "</th>");
					out.println("<th>" + "UNIT_PRICE" + "</th>");
					out.println("<th>" + "QUANTITY" + "</th>");
				}
				for (int i = 0; i < result.size(); i++) {
					out.println("<tr>");
					Ingredient ingredient = result.get(i);
					out.println("<td>" + ingredient.getId() + "</td>");
					out.println("<td>" + ingredient.getName() + "</td>");
					out.println("<td>" + ingredient.getUnit_price() + "</td>");
					out.println("<td>" + ingredient.getQuantity() + "</td>");
					out.println("</tr>");
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>