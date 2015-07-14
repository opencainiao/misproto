<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>退出</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/js/front/login/base.css"
	type="text/css">  
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/js/front/login/settings.css"
	type="text/css">
<style type="text/css">

.navbar-brand {
    font-size: 38px !important;
}

.wcontainer {
	min-height: 650px;
}

.logout-wrap {
	padding: 48px 0;
}

.logout-left {
	text-align: center;
	padding-top: 40px;
}

.logout-left i {
	display: inline-block;
	width: 120px;
	height: 120px;
	/*background: url(/static/images/rl-sprite.png) no-repeat 0 -130px;*/
}

.logout-left p {
	font-size: 24px;
	color: #656e73;
	padding: 35px 0 30px;
}

.logout-left a:link, .logout-left a:visited {
	color: #39b94e;
}

.logout-left a:hover {
	text-decoration: underline;
}

.logout-left {
	float: left;
	width: 320px;
}

.logout-right {
	position: relative;
	margin-left: 320px;
	border-left: 1px solid #edf0f2;
	padding-left: 60px;
}

.field-wrap {
	width: 520px;
	position: relative;
}

#info, #contact {
	border: 1px solid #d0d6d9;
	width: 260px;
	font-size: 14px;
	transition: border-color 0.2s;
	-webkit-transition: border-color 0.2s;
	-moz-transition: border-color 0.2s;
	-o-transition: border-color 0.2s;
	padding: 7px;
}

#info:focus, #contact:focus {
	border-color: #7bd089;
}

#info {
	width: 500px;
	height: 200px;
	resize: none;
	overflow: hidden;
}

#contact {
	vertical-align: middle;
	width: 500px;
	height: 30px;
	line-height: 24px;
	font-size: 14px;
	padding: 5px;
}

.logout-right button {
	display: inline-block;
	margin-right: 5px;
	vertical-align: middle;
	color: #fff;
	font-size: 14px;
	background-color: #39b94e;
	height: 40px;
	line-height: 40px;
	width: 190px;
	cursor: pointer;
	text-align: center;
	transition: background-color 0.2s;
	-webkit-transition: background-color 0.2s;
	-moz-transition: background-color 0.2s;
	-o-transition: background-color 0.2s;
}

.logout-right button:hover {
	background-color: #33a646;
}

.logout-right h2 {
	margin-bottom: 12px;
}

.logout-right .rlf-tip-wrap {
	font-size: 12px;
	height: 30px;
}

.logout-right .rlf-tip-error {
	background: url(static/images/rl-sprite.png) no-repeat -108px -934px;
	padding-left: 15px;
	color: #be3948;
}

.logout-right .error-field {
	border-color: #be3948 !important;
}

.placeholder {
	color: #d0d6d9;
	font-size: 14px;
}

.phd:-moz-placeholder {
	color: #d0d6d9;
	font-size: 14px;
}

.phd::-moz-placeholder {
	color: #d0d6d9;
	font-size: 14px;
}

.phd::-webkit-input-placeholder {
	color: #d0d6d9;
	font-size: 14px;
	z
}

.phd:-ms-input-placeholder {
	color: #d0d6d9;
	font-size: 14px;
}

.us-join-qq {
	display: inline-block;
	width: 160px;
	height: 40px;
	line-height: 40px;
	vertical-align: middle;
	background: #2ea7e0;
	text-align: center;
}

.us-join-qq:link, .us-join-qq:visited {
	color: #fff;
}

.us-join-qq:hover {
	background: #2996c9;
}

.us-join-qq i {
	display: inline-block;
	width: 24px;
	height: 20px;
	margin-right: 4px;
	vertical-align: middle;
	background: url(/static/images/us-sprite.png) no-repeat -76px -110px;
}

.result-wrap {
	display: none;
	text-align: center;
	padding: 100px 60px 100px 0;
}

.result-wrap h2 {
	font-size: 24px;
	color: #656e73;
	line-height: 1.5em;
}

.result-wrap a {
	display: inline-block;
	margin-top: 15px;
}

.result-wrap a:link, .result-wrap a:visited {
	color: #39b94e;
}

.qqGroup {
	position: absolute;
	right: -220px;
	top: 20px;
	padding-top: 10px;
	line-height: 2.5em;
}

.qqGroup a {
	padding-left: 10px;
	color: #008000;
}

.qqGroup span {
	padding-left: 10px;
}
</style>
<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>
</head>
<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
<jsp:include page="/WEB-INF/jsp/include/common_navlogin.jsp"></jsp:include>
<div class="wcontainer" style="margin-top:65px">
    <div class="wwrap wrap-boxes" style="padding: 5px 0px 0px;">
        <div class="wheader-wrap" style="padding-bottom: 10px; padding-left: 20px;">
            <h1>退出登录</h1>
        </div>
        <div class="logout-wrap clearfix">
            <div class="logout-left">
                <i></i>
                <p class="pwd-rstsuc-txt">您已成功退出</p>
                <a href="<%=request.getContextPath()%>/materialtypehome/list">返回首页</a>
            </div>
            <div class="logout-right">
                <div class="result-wrap" id="continue_fillback_div">
                    <h2>提交成功</h2>
                    <p>感谢您的建议，我们会努力做的更好！</p>
                    <a href="#" id="continue_fillback_link" hidefocus="true">继续添加建议</a>
                </div>
                <div class="field-wrap" id="fillback_div">
                    <h2 style=" color: #656e73;  font-size: 24px;">意见反馈</h2>
                    <div>
                        <textarea name="info" id="info" class="phd" cols="30" rows="10" placeholder="骚年，哪有不爽，来喷吧！"></textarea>
                        <p class="rlf-tip-wrap"></p>
                    </div>
                    <div>
                        <input type="text" name="contact" id="contact" class="phd" placeholder="求联系方式（邮箱、qq），产品经理给您跪了！">
                        <p class="rlf-tip-wrap"></p>
                    </div>
                     <div class="btn-wrap clearfix">
                        <button hidefocus="true" id="btn_fillback"  type="button" class="btn btn-success btn-lg"  style="padding-top: 0px; padding-bottom: 0px;">提交</button>
                        <p id="feedback-error" class="rlf-tip-wrap rlf-tip-error" style="display:none"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
<script type="text/javascript"
		src="<%=request.getContextPath()%>/resources/js/front/login/login.js"></script>
<script>

	$().ready(function() {
		registerlogin();
		$("#btn_fillback").bind("click", submit_fillback);
		$("#continue_fillback_link").bind("click",function(){
			$("#fillback_div").show();
    		$("#continue_fillback_div").hide();
    		$("#info").val("");
		})
	});

	//保存
	var submit_fillback = function() {
		
		// 控制按钮为禁用
		$.disableButton("submit_fillback");

		var paramForm = {};
		
		paramForm["info"] = $("#info").val().trim();
		paramForm["contact"] = $("#contact").val().trim();
		
		if (paramForm["info"]==""){
			layer.alert("请输入反馈信息");
			return false;
		}
		
		var url_to =  "<%=request.getContextPath()%>/fillback/submit";
		
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
	        			$.alertBRErrors_mou(data['brErrors']);
	        		}else {
	        			layer.alert(data['message']);
	        		}
	        	}else{
	        		$("#fillback_div").hide();
	        		$("#continue_fillback_div").show();
	        	}
	        },
	        complete:function(XMLHttpRequest, textStatus){
	        	// 控制按钮为可用
	        	$.enableButton("submit_fillback");
	        }
	    }); 
	};
</script>
</body>
</html>
