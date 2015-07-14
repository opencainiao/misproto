
$().ready(function() {

	$("#btn_save").bind("click", save);
	
});

//保存
var save = function() {
	
	// 控制按钮为禁用
	$.disableButton("btn_save");

	var paramForm = $('form').getFormParam_ux();
	
	var url_to = window.location.href ;
	//var url_to =  "<%=request.getContextPath()%>/sysroleadmin/add";
	
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
        		
        		if (data['brErrors']){
        			$.showBRErrors(data['brErrors'],$("#sysrole_add_div"));
        		}else {
        			layer.alert(data['message']);
        		}
        	}else{
        		layer.alert( "修改成功", 1);
        	}
        },
        complete:function(XMLHttpRequest, textStatus){
        	
        	//layer.alert(JSON.stringify(XMLHttpRequest));
        	// 控制按钮为可用
        	$.enableButton("btn_save");
        }
    }); 
};
