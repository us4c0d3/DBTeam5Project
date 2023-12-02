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
    <form action="Mypage.jsp" method="post">
        Name: <input type="text" name="name" value="<%= userCurrentName %>>" required><br>
        Phone number: <input type="email" name="phoneNumber" value="<%= userCurrentPhoneNumber %>" required><br>
        Current Password: <input type="password" name="currentPassword" required><br>
        
        <!-- 다른 필요한 필드 추가 -->
        <input type="submit" value="Update">
    </form>
</body>
</html>
