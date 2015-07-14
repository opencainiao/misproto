var _toUpdate = {};

$().ready(function() {

	//alert(JSON.stringify(zzdhncpjson));

	$.init_grid_with_array_json(zzdhncpjson, $("#grid_ncp>tbody"),toShow_in_grid,hide_in_grid);

	$("#add_ncp").click(function() {
		pupUpNcp();
	});

	$("#btn_save").bind("click", save);
	
});

//保存
var save = function() {
	
	//	设置农产品
	var datajsons = $("#grid_ncp>tbody").data("data_jsons");
	var formatStr = [];
	for ( var k in datajsons) {
		formatStr.push(datajsons[k]);
	}

	$("#ncp").val(JSON.stringify(formatStr));
	
	// 控制按钮为禁用
	$.disableButton("btn_save");

	var paramForm = $('form').getFormParam_ux();
	
	var url_to = window.location.href ;
	
	//alert("url_to\n" + url_to);
	
	//$.enableButtonTime("btn_save",2);
	
	//$.getSitePath() + "/zzdhadmin/"+ $("#_id").val()+"/update";
	
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
* 弹出农产品录入框
*/
function pupUpNcpUpdate(sno_u) {
	
	_toUpdate = $("#grid_ncp>tbody").data("data_jsons")[sno_u];
	
	$.layer({
		type : 2,
		title : [ '录入农产品信息',
				'background:#2B2E37; height:40px; color:#fff; border:none;' //自定义标题样式
		],
		border : [ 0 ],
		area : [ '70%', '450px' ],
		btns:2,
		btn: ['确定', '取消'],
		iframe : {
			src : $.getSitePath() + "/zzdhadmin/ncp" + "?ts=" + new Date().getTime()
		},
		closeBtn: false,//没有关闭按钮
		yes: function(index){
			//alert("点击了确定");
			//alert(JSON.stringify(index));
			var inputObj = $.get_data_from_popup_mou(index);
			
			var $tbody = $("#grid_ncp>tbody");
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
* 弹出农产品录入框
*/
function pupUpNcp() {
	
	$.layer({
		type : 2,
		title : [ '录入农产品信息',
				'background:#2B2E37; height:40px; color:#fff; border:none;' //自定义标题样式
		],
		border : [ 0 ],
		area : [ '70%', '450px' ],
		btns:2,
		btn: ['确定', '取消'],
		iframe : {
			src : $.getSitePath() + "/zzdhadmin/ncp" + "?ts=" + new Date().getTime()
		},
		closeBtn: false,//没有关闭按钮
		yes: function(index){
			//alert("点击了确定");
			//alert(JSON.stringify(index));
			
			var inputObj = $.get_data_from_popup_mou(index);
			var $tbody = $("#grid_ncp>tbody");
			$.addtr_mou_json_delete(inputObj, $tbody ,pupUpNcpUpdate,toShow_in_grid,hide_in_grid);
			
			//alert(JSON.stringify($("#grid_ncp>tbody").data("data_jsons")) + "\n\n" + $("#grid_ncp>tbody").data("sno_max"));
			layer.close(index);
			
			return;
		},
		no: function(index){
		}
	})
}
