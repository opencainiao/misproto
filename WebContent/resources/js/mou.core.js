/*******************************************************************************
 * 系统全局变量定义
 */
var mou_sys = {
	ctx : '' // 系统ctx路径
};
(function($) {
	$.fn.isExist_ux = function() {
		return this.length > 0;
	};
})(jQuery);

/*******************************************************************************
 * 扩展字符串功能
 */
$.extend(String.prototype, {
	/***************************************************************************
	 * 取字符串长度
	 * 
	 * @returns {Number}
	 */
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
	}
});

// 封装全局函数
;
(function($) {
	$.extend({
		/***********************************************************************
		 * 设置系统路径
		 * 
		 * @returns
		 */
		setSitePath1 : function(ctx) {
			mou_sys.ctx = ctx;
		},
		/***********************************************************************
		 * 取系统路径
		 * 
		 * @returns
		 */
		getSitePath1 : function() {
			return mou_sys.ctx;
		},
		/***********************************************************************
		 * 判断对象是否是array类型 <br>
		 * aa = [1,2,3]; dd = $.isArray(aa);
		 * 
		 * @param obj
		 * @returns {Boolean}
		 */
		isArray_nnnnn : function(obj) {
			return Object.prototype.toString.call(obj) === '[object Array]';
		},
		isIE : function() {
			var browser = navigator.appName;
			if (browser == "Microsoft Internet Explorer") {
				return true;
			}

			return false;
		},
		ieVersion : function() {
			var browser = navigator.appName
			var b_version = navigator.appVersion
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return 6;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE7.0") {
				return 7;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE8.0") {
				return 8;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE9.0") {
				return 9;
			}
		},
		getNavigatorType : function() {
			if (navigator.userAgent.indexOf("MSIE") > 0) {
				return "MSIE";
			}
			if (isFirefox = navigator.userAgent.indexOf("Firefox") > 0) {
				return "Firefox";
			}
			if (isSafari = navigator.userAgent.indexOf("Safari") > 0) {
				return "Safari";
			}
			if (isOpera = navigator.userAgent.indexOf("Opera") > 0) {
				return "Opera";
			}
			if (isCamino = navigator.userAgent.indexOf("Camino") > 0) {
				return "Camino";
			}
			if (isMozilla = navigator.userAgent.indexOf("Gecko/") > 0) {
				return "Gecko";
			}
		},
		/***********************************************************************
		 * 判断obj是否有propName属性
		 * 
		 * @param obj
		 * @param propName
		 * @returns {Boolean}
		 */
		hasPro : function(obj, propName) {
			return (propName in obj);
		},
		/***********************************************************************
		 * 单form的添加和编辑页面的布局设置
		 */
		pageLayOutForDetail_oneForm : function() {

			var window_h = $(window).height();
			var formdiv_h = $(".formdiv").height();
			var innerPage_h = $(".innerPage").height();

			if ((formdiv_h + 15) <= innerPage_h) {
				$(".innerPage").height(innerPage_h - 15);
			}
			//
			// var arr = [];
			// arr.push("window_h--" + $(window).height());
			// arr.push("innerPage_h--" + $(".innerPage").height());
			// arr.push("formdiv_h--" + $(".formdiv").height());
			// alert(arr.join("\n"));
		},
		/***********************************************************************
		 * 取节点的符合条件的子节点 <br>
		 * var node_ = $.getChildrenNodeByParam_ux(node2,"dptnum", "000071"); //
		 * alert(jsonToString(node_));
		 * 
		 * @param node
		 * @param key
		 * @param value
		 * @returns
		 */
		getChildrenNodeByParam_ux : function(node, key, value) {
			if (!node || !key)
				return null;

			var nodes = node.children;
			for (var i = 0, l = nodes.length; i < l; i++) {
				if (nodes[i][key] == value) {
					return nodes[i];
				}
			}
			return null;
		},
		/***********************************************************************
		 * 控制按钮为禁用
		 * 
		 * @param btnId
		 */
		disableButton : function(btnId) {
			$("#" + btnId).attr({
				"disabled" : "disabled"
			});
		},
		/***********************************************************************
		 * 控制按钮为可用(延迟两秒)
		 * 
		 * @param btnId
		 */
		enableButton : function(btnId) {
			setTimeout(function() {
				$("#" + btnId).removeAttr("disabled");
			}, 0);
		},
		/***********************************************************************
		 * 控制按钮为可用(延迟单位-秒)
		 * 
		 * @param btnId
		 */
		enableButtonTime : function(btnId, time) {
			setTimeout(function() {
				$("#" + btnId).removeAttr("disabled");
			}, 2 * time);
		},
		/***********************************************************************
		 * 渲染下拉列表（DDL方式）
		 * 
		 * @returns
		 */
		renderplugins : function() {
			$("select").each(function() {
				$(this).ddslick({
					selectText : "请选择"
				});
			});

			var width = "162px";
			var height = "20px";
			var backgroundcolor = "#FFFFFF";

			// render ddl插件
			if ($.isIE()) {
			} else {
				width = "160px";
			}

			$(".dd-container").css({
				width : width
			}).css({
				height : "20px"
			});

			$(".dd-select").css({
				width : width
			}).css({
				height : height
			}).css({
				background : backgroundcolor
			}).css({
				border : "1px solid #999999"
			});

			$(".dd-selected").css({
				padding : "0px"
			});

			$(".dd-options").css({
				width : width
			});
			$(".dd-option").css({
				padding : "1px"
			});
		}
	});
})(jQuery);

