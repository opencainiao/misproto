$().ready(function() {

	materialtype_manage.search();
});

var materialtype_manage_functions = {
	pageLayout : function() {
		var inPage_h = $(window).height();
		var table_h = mou_grid_ux.getTableH($("#materialtype_div"), inPage_h - 100);
		
		$(".bDiv").height(table_h);
	}
};

var materialtype_manage = {
		
	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {
		
		var searchcondition = {};
		searchcondition["name"] = "specid";
		searchcondition["value"] = $("#specid").val();
		
		var params = [];
		params.push(searchcondition);
		
		materialtype_manage.gridsetting.url = $.getSitePath() + '/materialtypehome/list_related_spec_materialtype?ts=' + new Date().getTime();
		
		materialtype_manage.gridsetting.params = params;
		$("#list").flexigrid(materialtype_manage.gridsetting);
	},
	
	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : true,
		resizable : false,
		grid_id:'list',
		parent_div_id:'materialtype_list_div',
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
			name : 'materialtypename',
			fill:true
		}]
	}
};
