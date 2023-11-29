<%@ page import="com.app.utils.* "%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Page</title>
</head>
<body>
    <h2>Update Your Information</h2>
    <% 
        /*
	    public ResultSet showCustomer(String id, String password) {
			ResultSet rs = null;
			rs = tm.customer.SELECT(id, password);
	
			return rs;
		}
	
		public ResultSet showManager(String id, String password) {
			ResultSet rs = null;
			rs = tm.manager.SELECT(id, password);
	
			return rs;
		}
	*/
	
	String userCurrentName = "";
	String userCurrentPhoneNumber = "";
	
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
