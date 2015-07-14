$().ready(
		function() {

			article_manage.init();

			// 绑定查询
			$("#search", $("#article_manage")).bind("click",
					article_manage_functions.searchArticle);

			$("#searchPublished", $("#article_manage")).bind("click",
					article_manage_functions.searchPublished);
			$("#searchUnpublicshed", $("#article_manage")).bind("click",
					article_manage_functions.searchUnpublicshed);

		});

var article_manage_functions = {

	/***************************************************************************
	 * 查询已发布文章
	 */
	searchPublished : function() {
		var paramArray = [];
		paramArray.push({
			name : 'status',
			value : "1"
		});
		paramArray.push({
			name : 'reload',
			value : true
		});

		article_manage.gridsetting.params = paramArray;
		$("#list").flexReload(article_manage.gridsetting);
	},
	/***************************************************************************
	 * 查询
	 */
	searchUnpublicshed : function() {

		var paramArray = [];
		paramArray.push({
			name : 'status',
			value : "0"
		});
		paramArray.push({
			name : 'reload',
			value : true
		});

		article_manage.gridsetting.params = paramArray;
		$("#list").flexReload(article_manage.gridsetting);
	},
	/***************************************************************************
	 * 查询
	 */
	searchArticle : function() {

		var paramForm = $('form').getFormParam_ux();

		var paramArray = mou_grid_ux.convert2ParamArray(paramForm);
		paramArray.push({
			name : 'reload',
			value : true
		});

		article_manage.gridsetting.params = paramArray;
		// alert(JSON.stringify(article_manage.gridsetting.params));
		$("#list").flexReload(article_manage.gridsetting);
	},
	/***************************************************************************
	 * 删除一条记录
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var url_to = $.getSitePath() + '/articleadmin/' + data["_id_m"]
				+ '/delete';

		data["articleid"] = data["_id_m"];

		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				article_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(article_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 进入修改页面
	 * 
	 * @param data
	 */
	toEdit : function(data) {

		var url = $.getSitePath() + '/articleadmin/' + data["_id_m"]
				+ "/update";

		window.location.href = url;
	},
	/***************************************************************************
	 * 进入详细信息页面
	 * 
	 * @param data
	 * @returns {Boolean}
	 */
	toDetail : function(data) {

		var url = $.getSitePath() + '/articleadmin/' + data["_id_m"];
		window.location.href = url;
	},
	doPublishOp : function(data) {

		var url_to = $.getSitePath() + '/articleadmin/' + data["_id_m"]
				+ '/dopublishop';

		data["articleid"] = data["_id_m"];

		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				article_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(article_manage.gridsetting);
			}
		});
	},
};

var article_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		var _range = $(window.document);

		article_manage.gridsetting.url = $.getSitePath()
				+ '/articleadmin/searcharticles';
		$("#list").flexigrid(article_manage.gridsetting);

		article_manage.pageLayout();
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
		title : '文章列表',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		colModel : [ {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		}, {
			display : '文章标题',
			name : 'title',
			width : 250,
			m_type : 'link',
			select : [ "_id_m" ],
			callback : article_manage_functions.toDetail
		}, {
			display : '文章作者',
			name : 'author'
		}, {
			display : '所属频道',
			name : 'lmname',
			width : 120
		}, {
			display : '文章的状态',
			name : 'status',
			width : 120,
			condition : {
				'0' : '未发布',
				'1' : '已发布'
			}
		}, {
			display : '发布/取消发布',
			name : 'dopublicsop',
			m_type : 'buttons',
			width : 110,
			align : 'center',
			buttons : [ {
				r_name : 'dopublicsop',
				r_reltive : "status",
				text : {
					'0' : '发布',
					'1' : '取消发布'
				},
				css : {
					'0' : "btn btn-sm btn-primary",
					"1" : "btn btn-sm btn-danger"
				},
				callback : article_manage_functions.doPublishOp,
				paramConfig : [ "_id_m", "status" ]
			} ]
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			align : 'center',
			width : 150,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				css : "btn btn-xs btn-danger",
				callback : article_manage_functions.delOne,
				paramConfig : [ "_id_m" ]
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : article_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
