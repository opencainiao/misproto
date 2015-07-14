<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div>系统执行发生错误，信息描述如下：</div>
	${exception.message}
	<br> ${exception.stackTrace}
	<br>
	<c:out value="${exception.stackTrace}"></c:out>
	<br>
	<c:out value="${exception.cause}"></c:out>
	<br>

	<div>错误状态代码是：${pageContext.errorData.statusCode}</div>
	<div>错误发生页面是：${pageContext.errorData.requestURI}</div>
	<div>错误信息：${pageContext.exception}</div>
	<div>
		错误堆栈信息：<br />
		<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
			<p>${trace}</p>
		</c:forEach>
	</div>




</body>
</html>