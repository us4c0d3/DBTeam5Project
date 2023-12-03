<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Show Menu</title>
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

	<div class="d-flex justify-content-center align-items-center">
		<div class="text-start">
			<div>
				<br>
				<form action="ShowMenu.jsp" method="post">
					Date: <select name="menu_id">
						<%
						MenuDML mDML = new MenuDML();
						List<Menu> DateList = mDML.showMenu_v2();
						if (DateList != null) {
							for (int i = 0; i < DateList.size(); i++) {
								out.println("<option value=\"" + DateList.get(i).getMenu_id() + "\">"
							+ DateList.get(i).getStart_date() + " ~ " + DateList.get(i).getEnd_date() + "</option>");
							}
						}
						%>
					</select>  <input type="submit" name="Submit" />
				</form>
				<h2>Menu</h2>
				<%
				String menu_id = "";

				out.println("<table class=\"table table-success table-striped\" border=\"1\">");
				out.println("<th>" + "ITEM_ID" + "</th>");
				out.println("<th>" + "NAME" + "</th>");
				out.println("<th>" + "UNIT_PRICE" + "</th>");
				out.println("<th>" + "ITEM_QUANTITY" + "</th>");
				out.println("<th>" + "CATEGORY" + "</th>");
				out.println("<th>" + "SOLDOUT" + "</th>");

				if (request.getParameter("menu_id") != null) {
					menu_id = request.getParameter("menu_id");
					List<Menu_item> rs = mDML.showMenu_item_v2(menu_id);

					if (rs == null) {
						out.println("No result.");
					} else if (rs != null) {
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
				}

				// 메뉴 조회 로직
				// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
				// 메뉴 아이템을 화면에 표시
				%>
			</div>
		</div>
	</div>
</body>
</html>