var _toUpdate = {};

$().ready(function() {

	//alert(JSON.stringify(zmjdncpjson));

	$.init_grid_with_array_json(zmjdzmjson, $("#grid_zm>tbody"),toShow_in_grid,hide_in_grid);

	$("#add_zm").click(function() {
		pupUpNcp();
	});

	$("#btn_save").bind("click", save);
	
});

//保存
var save = function() {
	
	//	设置种苗
	var datajsons = $("#grid_zm>tbody").data("data_jsons");
	var formatStr = [];
	for ( var k in datajsons) {
		formatStr.push(datajsons[k]);
	}

	$("#zm").val(JSON.stringify(formatStr));
	
	// 控制按钮为禁用
	$.disableButton("btn_save");

	var paramForm = $('form').getFormParam_ux();
	
	var url_to = window.location.href ;
	
	//alert("url_to\n" + url_to);
	
	//$.enableButtonTime("btn_save",2);
	
	//$.getSitePath() + "/zmjdadmin/"+ $("#_id").val()+"/update";
	
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
        		if (data['brErrors']){
        			$.showBRErrors_mou(data['brErrors'],$("#zmjdupdatediv"));
        			
        			if (data['brErrors']['content']){
        				alert(data['brErrors']['content']);
        			}
        		}else {
        			layer.alert(data['message']);
        		}
        	}else{
        		var _id= data['message'];
        		$("input[name=_id]").val(_id);
        		
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

/****
* 弹出种苗录入框
*/
function pupUpNcpUpdate(sno_u) {
	
	_toUpdate = $("#grid_zm>tbody").data("data_jsons")[sno_u];
	
	$.layer({
		type : 2,
		title : [ '录入种苗信息',
				'background:#2B2E37; height:40px; color:#fff; border:none;' //自定义标题样式
		],
		border : [ 0 ],
		area : [ '70%', '450px' ],
		btns:2,
		btn: ['确定', '取消'],
		iframe : {
			src : $.getSitePath() + "/zmjdadmin/zm" + "?ts=" + new Date().getTime()
		},
		closeBtn: false,//没有关闭按钮
		yes: function(index){
			//alert("点击了确定");
			//alert(JSON.stringify(index));
			var inputObj = $.get_data_from_popup_mou(index);
			
			var $tbody = $("#grid_zm>tbody");
			$.addtr_mou_json_update(inputObj, $tbody ,pupUpNcpUpdate,toShow_in_grid,hide_in_grid);
			
			layer.close(index);
			
			_toUpdate = {};
		},
		no: function(index){
			_toUpdate = {};
		}
	})
}

/****
* 弹出种苗录入框
*/
function pupUpNcp() {
	
	$.layer({
		type : 2,
		title : [ '录入种苗信息',
				'background:#2B2E37; height:40px; color:#fff; border:none;' //自定义标题样式
		],
		border : [ 0 ],
		area : [ '70%', '450px' ],
		btns:2,
		btn: ['确定', '取消'],
		iframe : {
			src : $.getSitePath() + '/zmjdadmin/zm' + "?ts=" + new Date().getTime()
		},
		closeBtn: false,//没有关闭按钮
		yes: function(index){
			//alert("点击了确定");
			//alert(JSON.stringify(index));
			
			var inputObj = $.get_data_from_popup_mou(index);
			var $tbody = $("#grid_zm>tbody");
			$.addtr_mou_json_delete(inputObj, $tbody ,pupUpNcpUpdate,toShow_in_grid,hide_in_grid);
			
			//alert(JSON.stringify($("#grid_ncp>tbody").data("data_jsons")) + "\n\n" + $("#grid_ncp>tbody").data("sno_max"));
			layer.close(index);
			
			return;
		},
		no: function(index){
		}
	})
}
