<%@ page
	import="com.app.utils.*,com.app.tables.*,java.sql.*,java.util.*,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Modify Menu</title>
</head>
<body>
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
	if (menu_id != "" && start_date != "" && end_date != "") {
		mDML.modifyMenu(menu_id, "start_date", start_date);
		mDML.modifyMenu(menu_id, "end_date", end_date);
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