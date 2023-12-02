<%@ page import="com.app.utils.*, com.app.tables.TableMain "%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Page</title>
</head>
<body>
    <h2>Update Your Information</h2>
    <% 
    Cookie[] cookies = request.getCookies();
    String userId = null;
    String userCurrentName = "";
	String userCurrentPhoneNumber = "";
    if(cookies != null) {
    	for(Cookie cookie : cookies) {
    		if("userId".equals(cookie.getName())) {
    			userId = cookie.getValue();
    			break;
    		}
    	}
    }
	
    if(userId != null) {
    	userCurrentName = 
    }
	
    %>
    <form action="Mypage.jsp" method="post">
        Name: <input type="text" name="name" value="<%= userCurrentName %>>" required><br>
        Email: <input type="email" name="phoneNumber" value="<%= userCurrentPhoneNumber %>" required><br>
        Current Password: <input type="password" name="currentPassword" required><br>
        
        <!-- 다른 필요한 필드 추가 -->
        <input type="submit" value="Update">
    </form>
</body>
</html>
