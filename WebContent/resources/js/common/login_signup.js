function show_login_sinup() {

	var loginurl = $.getSitePath() + ;
	var signupurl;
	
	var tabIframe = function(src) {
		return '<iframe frameborder="0" src="' + src + '" style="width:1000px; height:465px;"></iframe>';
	};
	layer.tab({
		area : [ '1000px', '500px' ],
		data : [ {
			title : '登陆',
			content : tabIframe(loginurl)
		}, {
			title : '注册',
			content : tabIframe(signupurl)
		}]
	});
}