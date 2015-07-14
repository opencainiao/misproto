<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

.ipt {
	background-color: #fff;
	background-image:url("<%=request.getContextPath()%>/resources/image/ipt-sprite.png");
	background-repeat: no-repeat;
}

.ipt {
	background-color: #ffffff;
	border: 1px solid #98a1a6;
	border-radius: 0;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
	color: #14191e;
	font-size: 14px;
	width: 250px;
	height: 20px;
	line-height: 20px;
	padding: 9px 9px 9px 48px;
	transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s
		ease-in-out 0s;
	height: 20px;
}

.ipt-pwd {
	background-position: 0 -48px;
	
	 margin-top: 30px;
}

.rlf-tip-wrap {
	font-size: 12px;
	height: 30px;
	text-align: left;
	margin-top: 0px;
	margin-bottom: 0px;
}

.placeholder {
	color: #98a1a6;
}

.btn-full {
	width: 100%;
}

.btn-red {
	background-color: #cc0000;
	border-color: #cc0000;
	border-style: solid;
	border-width: 1px;
	color: #ffffff;
	cursor: pointer;
	font-size: 14px;
	height: 38px;
	line-height: 20px;
	transition: all 0.3s ease 0s;
}
</style>

<script type="text/javascript">
	$().ready(function() {
		$('input, textarea').placeholder();
		
		$("#touserlogin").bind("click", function() {
			var url_to = "<%=request.getContextPath()%>/userlogin?from=resetpwdsuccess";
			window.location.href = url_to;
		});
		
		var validresult = '${validresult}';
		
		if (validresult  == '03'){
			$("#success_div").hide();
			$("#fail_div").hide();
		}else{
			$("#fail_div").show();
			$("#success_div").hide();
			$("#resetpwd_form").hide();
		}
		
		$("input").each(function() {
			$(this).on('blur', function() {
				$(this).next(".rlf-tip-wrap").find("span").hide();
				validate($(this));
			})

			$(this).on('focus', function() {
				if ($(this).val().trim() == "") {
					$(this).next(".rlf-tip-wrap").find("span").show();
				}
			})

			$(this).on('keyup', function() {
				// 禁止输入空格
				$(this).val($(this).val().replace(/(^\s+)|(\s+$)/g, ""));

				if ($(this).val().trim() != "") {
					validate($(this));
				}
			});
		});

		$("#reset_pwd_btn").bind("click", function() {

			$.disableButton("reset_pwd_btn");
			
			var flag = true;

			$("input").each(function() {
				if (!validate($(this))) {
					flag = false;
				}
			});

			if (!flag){
				$.enableButton("reset_pwd_btn");
				return false;
			}
			
			var url_to = "<%=request.getContextPath()%>/resetpassword";
			var paramForm = $('#resetpwd_form').getFormParam_ux();
			paramForm["email"] = '${email}';
			paramForm["resetpwdcode"] = '${resetpwdcode}';
			
			//alert( JSON.stringify(paramForm));

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
						layer.alert(message);
					} else {
						$("#success_div").show();
						$("#resetpwd_form").hide();
						$("#fail_div").hide();
					}
				},
				complete : function(XMLHttpRequest, textStatus) {

					// 控制按钮为可用
					$.enableButton("reset_pwd_btn");
				}
			});

		});
	});

	var isValidPassword = function(password) {
		if (password.length > 16 || password.length < 6) {
			return false;
		}

		return true;
	}
	
	var validate = function(obj) {

		var type = obj.attr('data-validate');
		var val = obj.val().trim();
		var span = $(obj).next(".rlf-tip-wrap").find("span");

		var flag = true;

		if (type == "password") {
			if (val == "") {
				span.html("请输入密码");
				flag = false;
			} else if (!isValidPassword(val)) {
				span.html("请输入6-16位密码，不能使用空格");
				flag = false;
			}
		}

		if (!flag) {
			span.css("color", "red");
			span.show();
		} else {
			span.hide();
		}

		return flag;
	}
</script>
</head>
<body>
	<div class="Center-Container is-Inline">
		<div class="Center-Block" style="width: 650px;">
			<div class="box_headder">
				<h1
					style="margin-top: 0px; padding-top: 10px; padding-left: 10px; color: #fff;">UMC
					用户重置密码</h1>
			</div>
			<div class="box_content">
				<form id="resetpwd_form">
					<div>
						<input type="password" name="password" data-validate="password"
							class="ipt ipt-pwd" placeholder="请输入新密码" />
						<p class="rlf-tip-wrap" style='height: 25px; color: rgb(34, 34, 34); font-family: "Helvetica","Arial",sans-serif; font-size: 16px; margin: 0px 0px 10px; padding-left: 170px; text-align: left;'>
							<span style="display: none">请输入6-16位密码，区分大小写，不能使用空格</span>
						</p>
					</div>
					<div class="rlf-group clearfix">
						<p class="rlf-tip-wrap rlf-g-tip"
							style="margin-top: 0px; height: 10px;"></p>
						<input type="button" id="reset_pwd_btn" value="提交"
							hidefocus="true" class="btn-red btn-full r"
							style="font-size: 20px; font-weight: bold; width: 310px;" />
					</div>
				</form>
				<div id="success_div">
					<img alt="重置密码成功" style="width: 160px; height: 160px;  margin-top: 20px;"
						src="<%=request.getContextPath()%>/resources/image/tick.png">
					<h1
						style="color: rgb(131, 161, 152); padding: 8px 16px 0px; font-size: 20px; text-decoration: none; word-break: break-all; margin-top: 18px; margin-bottom: 0px;">
						重置密码成功, &nbsp;<a id="touserlogin" style="text-decoration: underline; cursor: pointer;">登陆</a> &nbsp;UMC</h1>
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