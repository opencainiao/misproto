<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/admin/main.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/bootstrap-3.2.0-dist/css/bootstrap.css"
	type="text/css">
<link media="screen" type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/Flexigrid-master/css/flexigrid.css" />
</head>
<body class="page">
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />

	<ul class="breadcrumb">
		<li class="active">材料单位管理</li>
	</ul>

	<div id="content_inner_page" style="padding: 15px;">
		<ul class="breadcrumb">
			<div class="input-group" style="width: 400px;clear: both;">
				<input id="search_condition" name="search_condition" type="text"
					class="form-control" placeholder="输入名称进行查询"> 
				<span
					class="input-group-btn">
					<button id="btn_search" class="btn btn-primary" type="button" style="border-top-left-radius:4px;border-bottom-left-radius:4px">查询</button>
				</span>
				
				<span class="input-group-btn" style="padding-left: 50px ;">
					<button id="btn_add" class="btn btn-primary" type="button" style="border-top-left-radius:4px;border-bottom-left-radius:4px">添加单位</button>
				</span>
			</div>
		</ul>

		<div id="unit_manage" class="innerpage">
			<table id="list"></table>
		</div>
	</div>


	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/Flexigrid-master/js/flexigrid.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/Flexigrid-master/js/mou.fleligrid.ux.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/mou.core.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/mou.ajax.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/layer/layer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/admain/unit/list.js"></script>

</body>

</html>