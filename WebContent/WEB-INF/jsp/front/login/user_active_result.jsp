<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户激活</title>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>

<style>
.xubox_tab_main {
	padding-left: 0px;
}

.Center-Container.is-Inline {
	margin-top: 50px;
	text-align: center;
	overflow: auto;
}

.Center-Container.is-Inline:after, .is-Inline .Center-Block {
	display: inline-block;
	vertical-align: middle;
}

.Center-Container.is-Inline:after {
	content: '';
	height: 100%;
	margin-left: -0.25em; /* To offset spacing. May vary by font */
}

.is-Inline .Center-Block {
	border-radius: 4px;
	max-width: 99%;
	background: none repeat scroll 0 0 #e3e5cf;
	/* Prevents issues with long content causes the content block to be pushed to the top */
	/* max-width: calc(100% - 0.25em) /* Only for IE9+ */
}

.box_headder {
	font-family: "Helvetica Neue", Helvetica, Arial;
	margin-top: 0px;
	background: none repeat scroll 0 0 #83a198;;
	height: 60px;
	text-align: left;
}

.box_content {
	background: none repeat scroll 0 0 #e3e5cf;
	height: 300px;
}

.success  h1 {
	color: #aad200;
}
</style>


<script type="text/javascript">
	$().ready(function() {
		hideOrshow();
		
		$("#touserlogin").bind("click", function() {
			var url_to = "<%=request.getContextPath()%>/userlogin?from=active";
			window.location.href = url_to;
		});
	});

	function hideOrshow() {
		var code = "${resultcode }";

		if (code == "00") {
			$("#success_div").show();
			$("#fail_div").hide();
		} else {
			$("#success_div").hide();
			$("#fail_div").show();
		}
	}

	function bindSendActiveEmail() {
		// 再次发送激活邮件
		$("#signup-btn").bind("click", function() {

			$.disableButton("signup-btn");

			var flag = true;

			$("input").each(function() {
				if (!validate($(this))) {
					flag = false;
				}
			});

			if (!flag) {
				$.enableButton("signup-btn");
				return false;
			}

			var paramForm = $('#signup-form').getFormParam_ux();

			$.ajax({
				type : 'POST',
				url : url_to,
				data : $.extend({
					ts : new Date().getTime()
				}, paramForm),
				type : 'POST',
				dataType : 'json',
				success : function(data) {

					var message = data['message'];

					// alert(data.success);
					if (data['success'] == 'n') {
						if (message == "01") {
							layer.alert("两次密码不一致");
						} else if (message == "02") {
							layer.alert("邮箱已被注册");
						}
					} else {
						layer.alert("注册成功", 1);
					}
				},
				complete : function(XMLHttpRequest, textStatus) {

					// 控制按钮为可用
					$.enableButton("signup-btn");
				}
			});

		});
	}
</script>
</head>
<body style="background-color: #f0f2e7">
	<div class="Center-Container is-Inline">
		<div class="Center-Block" style="width: 650px;">
			<div class="box_headder">
				<h1
					style="margin-top: 0px; padding-top: 10px; padding-left: 10px; color: #fff;">UMC
					用户激活</h1>
			</div>
			<div class="box_content">
				<div id="success_div" class="success" style="padding-top: 20px;">
					<img alt="激活成功" style="width: 160px; height: 160px;"
						src="<%=request.getContextPath()%>/resources/image/tick.png">
					<h1 style="color: rgb(131, 161, 152); padding: 8px 16px 0px; text-decoration: none; word-break: break-all; margin-top: 18px; margin-bottom: 0px;">
						用户激活成功, &nbsp;<a id="touserlogin" style="text-decoration: underline; cursor: pointer;">登陆</a> &nbsp;UMC
					</h1>
				</div>
				<div id="fail_div" class="fail" style="padding-top: 20px;">
					<img src="<%=request.getContextPath()%>/resources/image/sorry.png"
						style="width: 160px; height: 160px;" alt="激活失败">
					<h1 style="color: rgb(131, 161, 152); padding: 8px 16px; text-decoration: none; word-break: break-all; margin-top: 5px; margin-bottom: 0px;">
						抱歉，链接已失效</h1>
				</div>
			</div>
		</div>
	</div>
</html>