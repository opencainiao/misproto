jQuery.fn.size = function() {
	return jQuery(this).get(0).options.length;
};
// 获得选中项的索引
jQuery.fn.getSelectedIndex = function() {
	return jQuery(this).get(0).selectedIndex;
};
// 获得当前选中项的文本
jQuery.fn.getSelectedText = function() {
	if (this.size() == 0) {
		return "下拉框中无选项";
	} else {
		var index = this.getSelectedIndex();
		return jQuery(this).get(0).options[index].text;
	}
};
// 获得当前选中项的值
jQuery.fn.getSelectedValue = function() {
	if (this.size() == 0) {
		return "下拉框中无选中值";
	} else {
		return jQuery(this).val();
	}
};
// 设置select中值为value的项为选中
jQuery.fn.setSelectedValue = function(value) {
	jQuery(this).get(0).value = value;
};
// 设置select中文本为text的项被选中
jQuery.fn.setSelectedText = function(text) {
	var isExist = false;
	var count = this.size();
	for ( var i = 0; i < count; i++) {
		if (jQuery(this).get(0).options[i].text == text) {
			jQuery(this).get(0).options[i].selected = true;
			isExist = true;
			// break; (只设置第一个被选中)
		}
	}
	if (!isExist) {
		alert("下拉框中不存在该项");
	}
};
// 设置选中指定索引项
jQuery.fn.setSelectedIndex = function(index) {
	var count = this.size();
	if (index >= count || index < 0) {
		alert("选中项索引超出范围");
	} else {
		jQuery(this).get(0).selectedIndex = index;
	}
};
// 判断select项中是否存在值为value的项
jQuery.fn.isExistItem = function(value) {
	var isExist = false;
	var count = this.size();
	for ( var i = 0; i < count; i++) {
		if (jQuery(this).get(0).options[i].value == value) {
			isExist = true;
			break;
		}
	}
	return isExist;
};
// 向select中添加一项，显示内容为text，值为value,如果该项值已存在，则提示
jQuery.fn.addOptionLast = function(text, value) {
	if (this.isExistItem(value)) {
		// alert("待添加项的值已存在");
		return;
	} else {
		jQuery(this).get(0).options.add(new Option(text, value));
	}
};
// 为Select插入一个Option(第一个位置)，显示内容为text，值为value
jQuery.fn.addOptionFirst = function(text, value) {
	if (this.isExistItem(value)) {
		// alert("待添加项的值已存在");
		return;
	} else {
		jQuery(this).prepend(new Option(text, value));
	}
};
// 删除select中值为value的项
jQuery.fn.removeItem = function(value) {

	var count = this.size();
	for ( var i = 0; i < count; i++) {
		if (jQuery(this).get(0).options[i].value == value) {
			jQuery(this).get(0).remove(i);
			break;
		}
	}
};
// 删除select中指定索引的项
jQuery.fn.removeIndex = function(index) {
	var count = this.size();
	if (index >= count || index < 0) {
		alert("待删除项索引超出范围");
	} else {
		jQuery(this).get(0).remove(index);
	}
};
// 删除select中选定的项
jQuery.fn.removeSelected = function() {
	var index = this.getSelectedIndex();
	this.removeIndex(index);
};
// 清除select中的所有项
jQuery.fn.clearAll = function() {
	jQuery(this).get(0).options.length = 0;
};

// 用给定的JSON数据初始化下拉列表
jQuery.fn.iniSelect_noAll = function(data, setting) {

	var text = "text";
	var value = "value";

	this.clearAll();
	if (setting != null && setting != undefined) {
		text = setting[text];
		value = setting[value];
	}
	var dropdownList = this;

	$.each(data, function() {
		dropdownList.addOptionLast(this[text], this[value]);
	});
};
