// 封装全局函数
;
(function($) {
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
	
	$.extend({
		/***********************************************************************
		 * 取FLEXIGRID的列宽度
		 */
		getFGridColWidth : function(colnam) {
			var rtnW = 15;// 默认返回15
			var width = colnam.getLength();
			if (width <= 8) {
				rtnW = 80;
			}else if (width <= 12) {
				rtnW = 80;
			}else if (width <= 16){
				rtnW = 100;
			}else{
				rtnW = 150;
			}

			return rtnW;
		}
		
	});
})(jQuery);
