<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>查询单位</title>

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>
<link media="screen" type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/Flexigrid-master/css/flexigrid.css" />

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lte IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
.xubox_tab_main {
	padding-left: 0px;
}

.navbar-brand {
	font-size: 38px;
}

.jumbotron {
	padding: 65px 0px 2px 30px;
}

.jumbotron p {
	font-size: 18px;
}

.jumbotron div[class*="col-"] {
	padding-left: 6px;
	padding-right: 6px;
}

.span_center {
	float: left;
	font-size: 18px;
	height: 50px;
	line-height: 20px;
	padding: 15px;
}

.flexigrid {
	font-size: 12px;
}

nav ul{
	padding-left: 10px!important;
	padding-top: 5px;
}
</style>
</head>

<body>
	<ul class="breadcrumb" id="add_spec_breadcrumb" >
		<li>
			<input type="button" id="btn_confirm"   class="btn btn-nm btn-primary " value="确定"> 
			<input type="button" id="btn_cancel"   class="btn btn-nm btn-primary " value="取消">
		</li>
	</ul>
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	
	<div class="container" style="margin-top: 5px;">
		<nav class="navbar navbar-default" role="navigation"
			style="margin-bottom: 0px; width: 100%; float: left;">
			<ul
				style="float: left; color: #9d9d9d; width: 30%;">
				<li style="display: inline;">
					<input id="unitname" name="unitname" class="form-control" type="text" style="width: 100%" placeholder="输入单位名称">
				</li>
			</ul>
			<ul style="float: left; ">
				<li style="display: inline; width: 10%;">
					<button id="btn_search" class="btn btn-nm btn-default" type="button">查询</button>
				</li>
			</ul>
		</nav>
		
		<div id="unit_search_div" class="row"
			style="border: 0px solid red; float: left; width: 100%; margin-left: 0px; padding-top: 5px">
			<div id="unit_search" class="innerpage">
				<table id="list"></table>
			</div>
		</div>
	</div>


	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/Flexigrid-master/js/flexigrid.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/Flexigrid-master/js/mou.fleligrid.ux.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/mou_grid.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/front/login/login.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/front/unit/list.js"></script>

</body>
</html>
