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
<ul class="breadcrumb">
    <li class="active">修改用户密码</li>
</ul>
<div class="colOne" id="passwordreset_div">
	<!-- 此时没有写action,直接提交会提交给/add -->
	<sf:form method="post" modelAttribute="passwordreset"  enctype="multipart/form-data" class="box">
		<table class="formtable_s" style="margin-top:50px; margin-bottom:30px">
			<tbody>
				<tr >
					<th>原密码</th>
					<td><sf:input path="oripassword" id="oripassword"/></td>
					<td><span class="error" id="oripassword_err"></span></td>
				</tr>
				<tr >
					<th>新密码</th>
					<td><sf:input path="newpassword1" id="newpassword1"/></td>
					<td><span class="error" id="newpassword1_err"></span></td>
				</tr>
				<tr >
					<th>新密码</th>
					<td><sf:password path="newpassword2" id="newpassword2"/></td>
					<td><span class="error" id="newpassword2_err"></span></td>
				</tr>
			</tbody>
		</table>
		<div class="i_do_btns"  >
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
		
		//$.vpart_l_center($("#passwordreset_div"), $(".formtable_s"), 30) ;
		
	});

	//保存
	var save = function() {
		
		// 控制按钮为禁用
		$.disableButton("btn_save");

		var paramForm = $('form').getFormParam_ux();
		
		var url_to =  "<%=request.getContextPath()%>/passwordresetadmin/update";
		
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
	        			$.showBRErrors(data['brErrors'],$("#passwordreset_div"));
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