$().ready(function() {

	material_manage.search();

	$("#btn_search").click(function() {
		material_manage.search_from_home();
	});

	registerlogin();
});

var material_manage_functions = {
	/***************************************************************************
	 * 进入详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	showDetail : function(data) {

		var toLog = [];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/materialhome/' + data["_id_m"];

		// window.location.href = url;
		$.layer({
			type : 2,
			shadeClose : true,
			title : '材料信息',
			closeBtn : [ 0, false ],
			shade : [ 0.8, '#000' ],
			border : [ 0 ],
			area : [ '60%', "60%" ],
			iframe : {
				src : url
			}
		});
	},
	pageLayout : function() {
		var inPage_h = $(window).height();
		var table_h = mou_grid_ux.getTableH($("#material_grid_div"), inPage_h - 250);
		
		$(".bDiv").height(table_h);
	}
};

var material_manage = {

	search_from_home : function() {
		var searchcondition = $("#search_condition").val().trim();

		var url_to = $.getSitePath() + '/search?time=' + new Date().getTime() + "&search_condition=" + searchcondition;
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
		
		material_manage.gridsetting.url = $.getSitePath() + '/materialhome/list_code?ts=' + new Date().getTime();
		
		if ($("#material_grid_div").attr("s_times")){
			params.push({
				name : 'reload',
				value : true
			});
			material_manage.gridsetting.params = params;
			$("#maertial_list").flexReload(material_manage.gridsetting);
		}else{
			material_manage.gridsetting.params = params;
			$("#maertial_list").flexigrid(material_manage.gridsetting);
		}
	},
	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : false,
		showToggleBtn : false,
		grid_id : 'maertial_list',
		parent_div_id : 'material_grid_div',
		url : '',
		method : 'POST',
		dataType : 'json',
		nowrap : true, // 是否不换行
		autoload : true,// 自动加载
		usepager : true,
		title : '',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的材料',
		onSuccess : material_manage_functions.pageLayout,
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
			hide : true
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		}, {
			display : '材料代码UMC',
			name : 'code',
			width : 150,
			m_type : 'link',
			select : [ "_id_m" ],
			callback : material_manage_functions.showDetail
		}, {
			display : '单位',
			name : 'unit',
			width : 100
		}, {
			display : '短描述',
			name : 'shortdes',
			fill : true
		}, {
			display : '长描述',
			name : 'longdes',
			hide : true
		}, {
			display : '注释',
			name : 'note',
			m_type : 'link',
			width : 200,
			hide : true
		} ]
	}
};
