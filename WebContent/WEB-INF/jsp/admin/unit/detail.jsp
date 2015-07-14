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
	<ul class="breadcrumb">
	    <li><a href="<%=request.getContextPath()%>/unitadmin/list">材料单位管理</a> <span class="divider"></span></li>
	     <li class="active">单位信息</li>
	</ul>
	<div class="colOne" style="padding:20px">
		<sf:form method="post" class="box">
			<table class="detailtable">
				<tbody>
					<tr >
						<th>逻辑主键</th>
						<td>${unit._id}</td>
					</tr>
					<tr >
						<th>名称</th>
						<td>${unit.name}</td>
					</tr>
				</tbody>
			</table>
		</sf:form>
	</div>
</body>
</html>