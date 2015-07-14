<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 此时没有写action,直接提交会提交给/add -->
	<form action="login" method="post">

		用户名<input type="text" name="username"></input><br> 
		密码<input
			type="password" name="password"></input> <input type="submit"
			value="登陆" />
	</form>
</body>
</html>