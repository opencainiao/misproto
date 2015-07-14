<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
<input type="hidden" id="_id" name="_id" value=${ _id } />
<img id="loading" src="<%=request.getContextPath()%>/resources/AjaxFileUploaderV2.1/loading.gif" style="display:none;">

<input type="hidden" name="page-title" value="编辑角色信息" />
<ul class="breadcrumb">
    <li><a href="<%=request.getContextPath()%>/sysroleadmin/roles">角色管理</a> <span class="divider"></span></li>
    <li class="active">编辑角色信息</li>
</ul>
<div class="colOne" id="sysrole_update_div">
	<div class="haomaTitle">
		<div class="haoma-inner">基本信息</div>
	</div>
	<!-- 此时没有写action,直接提交会提交给/add --> 
	<sf:form method="post" modelAttribute="sysrole" enctype="multipart/form-data" class="box">
		<table class="formtable_s">
			<tbody>
				<tr >
					<th>逻辑主键</th>
					<td><input type="text" class="readonly" readonly="readonly" value="${sysrole._id}" /></td>
					<td></td>
				</tr>
				<tr >
					<th>名称</th>
					<td><sf:input path="name" id="name"/></td>
					<td><span class="error" id="name_err"></span></td>
				</tr>
				<tr >
					<th>角色简介</th>
					<td><sf:textarea path="remark" id="remark"/></td>
					<td><span class="error" id="remark_err"></span></td>
				</tr>
			</tbody>
		</table>
		
		<div class="i_do_btns">
			<input id="btn_save"  type="button" class="btns block margin_auto" value="提交"/>
		</div>
	</sf:form>
</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou_grid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou.ajax.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/AjaxFileUploaderV2.1/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/admain/sysrole/update.js"></script>
	
</body>
</html>