<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<input type="hidden" id="username_logined" name="username_logined"
	value="${nickname }" />
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header" style="float: left; width: 20%">
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/home">UMC</a>
		</div>
		<div id="navbar" style="float: right; width: 70%">
			<ul id="login_form" style="float: right; padding-top: 0px; color: rgb(157, 157, 157); margin-bottom: 0px; margin-top: 9px;">
			
				<li style="display: inline">
					<button id="login_btn" type="button" class="btn btn-success">登录</button>
				</li> 
				<li style="display: inline">
					<button id="signup_btn" type="button" class="btn btn-primary">注册</button>
				</li>
			</ul>
			<ul id="loginuserinf"
				style="float: right; padding-top: 15px; color: #9d9d9d">
				<li style="display: inline">你好,<span id="loginuser"></span>
				</li>
				<li style="display: inline"><a id="btn_logout"
					style="padding-left: 5px; padding-top: 0px;" href="#">[退出]</a></li>
			</ul>
		</div>

	</div>
</nav>

