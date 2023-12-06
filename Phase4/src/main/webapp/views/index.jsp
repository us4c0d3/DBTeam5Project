<!DOCTYPE html>
<html>
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

</body>
</html>