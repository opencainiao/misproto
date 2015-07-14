<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>

<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/admin/main.css"
	type="text/css">
<link rel="stylesheet"	
	href="<%=request.getContextPath()%>/resources/bootstrap-3.2.0-dist/css/bootstrap.css"
	type="text/css">
</head>
<body class="page">
	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	
    <ul class="breadcrumb">
        <li class="active">系统管理用户管理</li>
    </ul>
    
    <div id="content_inner_page" style="padding:15px;">
    	<ul class="breadcrumb">
    		<li><a href="add" class="btn btn-small btn-info">添加管理用户</a></li>
    	</ul>
	
		<div id="manageuser_manage" class="innerpage">
			<table cellspacing="0" cellPadding="0" id="listTable" class="listtbl_mou">
				<thead>
					<tr>
						<th style="width:180px">用户标识</th>
						<th style="width:120px">用户名称</th>
						<th style="width:160px">用户昵称</th>
						<th style="width:120px">用户状态</th>
						<th style="width:100px">用户邮箱</th>
						<th style="width:300px">用户操作</th>
					</tr>
				</thead>
				<c:if test="${ list!= null}"> 
					<tbody>
						<c:forEach items="${list }" var="manageuser">
							<tr>
								<td style="width:180px">${manageuser._id }&nbsp;</td>
								<td style="width:120px"><a href="javascript:manageuser_manage_functions.toDetail('${manageuser._id }')" class="list_link">${manageuser.username }</a></td>
								<td style="width:160px">${manageuser.nickname }&nbsp;</td>
								<td style="width:120px">
									<c:if test="${manageuser.userstate.code eq 0 }">
										<span>停用</span>
										<a href="javascript:manageuser_manage_functions.updatestatus('${manageuser._id }',1)" class="btn btn-xs btn-primary">启用</a>
									</c:if>
									<c:if test="${manageuser.userstate.code eq 1 }">
										<span>启用</span>
										<a href="javascript:manageuser_manage_functions.updatestatus('${manageuser._id }',0)" class="btn btn-xs btn-danger">停用</a>
									</c:if>
									<c:if test="${manageuser.userstate.code eq 2 }">
										<span>已冻结</span>
									</c:if>
									<c:if test="${manageuser.userstate.code eq 3 }">
										<span>已删除</span>
									</c:if>
									&nbsp;
								</td>
								<td style="width:100px">
									${manageuser.email }
								</td>
								<td style="width:300px">
									<a href="javascript:manageuser_manage_functions.delOne('${manageuser._id }')" class="btn btn-xs btn-danger">删除</a>
									<a href="javascript:manageuser_manage_functions.toEdit('${manageuser._id }')" class="btn btn-xs btn-primary">修改</a>
									<a href="<%=request.getContextPath() %>/admin/channel/manageuserchannels/${manageuser._id }" class="list_op">管理栏目</a>
								&nbsp;
								</td>
							</tr>
						</c:forEach>
					</tbody>
				 </c:if>
			</table>
		</div>
    </div>
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou.core.js"></script>
	
	<script>
	$().ready(function() {

	});

	var manageuser_manage_functions = {
		/***************************************************************************
		 * 更新用户状态
		 * 
		 * @param data
		 */
		updatestatus : function(_id,statuscod) {

			var url_to = $.getSitePath() + '/useradmin/' + _id + '/updatestatus';

			$.ajax({
				type : 'POST',
				url : url_to,
				data : {
					ts : new Date().getTime(),
					statuscod:statuscod
				},
				dataType : 'json',
				success : function(data) {
					//刷新页面;
					var url_refresh = $.getSitePath() + '/useradmin/list';
					window.location.href = url_refresh;
				}
			});
		},
		/***************************************************************************
		 * 删除管理用户
		 * 
		 * @param data
		 */
		delOne : function(_id) {

			var url_to = $.getSitePath() + '/useradmin/' + _id + '/delete';

			$.ajax({
				type : 'POST',
				url : url_to,
				data : {
					ts : new Date().getTime()
				},
				dataType : 'json',
				success : function(data) {
					
					if (data['success'] == 'n'){
		        		layer.alert(data['message']);
		        	}else{
		        		
		        		//刷新页面;
						var url_refresh = $.getSitePath() + '/useradmin/list';
						window.location.href = url_refresh;
		        	}
				}
			});
		},
		/***************************************************************************
		 * 进入管理用户修改页面
		 * 
		 * @param data
		 */
		toEdit : function(_id) {

			var url = $.getSitePath() + '/useradmin/' + _id + "/update";
			window.location.href = url;
		},
		/***************************************************************************
		 * 进入管理用户详细信息页面
		 * 
		 * @param data
		 * @returns {Boolean}
		 */
		toDetail : function(_id) {

			var url = $.getSitePath() + '/useradmin/' + _id;
			window.location.href = url;
		}
	};
	</script>
</body>
</html>