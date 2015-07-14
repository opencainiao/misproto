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
<style>
table.formtable_s  td{
	width:500px
}

.space_center{
	text-align: center;
}
</style>
</head>
</head>
<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />

<ul class="breadcrumb">
    <li class="active">系统基本信息</li>
</ul>
<div class="colOne" id ="systembaseinfadmin_update_div">
	<sf:form method="post" modelAttribute="systembaseinfo" enctype="multipart/form-data" class="box">
		<input type="hidden" id="_id_m" name="_id_m" value=${ _id } />	
		<table class="formtable_s">
			<tbody>
				<tr>
					<th>主键</th>
					<td><input class="readonly" readonly="readonly" value="${systembaseinfo._id}"/></td>
				</tr>
				<tr >
					<th>网站名称</th>
					<td><sf:input path="name" class="required"/><span class="error" id="name_err"></span></td>
				</tr>
				<tr >
					<th>网站所在地址</th>
					<td><sf:input path="address"/><span class="error" id="address_err"></span>
					</td>
				</tr>
				<tr style="display:none">
					<th>邮政编码</th>
					<td><sf:input path="zipCode"/></td>
				</tr>
				<tr >
					<th>联系电话</th>
					<td><sf:input path="phone" class="required"/><span class="error" id="phone_err"></span></td>
				</tr>
				<tr >
					<th>网站联系邮箱</th>
					<td><sf:input path="email" class="required email"/><span class="error" id="email_err"></span></td>
				</tr>
			</tbody>
		</table>
	</sf:form>
	<div class="i_do_btns space_center">
		<input id="btn_save"  type="button" class="btn  btn-primary" value="保存"/>
		<input id="btn_cancel"  type="button" class="btn btn-primary" value="取消"/>
	</div>
</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou_grid.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou.ajax.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery-validation-1.13.1/dist/jquery.validate.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/jquery-validation-1.13.1/dist/localization/messages_zh.js"></script>
	
	
	<script type="text/javascript">
		$().ready(function() {
			
			$("#btn_save").bind("click", save);
			
			$("#btn_cancel").bind("click", cancel);
		});
	
		//保存
		var saveSubmit = function() {
			
			// 控制按钮为禁用
			$.disableButton("btn_save");
	
			var paramForm = $('form').getFormParam_ux();
			var url_to =  "<%=request.getContextPath()%>/systembaseinfadmin/update";
			
			$.ajax({
		        type:'POST',
		        url: url_to,
		        data : $.extend({
					ts : new Date().getTime()
				}, paramForm),
				type : 'POST',
				dataType : 'json',
		        success: function(data){
		        	if (data['success'] == 'n'){
		        		if (data['brErrors']){
		        			$.showBRErrors(data['brErrors'],$("#systembaseinfadmin_update_div"));
		        		}else {
		        			layer.alert(data['message']);
		        		}
		        	}else{
		        		layer.alert( "保存成功", 1);
		        	}
		        },
		        complete:function(XMLHttpRequest, textStatus){
		        	// 控制按钮为可用
		        	$.enableButton("btn_save");
		        }
		    }); 
		};
		
		function save(){
			
			var validator = $(".box").validate({
				debug: false
			});
			
			//alert(validator.form());
			
			if (validator.form()){
				saveSubmit();
			}
		}
		
		var cancel = function() {
			window.location.href = "<%=request.getContextPath()%>/systembaseinfadmin/update";
		}
	
	</script>
</body>
</html>