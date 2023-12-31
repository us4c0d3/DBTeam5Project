<%@ page
	import="com.app.utils.MenuDML,com.app.tables.*,java.sql.*,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Menu</title>
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
				<form action="ShowMenu.jsp" method="post" style="width:800px;">
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
				out.println("<th width=\"360\">" + "NAME" + "</th>");
				out.println("<th width=\"50\">" + "UNIT_PRICE" + "</th>");
				out.println("<th width=\"50\">" + "ITEM_QUANTITY" + "</th>");
				out.println("<th width=\"170\">" + "CATEGORY" + "</th>");
				out.println("<th width=\"50\">" + "SOLDOUT" + "</th>");

				if (request.getParameter("menu_id") != null) {
					menu_id = request.getParameter("menu_id");
					List<Menu_item> rs = mDML.showMenu_item_v2(menu_id);

					if (rs == null) {
						out.println("No result.");
					} else if (rs != null) {
						for (int i = 0; i < rs.size(); i++) {
					out.println("<tr>");
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