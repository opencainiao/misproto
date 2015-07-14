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
<title>UMC</title>

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>

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
	font-size: 14px;
}

.Center-Container.is-Inline {
	text-align: center;
	overflow: auto;
}

.Center-Container.is-Inline:after, .is-Inline .Center-Block {
	display: inline-block;
	vertical-align: middle;
}

.Center-Container.is-Inline:after {
	content: '';
	height: 100%;
	margin-left: -0.25em; /* To offset spacing. May vary by font */
}

.is-Inline .Center-Block {
	max-width: 99%;
	/* Prevents issues with long content causes the content block to be pushed to the top */
	/* max-width: calc(100% - 0.25em) /* Only for IE9+ */
}

.form-control {
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	color: #555;
	font-size: 16px;
	height: 36px;
	line-height: 1.42857;
	padding: 6px 12px;
	transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s
		ease-in-out 0s;
	width: 100%;
}
</style>
</head>

<body>
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/front/login/login.js"></script>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/front/home/index.js"></script>
	<script>
		$.alertSuccess("成功测试","成功登陆系统");		
	</script>
</body>
</html>
