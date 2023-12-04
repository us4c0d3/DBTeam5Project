<%@ page
	import="com.app.utils.*,java.sql.*,com.app.tables.*,java.util.*,com.app.utils.DBConnection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Search Order</title>
<script src="../static/js/navbarScript.js"></script>
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
	<form action="OrderSearch.jsp" method="post">
		<label for="id">Customer ID:</label> <input type="text" id="id"
			name="id"> <input type="submit" value="Search">
	</form>
	<h2>Search Order</h2>
	<%
	OrderDML orderDML = new OrderDML(DBConnection.getConnection());
	String id = "";
	if (request.getParameter("id") != null)
		id = request.getParameter("id");
	Object[] result = orderDML.showOrder2(id);
	List<String> customerIds = (List<String>)result[0];
	List<String> orderIds = (List<String>)result[1];
	List<Payment> payments = (List<Payment>)result[2];
	List<Menu_item> menuItems = (List<Menu_item>)result[3];
	List<Integer> amounts = (List<Integer>)result[4];
	if (payments != null && menuItems != null && amounts != null) {
		out.println("<table border=\"1\">");
		out.println("<th>" + "CUSTOMER_ID" + "</th>");
		out.println("<th>" + "ORDER_ID" + "</th>");
		out.println("<th>" + "TOTAL_PRICE" + "</th>");
		out.println("<th>" + "PAYMENT_TYPE" + "</th>");
		out.println("<th>" + "CARD_INFO" + "</th>");
		out.println("<th>" + "ITEM_ID" + "</th>");
		out.println("<th>" + "NAME" + "</th>");
		out.println("<th>" + "AMOUNT" + "</th>");
		out.println("<th>" + "UNIT_PRICE" + "</th>");
		out.println("<th>" + "ITEM_QUANTITY" + "</th>");
		out.println("<th>" + "CATEGORY" + "</th>");
		out.println("<th>" + "SOLDOUT" + "</th>");
	}
	for (int i = 0; i < payments.size(); i++) {
		out.println("<tr>");
		String customerId = customerIds.get(i);
		String orderID = orderIds.get(i);
		Payment payment = payments.get(i);
		Menu_item menuItem = menuItems.get(i);
		Integer amount = amounts.get(i);
		out.println("<td>" + customerId + "</td>");
		out.println("<td>" + orderID + "</td>");
		out.println("<td>" + payment.getTotal_price() + "</td>");
		out.println("<td>" + payment.getPayment_type() + "</td>");
		out.println("<td>" + payment.getCard_info() + "</td>");
		out.println("<td>" + menuItem.getItem_id() + "</td>");
		out.println("<td>" + menuItem.getName() + "</td>");
		out.println("<td>" + amount + "</td>");
		out.println("<td>" + menuItem.getUnit_price() + "</td>");
		out.println("<td>" + menuItem.getItem_quantity() + "</td>");
		out.println("<td>" + menuItem.getCategory() + "</td>");
		out.println("<td>" + menuItem.getSoldout() + "</td>");
		out.println("</tr>");
	}
	%>
</body>
</html>