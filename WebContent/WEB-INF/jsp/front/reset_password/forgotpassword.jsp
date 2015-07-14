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
	max-width: 99%;
	/* Prevents issues with long content causes the content block to be pushed to the top */
	/* max-width: calc(100% - 0.25em) /* Only for IE9+ */
}

.ipt {
	background-color: #fff;
	background-image: url("<%=request.getContextPath()%>/resources/image/ipt-sprite.png");
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

.ipt-email {
	background-position: 0 0;
}

.ipt-pwd {
	background-position: 0 -48px;
}

.ipt-nick {
	background-position: 0 -96px;
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
		
		$("#success_div").hide();
		
		$(window.parent.document).find(".xubox_tabnow").html("UMC 重设密码");

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

		$("#send_email_btn").bind("click", function() {

			$.disableButton("send_email_btn");
			
			var flag = true;

			$("input").each(function() {
				if (!validate($(this))) {
					flag = false;
				}
			});

			if (!flag){
				$.enableButton("send_email_btn");
				return false;
			}
			
			var url_to = "<%=request.getContextPath()%>/sendresetpasswordemail";
			var paramForm = $('#send_email_form').getFormParam_ux();
			
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
						$("#send_email_form").hide();
					}
				},
				complete : function(XMLHttpRequest, textStatus) {

					// 控制按钮为可用
					$.enableButton("send_email_btn");
				}
			});
			
		});
	});

	var validate = function(obj) {

		var type = obj.attr('data-validate');
		var val = obj.val().trim();
		var span = $(obj).next(".rlf-tip-wrap").find("span");

		var flag = true;

		if (type == "email") {
			if (val == "") {
				span.html("请输入您注册的邮箱");
				flag = false;
			} else if (val.isValidMail()) {
				span.html("邮箱格式不正确");
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
		<div class="Center-Block">
			<form id="send_email_form">
				<div >
					<input type="text" name="email" data-validate="email"
						class="ipt ipt-email"  placeholder="请输入登录邮箱" />
					<p class="rlf-tip-wrap">
						<span style="display: none">请输入您有效的邮箱</span>
					</p>
				</div>
				<div class="rlf-group clearfix">
					<p class="rlf-tip-wrap rlf-g-tip" style="margin-top: 0px; height: 10px; "></p>
					<input type="button" id="send_email_btn" value="发送重置密码邮件" hidefocus="true"
						class="btn-red btn-full r"  style="font-size: 20px; font-weight: bold"/>
				</div>
			</form>
			
			<div id="success_div">
				<img alt="发送重置密码邮件成功" style="width: 160px; height: 160px;"
						src="<%=request.getContextPath()%>/resources/image/tick.png">
				<h1 style="color: rgb(131, 161, 152); padding: 8px 16px 0px;font-size:20px ;text-decoration: none; word-break: break-all; margin-top: 18px; margin-bottom: 0px;">
					我们已发送重置密码邮件至您的邮箱中
				</h1>
				<h1 style="color: rgb(131, 161, 152); padding: 8px 16px 0px;font-size:20px ;text-decoration: none; word-break: break-all; margin-top: 18px; margin-bottom: 0px;">
					请您查收邮件并根据引导重置密码。
				</h1>
			</form>
		</div>
	</div>
</html>