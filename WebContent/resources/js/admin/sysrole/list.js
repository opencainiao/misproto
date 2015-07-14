$().ready(function() {

	sysrole_manage.init();
});

var sysrole_manage_functions = {
	/***************************************************************************
	 * 删除角色
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var toLog = [];

		toLog.push("delOne--\n" + JSON.stringify(data));

		var url_to = $.getSitePath() + '/sysroleadmin/' + data["_id_m"] + '/delete';

		data["xtlmid"] = data["_id_m"];

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
				sysrole_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(sysrole_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 进入角色修改页面
	 * 
	 * @param data
	 */
	toEdit : function(data) {

		var toLog = [];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/sysroleadmin/' + data["_id_m"] + "/update";

		data["roleid"] = data["_id_m"];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入角色详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	toDetail : function(data) {

		var toLog = [];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/sysroleadmin/' + data["_id_m"];

		data["roleid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	}
};

var sysrole_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		// alert("11");
		var _range = $(window.document);

		sysrole_manage.gridsetting.url = $.getSitePath() + '/sysroleadmin/searchRoles';
		// alert(sysrole_manage.gridsetting.url);
		$("#list").flexigrid(sysrole_manage.gridsetting);

		sysrole_manage.pageLayout();

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
		title : '系统角色列表',
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
			select : [ "snotyp", "snotypnam" ]
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		},{
			display : '名称',
			name : 'name',
			m_type : 'link',
			width : 400,
			select : [ "_id_m" ],
			callback : sysrole_manage_functions.toDetail
		}, {
			display : '简介',
			name : 'remark',
			width : 300
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 200,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : sysrole_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : sysrole_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
