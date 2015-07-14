$().ready(function() {

	zzdh_manage.init();
});

var zzdh_manage_functions = {
	/***************************************************************************
	 * 删除一条记录
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var toLog = [];

		toLog.push("delOne--\n" + JSON.stringify(data));

		var url_to = $.getSitePath() + '/zzdhadmin/' + data["_id_m"]
				+ '/delete';

		data["zzdhid"] = data["_id_m"];

		toLog.push("delOne--\n" + JSON.stringify(data));

		console.log(toLog.join("\n"));
	//	alert(toLog.join("\n"));

		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				zzdh_manage.gridsetting.params = [{name:'reload',value:true}];
				$("#zzdhs").flexReload(zzdh_manage.gridsetting);
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

		var url = $.getSitePath() + '/zzdhadmin/' + data["_id_m"] + "/update";

		data["zzdhid"] = data["_id_m"];

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

		var url = $.getSitePath() + '/zzdhadmin/' + data["_id_m"];

		data["zzdhid"] = data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	}
};

var zzdh_manage = {
		
	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		var _range = $(window.document);

		zzdh_manage.gridsetting.url = $.getSitePath()
				+ '/zzdhadmin/searchZzdhs';
		$("#zzdhs").flexigrid(zzdh_manage.gridsetting);
		
		zzdh_manage.pageLayout();
	},

	pageLayout : function() {

		var inPage_h = $(parent.window).height();
		// alert(inPage_h);
		var nav_h = 160;
		var btnbar_h = 40;
		var table_h = mou_grid_ux.getTableH($("#content_inner_page"), inPage_h
				- nav_h - btnbar_h - 2 - 8);
		
		var showArr = [];
		showArr.push(inPage_h-nav_h-btnbar_h - 2 - 8);
		showArr.push("table_h["+table_h+"]");
		
		//alert(showArr.join("\n"));
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
		title : '公共序号列表',
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
			display : '选择',
			m_type : 'checkbox',
			align : 'center',
			hide : true
		// select:["snotyp","snotypnam","inisno"]
		}, {
			display : '逻辑主键',
			name : '_id_m',
			width : 150,
			hide : true
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '名称',
			name : 'dhname',
			m_type : 'link',
			select : [ "_id_m" ],
			width:190,
			callback : zzdh_manage_functions.toDetail
		}, {
			display : '联系人',
			name : 'dhlinkname'
		}, {
			display : '联系电话',
			name : 'dhlinkphone'
		}, {
			display : '地址',
			name : 'address'
		}, {
			display : '标题',
			name : 'zzdhjj.title'
		}, {
			display : '简介',
			name : 'zzdhjj.summary'
		}, {
			display : '图片',
			name : 'zzdhjj.imgurl',
			m_type : 'image',
			width : 100
		}, {
			display : '种植大户ID',
			name : '_id_m',
			hide:true
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 150,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : zzdh_manage_functions.delOne,
				paramConfig : [ "_id_m" ]
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : zzdh_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
