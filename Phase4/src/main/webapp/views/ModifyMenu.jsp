<%@ page
	import="com.app.utils.*,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Modify Menu</title>
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
	<form action="ModifyMenu.jsp" method="post">
		Menu_Id: <input type="text" id="id" name="id" /><br>
		Modify_Start_Date: <input type="date" id="date" name="start_date"
			value="2020-01-01" min="2020-01-01" max="2023-12-31" /><br>
		Modify_End_Date: <input type="date" id="date" name="end_date"
			value="2020-01-01" min="2020-01-01" max="2023-12-31" /><br> <br>
		<input type="submit" name="Submit" /> <br>
	</form>
	<%
	MenuDML mDML = new MenuDML();
	String menu_id = "", start_date = "", end_date = "";
	if (request.getParameter("id") != null)
		menu_id = request.getParameter("id");
	if (request.getParameter("start_date") != null)
		start_date = request.getParameter("start_date");
	if (request.getParameter("end_date") != null)
		end_date = request.getParameter("end_date");
	try {
		DBConnection.beginTransaction();
		if (menu_id != "" && start_date != "" && end_date != "") {
			mDML.modifyMenu(menu_id, "start_date", start_date);
			mDML.modifyMenu(menu_id, "end_date", end_date);
	    	DBConnection.commit();
		}
		DBConnection.commit();
	} catch (SQLException e) {
	    // 실패한 경우 롤백
	    DBConnection.rollback();
	    e.printStackTrace();
	}
	List<Menu> result = mDML.showMenu_v2();
	if (result != null) {
		out.println("<table border=\"1\">");
		out.println("<th>" + "MENU_ID" + "</th>");
		out.println("<th>" + "START_DATE" + "</th>");
		out.println("<th>" + "END_DATE" + "</th>");
	}
	for (int i = 0; i < result.size(); i++) {
		out.println("<tr>");
		Menu menu = result.get(i);
		out.println("<th>" + menu.getMenu_id() + "</th>");
		out.println("<th>" + menu.getStart_date() + "</th>");
		out.println("<th>" + menu.getEnd_date() + "</th>");
		out.println("</tr>");
	}
	%>
</body>
</html>