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
    <li><a href="<%=request.getContextPath()%>/unitadmin/list">材料单位管理</a> <span class="divider"></span></li>
    <li class="active">添加材料单位</li>
</ul>
<div class="colOne" id="sysrole_add_div" style="padding-top: 80px">
	<input type="hidden" name="_id"/>
	<!-- 此时没有写action,直接提交会提交给/add -->
	<sf:form method="post" modelAttribute="unit" enctype="multipart/form-data" class="box">
		<table class="formtable_s" id="baseinfo">
			<tbody>
				<tr >
					<th>名称</th>
					<td><sf:input path="name" id="name" /></td>
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
		
		var successstr = "新增成功";
		
		var url_to =  "<%=request.getContextPath()%>/unitadmin/add";
		var _id = $("input[name=_id]").val();
		if (_id != ""){
			url_to = "<%=request.getContextPath()%>" + "/unitadmin/"+ _id +"/update";
			successstr = "修改成功";
		}
		
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
	        			$.showBRErrors_mou(data['brErrors'],$("#baseinfo"));
	        		}else {
	        			layer.alert(data['message']);
	        		}
	        	}else{
	        		var _id= data['message'];
	        		$("input[name=_id]").val(_id);
	        		
	        		layer.alert( successstr, 1);
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