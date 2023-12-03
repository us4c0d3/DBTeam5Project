<%@ page import="java.util.List"%>
<%@ page import="com.app.utils.*, com.app.tables.* "%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>My Page</title>
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

	<div class="d-flex justify-content-center align-items-center" style="height: 50vh;">
		<div class="text-center">
			<div>
				<h2>Update Your Information</h2>
				<%
				// 성공 메시지 가져오기
				String successMessage = (String)session.getAttribute("successMessage");
				if (successMessage != null && !successMessage.isEmpty()) {
					out.println("<p style='color: green;'>" + successMessage + "</p>");
					session.removeAttribute("successMessage"); // 메시지 표시 후 제거
				}

				// 오류 메시지 가져오기
				String errorMessage = (String)session.getAttribute("errorMessage");
				if (errorMessage != null && !errorMessage.isEmpty()) {
					out.println("<p style='color: red;'>" + errorMessage + "</p>");
					session.removeAttribute("errorMessage"); // 메시지 표시 후 제거
				}

				TableMain tm = TableMain.getInstance();
				Cookie[] cookies = request.getCookies();
				int checkCusOrMan = -1; // customer: 1, manager: 0
				String userId = null;
				String userCurrentName = "";
				String userCurrentPhoneNumber = "";
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if ("userId".equals(cookie.getName())) {
					userId = cookie.getValue();
					if (userId.substring(0, 2).equals("CU")) {
						checkCusOrMan = 1;
					} else if (userId.substring(0, 2).equals("MA")) {
						checkCusOrMan = 0;
					}
					break;
						}
					}
				}

				if (userId != null) {
					if (checkCusOrMan == 1) {
						List<Customer> customers = tm.customer.SELECTbyID(userId);
						userCurrentName = customers.get(0).getName();
						userCurrentPhoneNumber = customers.get(0).getPhonenumber();
					} else if (checkCusOrMan == -1) {
						List<Manager> managers = tm.manager.SELECTbyID(userId);
						userCurrentName = managers.get(0).getName();
						userCurrentPhoneNumber = managers.get(0).getPhonenumber();
					}
				}
				%>
				<form action="http://localhost:8080/Phase4/UpdateUserServlet"
					method="post">
					<input type="hidden" name="userId" value="<%=userId%>">
					Name: <input type="text" name="name" value="<%=userCurrentName%>"
						required><br> Phone number: <input type="email"
						name="phoneNumber" value="<%=userCurrentPhoneNumber%>" required><br>
					Current Password: <input type="password" name="currentPassword"
						required><br> Change Password: <input type="password"
						name="changePassword"> <input type="submit" value="Update">
				</form>
			</div>
		</div>
	</div>
</body>
</html>
