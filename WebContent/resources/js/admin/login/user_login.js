var ctx;
$().ready(function() {
	ctx = $("input:hidden[name=ctx]").val();
	
	v_l_center_login($("body"), $(".center_c"));
	
	// 处理提交按纽图片
	$("#submitBtn").mouseover(function() {
		this.src = ctx + "/images/login_submitBtn2.gif";
	}).mouseout(function() {
		this.src = ctx + "/images/login_submitBtn1.gif";
	}).click(function() {
		submitForm();
	});


	// 设置验证码图片框显隐
	//showHideVcodeImg();

	// 绑定验证码图片框单击事件实现更换验证码
	$("div.validateCodeDiv").click(function(event) {
		changeCode();
		event.stopPropagation();
	});
});

function submitForm() {
	//alert("submit");
	var oAcc = $("input[name=ure]");
	if (oAcc.val().trim().length == 0) {
		layer.alert( "请输入用户名", 0);
		oAcc.focus();
		return;
	}
	var oPass = $("input[name=password]");
	if (oPass.val().trim().length == 0) {
		layer.alert( "请输入密码", 1);
		oPass.focus();
		return;
	}
	var oVcode = $("input[name=vcode]");
	if (oVcode.val().trim().length == 0) {
		layer.alert( "请输入验证码", 1);
		oVcode.focus();
		return;
	}
	
	var url_to = $.getSitePath() + '/adminlogin';
	var para = $("form[name=frm1]").getFormParam_ux();
	
	$.ajax({
		type : 'POST',
		url : url_to,
		data : $.extend({
			ts : new Date().getTime()
		}, para),
		dataType : 'json',
		success : function(data) {
			
			//alert(JSON.stringify(data))
			
			if (data['success'] == 'n') {
				
				changeCode(); // 加在此处，防止暴力破解
				
				layer.alert( data['message'], 8);
				/*setTimeout(function(){
					v_l_center_login($("body"), $(".xubox_layer"));
					$(".xubox_layer").css("display","block");
				}, 20);
				*/
				return;
			}
			
			var successUrl = $.getSitePath() + '/admin/home';
			location = successUrl;
		}
	});
}

function showHideVcodeImg() {
	$("input[name=account], input[name=passwd], #submitBtn").click(function() {
		$("div.validateCodeDiv").css("display", "none");
	}).focus(function() {
		$("div.validateCodeDiv").css("display", "none");
	});

	$("input[name=vcode]").click(function(event) {
		$("div.validateCodeDiv").css("display", "block");
		event.stopPropagation();
	}).focus(function() {
		$("div.validateCodeDiv").css("display", "block");
	});

	$(document).click(function() {
		$("div.validateCodeDiv").css("display", "none");
	});
}

function changeCode() {
	$("#imgVcode").attr("src",
			ctx + "/ValCode/getValCode?ts=" + new Date().getTime());
}

function v_l_center_login(containerdiv, centerdiv) {

	centerdiv.css({
		position : "absolute"
	});
	centerdiv.css({
		top : "50%",
		left : "50%"
	});

	var height_ref_button = 0 - centerdiv.height() / 2;
	var width_ref_button = 0 - centerdiv.width() / 2;
	centerdiv.css({
		"margin-top" : height_ref_button
	});
	centerdiv.css({
		"margin-left" : width_ref_button
	});

}
