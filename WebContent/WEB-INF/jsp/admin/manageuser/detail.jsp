<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/admin/main.css"
	type="text/css">
<link rel="stylesheet"	
	href="<%=request.getContextPath()%>/resources/bootstrap-3.2.0-dist/css/bootstrap.css"
	type="text/css">
</head>
<body>
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="page-title" value="管理用户信息" />
    <ul class="breadcrumb">
        <li><a href="<%=request.getContextPath()%>/useradmin/list">管理用户管理</a> <span class="divider"></span></li>
        <li class="active">管理用户信息</li>
    </ul>
<div class="colOne" style="padding:20px">
	<sf:form method="post" modelAttribute="manageuser" enctype="multipart/form-data" class="box">
		<table class="detailtable">
		<tbody>
				<tr >
					<th>用户名</th>
					<td><sf:input path="username" id="username"/></td>
				</tr>
				<tr >
					<th>昵称</th>
					<td><sf:input path="nickname" id="nickname"/></td>
				</tr>
				<tr >
					<th>用户密码</th>
					<td><sf:input path="password" id="password"/></td>
				</tr>
				<tr >
					<th>联系电话</th>
					<td><sf:input path="phone" id="phone"/></td>
				</tr>
				<tr >
					<th>电子邮件</th>
					<td><sf:input path="email" id="email"/></td>
				</tr>
				<tr>
					<th>状态</th>
					<td>
						${manageuser.userstate.name}
					</td>
				</tr>
				<tr >
					<th>拥有角色</th>
					<td>
						<c:forEach items="${roles }" var="item">
							<a href="<%=request.getContextPath()%>/admin/role/${item.key}">
							[${item.value }]</a>&nbsp;
						</c:forEach>
					</td>
				</tr>
			</tbody>
		</table>
	</sf:form>
</div>
</body>
</html>