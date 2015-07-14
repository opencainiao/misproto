$().ready(function() {
	
	specorder_manage.search();
	
	$("#btn_search").click(function() {
		specorder_manage.search();
	});
});

var specorder_manage_functions = {
	
	/***************************************************************************
	 * 进入修改页面
	 * 
	 * @param data
	 */
	toEditSpecs : function(data) {

		var url = $.getSitePath() + '/materialtypeadmin/' + data["_id_m"]
				+ "/to_manage_materialtype_spec_order";

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

		var url = $.getSitePath() + '/materialtypehome/' + data["_id_m"] + "?f=3";

		data["roleid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		window.location.href = url;
	}
};

var specorder_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {
		var searchcondition = {};
		searchcondition["name"] = "search_condition";
		searchcondition["value"] = $("#search_condition").val().trim();
		
		var params = [];
		params.push(searchcondition);
		
		specorder_manage.gridsetting.url = $.getSitePath() + '/materialtypehome/list?ts=' + new Date().getTime();
		
		if ($("#specorder_manage").attr("s_times")){
			params.push({
				name : 'reload',
				value : true
			});
			specorder_manage.gridsetting.params = params;
			
			$("#list").flexReload(specorder_manage.gridsetting);
		}else{
			specorder_manage.gridsetting.params = params;
			$("#list").flexigrid(specorder_manage.gridsetting);
		}

		$("#specorder_manage").attr("s_times",1);
		
		specorder_manage.pageLayout();

	},
	pageLayout : function() {

		var inPage_h = $(parent.window).height();
		// alert(inPage_h);
		var nav_h = 160;
		var btnbar_h = 40;
		var table_h = mou_grid_ux.getTableH($("#content_inner_page"), inPage_h
				- nav_h - btnbar_h - 2 - 8);
		mou_grid_ux.resetHeight_grid($("#content_inner_page"), table_h);
	},
	
	
	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : true,
		resizable : false,
		url : '',
		method : 'POST',
		dataType : 'json',
		nowrap : true, // 是否不换行
		autoload : true,// 自动加载
		usepager : true,
		title : '材料类型信息列表',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		onSuccess:function(){
		},
		colModel : [{
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
			width : 200
//			m_type : 'link',
//			select : [ "_id_m" ],
//			callback : specorder_manage_functions.toDetail
		}, {
			display : '代码数量',
			name : 'matnum',
			width : 100,
			hide:true
		}, {
			display : '访问次数',
			name : 'accesstimes',
			width : 100,
			hide:true
		}, {
			display : '材料分类编码',
			name : 'matcode',
			width : 100
		},  {
			display : '材料分类别名',
			name : 'aliasname',
			width : 200
		},{
			display : '描述',
			name : 'description',
			width:250
		},  {
			fill:true,
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 150,
			buttons : [  {
				r_name : 'toEdit',
				text : '修改包含的规格顺序',
				callback : specorder_manage_functions.toEditSpecs,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
