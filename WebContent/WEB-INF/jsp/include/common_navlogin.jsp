<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<input type="hidden" id="username_logined" name="username_logined" value="${nickname }" />
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<%=request.getContextPath()%>/home" style="font-size: 38px;">UMC</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <form id="login_form" class="navbar-form navbar-right">
        <div class="form-group hidden">
          <input id="login_usrname" name="login_usrname"  type="text" placeholder="账号" class="form-control">
        </div>
        <div class="form-group hidden">
          <input id="login_usrpassword"   name="login_usrpassword" type="password" placeholder="密码" class="form-control">
        </div>
        <button id="login_btn" style = "padding: 6px 12px!important; margin: 0 2px !important;"  type="button" class="btn btn-success">登录</button>
        <button id="signup_btn" style = "padding: 6px 12px!important; margin: 0 2px !important;" type="button" class="btn btn-primary">注册</button>
      </form>
      <ul id="loginuserinf" class="nav navbar-nav navbar-right" >
      	<p class="navbar-text" style="margin-right: 5px;">你好,<span id="loginuser"></span></p>
		<li>
			<a id="btn_logout" style="padding-left: 5px; padding-top: 13px;" href="#">[退出]</a>
		</li>
	  </ul>
    </div>
  </div>
</nav>

