<%@ page import="com.app.utils.* "%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	<title>Main page</title>
</head>

<body>
	<ul class="nav" id="bar">
		<li class="Mypage">
			<a class="nav-link" href="./Mypage.jsp">
				<span>Mypage</span></a>
		</li>
		<li class="Login">
			<a class="nav-link" href="./../static/html/Login.html">
				<span>Login</span></a>
		</li>
		<li class="SearchOrder">
			<a class="nav-link" href="./OrderSearch.jsp">
				<span>Search Order</span></a>
		</li>
		<li class="ShowMenu">
			<a class="nav-link" href="./ShowMenu.jsp">
				<span>Show Menu</span></a>
		</li>
		<li class="InsertMenu">
			<a class="nav-link" href="./InsertMenu.jsp">
				<span>Insert Menu</span></a>
		</li>
		<li class="ModifyMenu">
			<a class="nav-link" href="./ModifyMenu.jsp">
				<span>Modify Menu</span></a>
		</li>
		<li class="ShowMenu_item">
			<a class="nav-link" href="./ShowMenu_item.jsp">
				<span>Show Menu item</span></a>
		</li>
		<li class="InsertMenu_item">
			<a class="nav-link" href="./InsertMenu_item.jsp">
				<span>Insert Menu item</span></a>
		</li>
		<li class="ModifyIngredient">
			<a class="nav-link" href="./ModifyIngredient.jsp">
				<span>Modify Ingredient</span></a>
		</li>

		<!--<button type="button" onclick="location.href='./../../views/Login.jsp'">Login</button> <br>
		<button type="button" onclick="location.href='./../../views/Mypage.jsp'">My page</button> <br>
		<button type="button" onclick="location.href='./../../views/SearchOrder.jsp'">Order</button> <br>
		<button type="button" onclick="location.href='./../../views/ShowMenu.jsp'">Show Menu</button> <br>
		<button type="button" onclick="location.href='./../../views/InsertMenu.jsp'">Insert Menu</button> <br>
		<button type="button" onclick="location.href='./../../views/ModifyMenu.jsp'">Modify Menu</button> <br>
		<button type="button" onclick="location.href='./../../views/ShowMenu_item.jsp'">Show Menu item</button> <br>
		<button type="button" onclick="location.href='./../../views/InsertMenu_item.jsp'">Insert Menu item</button>
		<br>
		<button type="button" onclick="location.href='./../../views/ModifyMenu_item.jsp'">Modify Menu item</button>
		<br>
		<button type="button" onclick="location.href='./../../views/ModifyIngredient.jsp'">Modify
			Ingredient</button>
		<br>
		-->
	</ul>
</body>

</html>