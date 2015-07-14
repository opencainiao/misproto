$().ready(function() {

	materialtype_manage.search();
	
	$("#btn_search").click(function() {
		materialtype_manage.search_from_home();
		//materialtype_manage.search();
	});
	
	$("#add_materialtype").click(function() {
		materialtype_manage_functions.toAddMaterialtype();
	});
	
	registerlogin();
});

var materialtype_manage_functions = {
	// 添加材料类型
	toAddMaterialtype:function(){
		var url_to = $.getSitePath() + '/materialtypehome/add?time=' +  new Date().getTime();
		window.location.href = url_to;
	},
	/***************************************************************************
	 * 删除
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var toLog = [];

		toLog.push("delOne--\n" + JSON.stringify(data));

		var url_to = $.getSitePath() + '/materialtypehome/' + data["_id_m"] + '/delete';

		toLog.push("delOne--\n" + JSON.stringify(data));

		console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				materialtype_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(materialtype_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 进入修改页面
	 * 
	 * @param data
	 */
	toEdit : function(data) {

		var toLog = [];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/materialtypehome/' + data["_id_m"] + "/update";

		data["roleid"] = data["_id_m"];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	toDetail : function(data) {

		var toLog = [];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/materialtypehome/' + data["_id_m"] + "?f=1";

		data["roleid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));
		//alert(url);

		window.location.href = url;
	},
	pageLayout : function() {
		var inPage_h = $(window).height();
		var table_h = mou_grid_ux.getTableH($("#materialtype_manage"), inPage_h - 300);
		
		//alert("inpage_h[" + inPage_h + "]\ntableh["+table_h+"]");
		
		//mou_grid_ux.resetHeight_grid($("#materialtype_manage"), table_h);
		
		$(".bDiv").height(table_h);
		//$("#materialtype_manage_div").height(table_h);
	}
};

var materialtype_manage = {
		
	search_from_home : function(){
		var searchcondition = $("#search_condition").val().trim();
		
		var url_to = $.getSitePath() + '/search?time=' +  new Date().getTime() + "&search_condition=" + searchcondition;
		window.location.href = url_to;
	},

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {
		
		var searchcondition = {};
		searchcondition["name"] = "search_condition";
		searchcondition["value"] = $("#search_condition").val().trim();
		
		var params = [];
		params.push(searchcondition);
		
		materialtype_manage.gridsetting.url = $.getSitePath() + '/materialtypehome/list?ts=' + new Date().getTime();
		
		if ($("#materialtype_manage_div").attr("s_times")){
			params.push({
				name : 'reload',
				value : true
			});
			materialtype_manage.gridsetting.params = params;
			
			$("#materialtype_manage").flexReload(materialtype_manage.gridsetting);
		}else{
			materialtype_manage.gridsetting.params = params;
			$("#materialtype_manage").flexigrid(materialtype_manage.gridsetting);
		}

		$("#materialtype_manage_div").attr("s_times",1);
	},
	
	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : true,
		resizable : false,
		grid_id:'list',
		url : '',
		method : 'POST',
		dataType : 'json',
		nowrap : true, // 是否不换行
		autoload : true,// 自动加载
		usepager : true,
		title : '',
		useRp : true,
		rp : 10, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		onSuccess : materialtype_manage_functions.pageLayout,
		colModel : [ {
			display : '选择',
			hide : true,
			m_type : 'radio',
			align : 'center',
			select : [ "_id_m" ]
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center',
			hide:true
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		}, {
			display : '材料分类名称',
			name : 'matname',
			m_type : 'link',
			width : 200,
			select : [ "_id_m" ],
			callback : materialtype_manage_functions.toDetail
		}, {
			display : '代码数量',
			name : 'matnum',
			width : 100
		}, {
			display : '访问次数',
			name : 'accesstimes',
			width : 100
		}, {
			display : '材料分类编码',
			name : 'matcode',
			width : 100
		},  {
			display : '材料分类别名',
			name : 'aliasname',
			width : 100
		},{
			display : '描述',
			name : 'description',
			fill:true
		}, {
			display : '注释',
			name : 'note',
			m_type : 'link',
			width : 200,
			hide:true
			
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 200,
			hide:true,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : materialtype_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : materialtype_manage_functions.toEdit,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-primary"
			} ]
		} ]
	}
};
