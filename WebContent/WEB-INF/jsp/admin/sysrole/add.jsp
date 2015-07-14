<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>
<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
<input type="hidden" name="page-title" value="添加角色信息" />
<ul class="breadcrumb">
    <li><a href="<%=request.getContextPath()%>/sysroleadmin/roles">系统角色管理</a> <span class="divider"></span></li>
    <li class="active">添加角色信息</li>
</ul>
<div class="colOne" id="sysrole_add_div">
	<!-- 此时没有写action,直接提交会提交给/add -->
	<sf:form method="post" modelAttribute="sysrole" enctype="multipart/form-data" class="box">
		<table class="formtable_s">
			<tbody>
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
	
	<script>
	
	$().ready(function() {
		
		$("#btn_save").bind("click", save);
	});

	//保存
	var save = function() {
		
		// 控制按钮为禁用
		$.disableButton("btn_save");

		var paramForm = $('form').getFormParam_ux();
		
		var url_to =  "<%=request.getContextPath()%>/sysroleadmin/add";
		
		$.ajax({
	        type:'POST',
	        url: url_to,
	        data : $.extend({
				ts : new Date().getTime()
			}, paramForm),
			type : 'POST',
			dataType : 'json',
	        success: function(data){
	        	//layer.alert( JSON.stringify(data), 8); 
	        	//alert(data.success);
	        	if (data['success'] == 'n'){
	        		
	        		if (data['brErrors']){
	        			$.showBRErrors(data['brErrors'],$("#sysrole_add_div"));
	        		}else {
	        			layer.alert(data['message']);
	        		}
	        	}else{
	        		layer.alert( "保存成功", 1);
	        	}
	        },
	        complete:function(XMLHttpRequest, textStatus){
	        	
	        	//layer.alert(JSON.stringify(XMLHttpRequest));
	        	// 控制按钮为可用
	        	$.enableButton("btn_save");
	        }
	    }); 
	};
	
	</script>
</body>
</html>