<%@ page
	import="com.app.utils.MenuDML,com.app.utils.DBConnection,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Insert Menu</title>
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
				<form action="InsertMenu.jsp" method="post">
					Modify_Start_Date: <input type="date" id="date" name="start_date"
						value="2020-01-01" min="2020-01-01" max="2023-12-31" /> 
					Modify_End_Date: <input type="date" id="date" name="end_date"
						value="2020-01-01" min="2020-01-01" max="2023-12-31" /> 
					  <input type="submit" name="Submit" /> <br> <br>
					  <h2>Menu Items</h2>
					<%
					MenuDML mDML = new MenuDML();
					List<Menu_item> Menu_itemList = mDML.showMenu_item("", "", "");
					if (Menu_itemList != null) {
						out.println("<table class=\"table table-success table-striped\" border=\"1\">");
						out.println("<th>" + "ITEM_ID" + "</th>");
						out.println("<th>" + "NAME" + "</th>");
						out.println("<th>" + "UNIT_PRICE" + "</th>");
						out.println("<th>" + "ITEM_QUANTITY" + "</th>");
						out.println("<th>" + "CATEGORY" + "</th>");
						out.println("<th>" + "SOLDOUT" + "</th>");
						out.println("<th>" + "Check" + "</th>");
						for (int i = 0; i < Menu_itemList.size(); i++) {
							out.println("<tr>");
							out.println("<td>" + Menu_itemList.get(i).getItem_id() + "</td>");
							out.println("<td>" + Menu_itemList.get(i).getName() + "</td>");
							out.println("<td>" + Double.toString(Menu_itemList.get(i).getUnit_price()) + "</td>");
							out.println("<td>" + Double.toString(Menu_itemList.get(i).getItem_quantity()) + "</td>");
							out.println("<td>" + Menu_itemList.get(i).getCategory() + "</td>");
							out.println("<td>" + Menu_itemList.get(i).getSoldout() + "</td>");
							out.println("<td><input type=\"checkbox\" name=\"selected_menu_item\" value=\""
						+ Menu_itemList.get(i).getItem_id() + "\"></td>");
							out.println("</tr>");
						}
					}
					%>
				</form>
			</div>
		</div>
	</div>
	<%
	//insertMenu(String start_date, String end_date, String manager_id)
	String start_date = "", end_date = "", manager_id = "MA000001"; //"MA000001"; // manager_id from Cookies
	if (request.getParameter("start_date") != null)
		start_date = request.getParameter("start_date");
	if (request.getParameter("end_date") != null)
		end_date = request.getParameter("end_date");

	try {
		DBConnection.beginTransaction();
		DBConnection.setReadCommited();
		if (start_date != "" && end_date != "" && manager_id != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date trans_start_date = sdf.parse(start_date);
			Date trans_end_date = sdf.parse(end_date);
			if (trans_start_date.compareTo(trans_end_date) > 0) {
		out.println("Start_date cannot be faster than end_date");
			} else {
		String id = mDML.insertMenu(start_date, end_date, manager_id);

		if (id == "" || id == null) {
			out.println("No result.");
		} else if (id != null) {
			out.println(id + " Menu insert successfully");
			String[] Menu_items = request.getParameterValues("selected_menu_item");
			for (int i = 0; i < Menu_items.length; i++) {
				mDML.insertContains(id, Menu_items[i]);
			}
		}
			}
		}
		DBConnection.commit();
	} catch (SQLException e) {
		// 실패한 경우 롤백
		DBConnection.rollback();
		e.printStackTrace();
	}

	// 메뉴 아이템 생성 로직
	// 예: List<MenuItem> menuItems = DatabaseUtil.getMenuItems();
	// 메뉴 아이템을 화면에 표시
	%>
</body>
</html>