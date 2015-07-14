function registerlogin() {
	$("#login_btn").click(function() {
		//login_from_head();
		show_login_sinup("login");
	});
	
	$("#btn_logout").click(function() {
		logout_from_head();
	});

	$("#signup_btn").click(function() {
		show_login_sinup("signup");
	});
	
	var username_logined = $("#username_logined").val().trim();
	if (username_logined != "") {
		$("#loginuser").html(username_logined);
		hideLogin();
	} else {
		showLogin();
	}
}

function loginwith(nickname){
	
	$("#username_logined").val(nickname);
	$("#loginuser").html(nickname);
	hideLogin();
}

function show_login_sinup(login_or_signup) {

	var loginurl = $.getSitePath() + "/userlogin";
	var signupurl = $.getSitePath() + "/usersignup";;
	
	var tabIframe = function(src) {
		return '<iframe frameborder="0" src="' + src + '" style="width:480px; height:500px;"></iframe>';
	};
	
	/*
	var aa = layer.tab({
		area : [ '480px', '500px' ],
		data : [ {
			title : '登陆',
			content : tabIframe(loginurl)
		}, {
			title : '注册',
			content : tabIframe(signupurl)
		}]
	});
	*/
	
	var url = loginurl;
	
	if (login_or_signup == "signup"){
		url = signupurl;
	}
	
	var aa = layer.tab({
		area : [ '480px', '500px' ],
		data : [ {
			title : '注册',
			content : tabIframe(url)
		}]
	});
	 
	var divid = "xubox_layer" + aa;
	var tabcontainer = $(".xubox_tabtit",$("#" + divid));
	
	tabcontainer.css("height","50px");
	
	$("span",tabcontainer).css("height","50px").css("line-height","50px").css("font-size","16px");
	$("span.xubox_tabclose",$("#" + divid)).css("right","20px").css("top","12px");
}

function showLogin() {
	$("#loginuserinf").hide();
	$("#login_form").show();
}

function hideLogin() {
	$("#loginuserinf").show();
	$("#login_form").hide();
}

function login_from_head() {

	var username = $("#login_usrname").val().trim();
	var userpassword = $("#login_usrpassword").val().trim();

	if (username == "") {
		$("#login_usrname").val("");
		alert("请输入用户名");
		return false;
	} else if (userpassword == "") {
		$("#login_usrpassword").val("");
		alert("请输入密码");
		return false;
	}

	$.disableButton("login_btn");

	var paramForm = $('#login_form').getFormParam_ux();
	paramForm["ure"] = paramForm["login_usrname"];
	paramForm["password"] = paramForm["login_usrpassword"];

	// 登陆系统
	var url_to = $.getSitePath() + "/userlogin";

	$.ajax({
		type : 'POST',
		url : url_to,
		data : $.extend({
			ts : new Date().getTime()
		}, paramForm),
		type : 'POST',
		dataType : 'json',
		success : function(data) {
			// layer.alert( JSON.stringify(data), 8);
			// alert(data.success);
			if (data['success'] == 'n') {
				layer.alert(data['message']);
			} else {
				var message = $.parseJSON(data['message']);
				//layer.alert(JSON.stringify(message), 8);
				$("#username_logined").val(message.nickname);
				$("#loginuser").html(message.nickname);
				hideLogin();
			}
		},
		complete : function(XMLHttpRequest, textStatus) {

			// 控制按钮为可用
			$.enableButton("login_btn");
		}
	});
}

function logout_from_head() {

	$("#loginuser").html("");
	$("#username_logined").val("");
	showLogin();
	// 控制按钮为可用
	$.enableButton("login_btn");
	
	// 登陆系统
	var url_to = $.getSitePath() + "/userlogout";
	window.location.href = url_to;
	return;
	
	$.ajax({
		type : 'POST',
		url : url_to,
		data : {
			ts : new Date().getTime()
		},
		type : 'POST',
		dataType : 'html',
		success : function(data) {
			
			//alert(data);
			// layer.alert( JSON.stringify(data), 8);
			
			$('body').html(data);
			
		},
		complete : function(XMLHttpRequest, textStatus) {
			$("#loginuser").html("");
			$("#username_logined").val("");
			showLogin();
			// 控制按钮为可用
			$.enableButton("login_btn");
		}
	});
	
	$("#login_usrname").val("");
	$("#login_usrpassword").val("");
	
	return false;
}