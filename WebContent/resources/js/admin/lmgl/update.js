
$().ready(function() {

	$("#btn_save").bind("click", save);
	
});

//保存
var save = function() {
	// 控制按钮为禁用
	$.disableButton("btn_save");

	var paramForm = $('form').getFormParam_ux();
	
	var url_to = window.location.href ;
	
	//$.enableButtonTime("btn_save",2);
	
	//layer.alert( url_to +"\n" + JSON.stringify(paramForm), 8); //风格一
	
	$.ajax({
        type:'POST',
        url: url_to,
        data : $.extend({
			ts : new Date().getTime()
		}, paramForm),
		type : 'POST',
		dataType : 'json',
        success: function(data){
        	//layer.alert( JSON.stringify(data), 8); 
        	//alert(data.success);
        	if (data['success'] == 'n'){
        		layer.alert(data['message']);
        	}else{
        		parent.loadTree();
        		layer.alert( "保存成功", 1);
        	}
        },
        complete:function(XMLHttpRequest, textStatus){
        	
        	//layer.alert(JSON.stringify(XMLHttpRequest));
        	// 控制按钮为可用
        	$.enableButton("btn_save");
        }
    }); 
};