;
(function($) {
	$.extend({
		/***********************************************************************
		 * 垂直百分比、水平居中布局
		 * 
		 * @param containerdiv
		 * @param centerdiv
		 */
		vpart_l_center : function(containerdiv, centerdiv, percent) {

			containerdiv.css({
				position : "relative"
			});
			centerdiv.css({
				position : "absolute"
			});
			centerdiv.css({
				top : "50%",
				left : "50%"
			});

			// var height_ref_button = 0 - centerdiv.height() / 2;
			var height_ref_button = 0 - containerdiv.height() * (50 - percent)
					/ 100;
			var width_ref_button = 0 - centerdiv.width() / 2;
			centerdiv.css({
				"margin-top" : height_ref_button
			});
			centerdiv.css({
				"margin-left" : width_ref_button
			});
		},
		/***********************************************************************
		 * 垂直、水平居中布局
		 * 
		 * @param containerdiv
		 * @param centerdiv
		 */
		v_l_center : function(containerdiv, centerdiv) {

			containerdiv.css({
				position : "relative"
			});
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
		},
		/***********************************************************************
		 * Flexigrid的宽度自动调整
		 * 
		 * @param config
		 */
		setFlexiGridWith : function(p, g) {
			var height = p.height;
			var h_all = p.total * 19;

			var flg = height - h_all;

			var width_all = 0;
			var num = 0;
			var widths = [];
			var numToUse = 0;
			$('thead tr:first th div', g.hDiv).each(function() {
				var width_tmp = parseInt($(this)[0].style.width);
				width_all += (width_tmp + 8);
				if ($.browser.mozilla) {
					width_all += 1;
					numToUse += 1;
				}
				widths.push(width_tmp);
				num++;
			});

			if (p.checkbox || p.radiobox) {
				width_all += 22;
				if ($.browser.mozilla) {
					width_all += 1;
				}
			}

			var width_h = $($('.flexigrid')[0]).width();
			var widthLeft = width_h - width_all - 8;
			if ($.browser.mozilla) {
				widthLeft -= 1;
			}

			if (widthLeft > 0) {
				var widthToSet = widths[num - 1] + widthLeft - numToUse - 25;
				if ($.browser.mozilla) {
					widthToSet += 15;
				}
				// if (flg >= 0) {
				// widthToSet += 15;
				// }
				$('thead tr:first th div', g.hDiv).each(function() {
					if (num == 1) {
						$(this).css({
							width : widthToSet
						});
					}

					num--;
				});
			}
		}
	});

})(jQuery);
;
