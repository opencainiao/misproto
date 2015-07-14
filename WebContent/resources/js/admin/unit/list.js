$().ready(function() {

	unit_manage.init();

	$("#btn_add").click(function() {
		var url_to = $.getSitePath() + '/unitadmin/add';
		
		window.location.href = url_to;
	});

	$("#btn_search").click(function() {

		var searchcondition = {};
		searchcondition["name"] = "search_condition";
		searchcondition["value"] = $("#search_condition").val().trim();
		
		var params = [];
		params.push(searchcondition);
		
		unit_manage.gridsetting.url = $.getSitePath() + '/unitadmin/list?ts=' + new Date().getTime();
		
		if ($("#unit_manage").attr("s_times")){
			params.push({
				name : 'reload',
				value : true
			});
			unit_manage.gridsetting.params = params;
			
			$("#list").flexReload(unit_manage.gridsetting);
		}else{
			unit_manage.gridsetting.params = params;
			$("#list").flexigrid(unit_manage.gridsetting);
		}

		$("#unit_manage").attr("s_times",1);
	});
});

var unit_manage_functions = {
	/***************************************************************************
	 * 删除角色
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var url_to = $.getSitePath() + '/unitadmin/' + data["_id_m"] + '/delete';

		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				unit_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(unit_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 进入角色修改页面
	 * 
	 * @param data
	 */
	toEdit : function(data) {

		var url = $.getSitePath() + '/unitadmin/' + data["_id_m"] + "/update";

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入角色详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	toDetail : function(data) {

		var url = $.getSitePath() + '/unitadmin/' + data["_id_m"];

		window.location.href = url;
	}
};

var unit_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		// alert("11");
		var _range = $(window.document);

		unit_manage.gridsetting.url = $.getSitePath() + '/unitadmin/list';
		// alert(unit_manage.gridsetting.url);
		$("#list").flexigrid(unit_manage.gridsetting);

		unit_manage.pageLayout();

	},
	pageLayout : function() {

		var inPage_h = $(parent.window).height();
		// alert(inPage_h);
		var nav_h = 160;
		var btnbar_h = 40;
		var table_h = mou_grid_ux.getTableH($("#content_inner_page"), inPage_h - nav_h - btnbar_h - 2 - 8);
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
		title : '材料单位列表',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		colModel : [ {
			display : '选择',
			hide : true,
			m_type : 'radio1',
			align : 'center',
			select : [ "_id_m", "name" ]
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		}, {
			display : '名称',
			name : 'name',
			m_type : 'link',
			width : 200,
			select : [ "_id_m" ],
			callback : unit_manage_functions.toDetail
		}, {
			display : '状态',
			name : 'status',
			width : 120,
			condition : {
				'0' : '已删除',
				'1' : '启用',
				'2' : '已停用'
			}
		}, {
			display : '简介',
			name : 'remark',
			width : 300,
			hide : true
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 200,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : unit_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : unit_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
