;
(function($) {
	$.extend({
		/***********************************************************************
		 * 处理服务器的br校验错误 <br>
		 * 显示错误信息，并添加行为（输入后，错误显示隐藏）
		 */
		showBRErrors_mou_abs : function(obj, field) {

			for ( var k in obj) {

				var key = k + "_err";
				
				var value = obj[k];

				if ($("#" + key, field).length == 0){
					
					var $objk = $("#" + k, field);
					
					var top = $objk.offset().top + 3;
					var l_k = $objk.offset().left + $objk.outerWidth( true ) + 5;
					
					var insertHtml = $('<div id="'+ key + '" style="position:absolute; top:'+ top +'px;left:' + l_k + 'px" class="error">' + value + '</div>');
					
					insertHtml.appendTo(field);
				}
				
				$("#" + key, field).removeClass("hidden");

				$("#" + k, field).bind('keyup', function() {
					var ele = $(this).attr("id") + "_err";
					$("#" + ele, field).addClass("hidden");
				});
			}
		},
		/***********************************************************************
		 * 处理服务器的br校验错误 <br>
		 * 显示错误信息，并添加行为（输入后，错误显示隐藏）
		 */
		alertBRErrors_mou : function(obj) {

			var insertHtml = "";
			for ( var k in obj) {

				var value = obj[k];

				insertHtml = insertHtml + "\n" + value ;
			}
			
			layer.alert(insertHtml);
		}
	});
	
	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		getLength : function() {
			var trimThis = this.trim();
			var realLength = 0;
			var len = trimThis.length;
			var charCode = -1;

			for (var i = 0; i < len; ++i) {
				charCode = trimThis.charCodeAt(i);
				if (charCode >= 0 && charCode <= 128) {
					realLength += 1;
				} else {
					realLength += 2;
				}
			}
			return realLength;
		},
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		startsWith:function (pattern){
			return this.indexOf(pattern) === 0;
		},
		endsWith:function(pattern) {
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceSuffix:function(index){
			return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
		},
		trans:function(){
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		encodeTXT: function(){
			return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		},
		replaceAll:function(os, ns){
			return this.replace(new RegExp(os,"gm"),ns);
		},
		replaceTm:function($data){
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				return $data[$1.replace(/[{}]+/g, "")];
			});
		},
		replaceTmById:function(_box){
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.val() ? $input.val() : $1;
			});
		},
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this)); 
		},
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this)); 
		},
		isValidMail:function(){
			var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
			return !reg.test(this);
		},
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
	    }
	});
})(jQuery);
;
