$().ready(function() {

	lmgl_manage.init();
});

var lmgl_manage_functions = {
	/***************************************************************************
	 * 删除栏目
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var toLog = [];

		toLog.push("delOne--\n" + JSON.stringify(data));

		var url_to = $.getSitePath() + '/lmgladmin/' + data["_id_m"] + '/delete';

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
				if (data['success'] == 'n'){
		    		layer.alert(data['message']);
		    		return;
		    	}
				
				lmgl_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				
				$("#xtlms").flexReload(lmgl_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 进入栏目修改页面
	 * 
	 * @param data
	 */
	toEdit : function(data) {

		var toLog = [];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/lmgladmin/' + data["_id_m"] + "/update";

		data["xtlmid"] = data["_id_m"];

		toLog.push("toEdit--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入栏目详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	toDetail : function(data) {

		var toLog = [];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/lmgladmin/' + data["_id_m"];

		data["xtlmid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入添加子栏目页面
	 * 
	 * @param data
	 */
	toAddSubLm : function(data) {

		var toLog = [];
		toLog.push("toAddSubLm--\n" + JSON.stringify(data));
		data["parent_id"] = data["_id_m"];
		data["parent_name"] = data["name"];
		data["parent_level"] = data["level"];
		toLog.push("toAddSubLm--\n" + JSON.stringify(data));

		var paramArr = [];
		paramArr.push("time=" + new Date().getTime());
		paramArr.push("parent_id=" + data["_id_m"]);
		paramArr.push("parent_name=" + data["name"]);
		paramArr.push("parent_level=" + data["level"]);

		var url_to = $.getSitePath() + '/lmgladmin/add?' + paramArr.join("&");

		window.location.href = url_to;

		return;
	},
	/***************************************************************************
	 * 进入查看子栏目页面
	 * 
	 * @param data
	 */
	show_sublms : function(data) {

		var toLog = [];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		var url = $.getSitePath() + '/lmgladmin/' + data["_id_m"];

		data["xtlmid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		window.location.href = url;

		return;
	},
	/***************************************************************************
	 * 进入管理子栏目页面
	 * 
	 * @param data
	 */
	manage_sublms : function(data) {

		var toLog = [];
		toLog.push("toAddSubLm--\n" + JSON.stringify(data));
		data["parent_id"] = data["_id_m"];
		data["parent_name"] = data["name"];
		data["parent_level"] = data["level"];
		toLog.push("toAddSubLm--\n" + JSON.stringify(data));

		var paramArr = [];
		paramArr.push("lmxxid=" + data["_id_m"]);
		paramArr.push("time=" + new Date().getTime());
		
		var url_to = $.getSitePath() + '/lmgladmin/tomagagesublms?' + paramArr.join("&");

		window.location.href = url_to;

		return;
	}
};

var lmgl_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		// alert("11");
		var _range = $(window.document);

		lmgl_manage.gridsetting.url = $.getSitePath() + '/lmgladmin/searchLmxxs';
		// alert(lmgl_manage.gridsetting.url);
		$("#xtlms").flexigrid(lmgl_manage.gridsetting);

		lmgl_manage.pageLayout();

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
		title : '系统栏目列表',
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
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide:true
			
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '名称',
			name : 'name',
			m_type : 'link',
			select : [ "_id_m" ],
			callback : lmgl_manage_functions.toDetail
		}, {
			display : '图片',
			m_type : 'image',
			name : 'lmimg.compressedPath',
			width : 100
		}, {
			display : '栏目级别',
			align : 'center',
			name : 'level'
		// select:["snotyp","snotypnam","inisno"]
		}, {
			display : '排序序号',
			name : 'ordernum'
		}, {
			display : '父栏目',
			name : 'parent_id'
		}, {
			display : '父栏目名称',
			name : 'parent_name'
		}, {
			display : '简介',
			name : 'remark'
		}, {
			display : '子栏目',
			name : 'addsublm',
			m_type : 'buttons',
			align : 'center',
			width : 180,
			buttons : [ {
				r_name : 'addsublm',
				text : '添加',
				callback : lmgl_manage_functions.toAddSubLm,
				paramConfig : [ "_id_m", "name", "level" ]
			}, {
				r_name : 'showsublms',
				text : '查看',
				callback : lmgl_manage_functions.show_sublms,
				paramConfig : [ "_id_m", "name", "level" ]
			},{
				r_name : 'managesublms',
				text : '管理',
				callback : lmgl_manage_functions.manage_sublms,
				paramConfig : [ "_id_m", "name", "level" ]
			} ]
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 150,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : lmgl_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : lmgl_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
