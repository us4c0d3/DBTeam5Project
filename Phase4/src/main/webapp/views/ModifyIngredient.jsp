<%@ page
	import="com.app.utils.*,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Modify Ingredient</title>
</head>
<body>
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
    mDML.modifyIngredient(ingredient_id, attribute, ingredient_quentity);
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