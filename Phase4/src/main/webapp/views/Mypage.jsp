<%@ page import="java.util.List" %>
<%@ page import="com.app.utils.*, com.app.tables.* "%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Page</title>
</head>
<body>
    <h2>Update Your Information</h2>
    <% 
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
    if(cookies != null) {
    	for(Cookie cookie : cookies) {
    		if("userId".equals(cookie.getName())) {
    			userId = cookie.getValue();
    			if(userId.substring(0, 2).equals("CU")) {
    				checkCusOrMan = 1;
    			} else if(userId.substring(0, 2).equals("MA")) {
    				checkCusOrMan = 0;
    			}
    			break;
    		}
    	}
    }
	
    if(userId != null) {
    	if(checkCusOrMan == 1) {
    		List<Customer> customers = tm.customer.SELECTbyID(userId);
    		userCurrentName = customers.get(0).getName();
    		userCurrentPhoneNumber = customers.get(0).getPhonenumber();
    	} else if(checkCusOrMan == -1) {
    		List<Manager> managers = tm.manager.SELECTbyID(userId);
    		userCurrentName = managers.get(0).getName();
    		userCurrentPhoneNumber = managers.get(0).getPhonenumber();
    	}
    }
	
    %>
    <form action="http://localhost:8080/Phase4/UpdateUserServlet" method="post">
    	<input type="hidden" name="userId" value="<%= userId %>">
        Name: <input type="text" name="name" value="<%= userCurrentName %>" required><br>
        Phone number: <input type="email" name="phoneNumber" value="<%= userCurrentPhoneNumber %>" required><br>
        Current Password: <input type="password" name="currentPassword" required><br>
        Change Password: <input type="password" name="changePassword">
        <input type="submit" value="Update">
    </form>
</body>
</html>
