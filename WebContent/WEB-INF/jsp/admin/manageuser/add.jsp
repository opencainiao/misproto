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

</style>
</head>
</head>
<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
<input type="hidden" name="page-title" value="添加管理用户" />
<ul class="breadcrumb">
    <li><a href="<%=request.getContextPath()%>/useradmin/list">系统管理用户管理</a> <span class="divider"></span></li>
    <li class="active">添加管理用户</li>
</ul>
<div class="colOne" id="manageuser_add_div">
	<!-- 此时没有写action,直接提交会提交给/add -->
	<sf:form method="post" modelAttribute="manageuser" enctype="multipart/form-data" class="box">
		<table class="formtable_s">
			<tbody>
				<tr >
					<th>用户名(必须是英文)</th>
					<td><sf:input path="username" id="username"/></td>
					<td><span class="error" id="username_err"></span></td>
				</tr>
				<tr >
					<th>昵称(可以是中文)</th>
					<td><sf:input path="nickname" id="nickname"/></td>
					<td><span class="error" id="nickname_err"></span></td>
				</tr>
				<tr >
					<th>用户密码</th>
					<td><sf:password path="password" id="password"/></td>
					<td><span class="error" id="password_err"></span></td>
				</tr>
				<tr >
					<th>联系电话</th>
					<td><sf:input path="phone" id="phone"/></td>
					<td><span class="error" id="phone_err"></span></td>
				</tr>
				<tr >
					<th>电子邮件</th>
					<td><sf:input path="email" id="email"/></td>
					<td><span class="error" id="email_err"></span></td>
				</tr>
				<tr>
					<th>状态</th>
					<td>
						<select name = "status">
							<option value="0">未启用</option>
							<option value="1">启用 </option>
							<option value="2">已冻结 </option>
							<option value="3">已删除</option>
						</select>
					</td>
				</tr>
				<tr >
					<th>角色</th>
					<td>
						<c:forEach var="role" items="${roles }">
							${role.name }<input type="checkbox" name="ROLEIDS" value="${role._id }"/>
						</c:forEach>
					</td>
					<td><span class="error" id="roleids_err"></span></td>
				</tr>
			</tbody>
		</table>
		
		<div class="i_do_btns">
			<input id="btn_save"  type="button" class="btns block margin_auto" value="提交"/>
		</div>
	</sf:form>
</div>
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou.ajax.js"></script>
	
	<script>
	
	$().ready(function() {
		
		$("#btn_save").bind("click", save);
	});

	//保存
	var save = function() {
		
		// 控制按钮为禁用
		$.disableButton("btn_save");

		var paramForm = $('form').getFormParam_ux();
		
		var url_to =  "<%=request.getContextPath()%>/useradmin/add";
		
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
	        			$.showBRErrors(data['brErrors'],$("#manageuser_add_div"));
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