<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_flexigrid.jsp"></jsp:include>

<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/admin/infrastructure/sysconst/list.js"></script>
<style>
.content_label{
	padding-bottom: 7px;
	background-color: #eee;
}
</style>
</head>

<body>
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	<input type="hidden" name="typecode" id="typecode" value="${sysconsttype.typecode }" />
	<input type="hidden" name="typename" id="typename" value="${sysconsttype.typename }" />
	
	<div id="content_inner_page" class="innercontent" >
		<div class="panel panel-default">
		  <div class="panel-body">
		  	<div class="container-fluid inlineone" style="margin-top: 10px">
				<div class="form-group ">
					<label for="" class="col-sm-2 control-label"> 类型编码 </label>
					<label for="" class="col-sm-2 control-label content_label" > ${sysconsttype.typecode } </label>
					
					<label for="" class="col-sm-2 control-label"> &nbsp; </label>
				
					<label  class="col-sm-2 control-label"> 类型名称 </label>
					<label  class="col-sm-2 control-label content_label" > ${sysconsttype.typename } </label>
				</div>
			</div>
		  </div>
		</div>


		<div id="data_manage" style="margin-top: 20px;">
			<table id="list"></table>
		</div>
	</div>
	
</body>
</html>