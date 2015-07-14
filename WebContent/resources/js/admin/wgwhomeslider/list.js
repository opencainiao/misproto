$().ready(function() {
	wgwhomeslider_manage.init();
});

var wgwhomeslider_manage_functions = {
	
	/****
	 * 初始化事件设置
	 */
	initSortEvent:function(){
		$("tr",$("#list")).on("click",".cancelPos",function(e){
			e.preventDefault();
			$(this).parent("span").prev(".btn").attr("sortflg","0");
			$(this).parent("span").remove();
		});
		
		$("tr",$("#list")).on("click",".confirmPos",function(e){
			e.preventDefault();
			var id = $(this).attr("data-id");
			var orip = $(this).attr("data-order");
			var newp = $('input',$(this).prev('.spinner')).val();
			
			var toShow = [];
			toShow.push("id["+id+"]");
			toShow.push("orip["+orip+"]");
			toShow.push("newp["+newp+"]");
			
			if(orip!=newp) {
				//通过dwr更新节点
				//dwrService.updatePicPos(id,op,np,function(){
				//	window.location.reload();
				//});
				var url_to =  $.getSitePath() + '/wgwhomeslideradmin/' + id	+ '/setOrder';
				$.ajax({
					type : 'POST',
					url : url_to,
					data : {
						ts : new Date().getTime(),
						id : id,
						orip : orip,
						newp : newp
					}, 
					dataType : 'json',
					success : function(data) {
						window.location.reload();
					}
				});
				
				//toShow.push("orp_newp[not_same]");
			}
			
			//alert(toShow.join("\n"));
			
			$(this).parent("span").prev(".btn").attr("sortflg","0");
			$(this).parent("span").remove();
		});
	},
	doUseOp:function(data){
		var url_to = $.getSitePath() + '/wgwhomeslideradmin/' + data["_id_m"]
			+ '/updatestatus';
		
		$.ajax({
			type : 'POST',
			url : url_to,
			data : $.extend({
				ts : new Date().getTime()
			}, data),
			dataType : 'json',
			success : function(data) {
				wgwhomeslider_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];
				$("#list").flexReload(wgwhomeslider_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 删除
	 * 
	 * @param data
	 */
	delOne : function(data) {

		var toLog = [];

		toLog.push("delOne--\n" + JSON.stringify(data));

		var url_to = $.getSitePath() + '/wgwhomeslideradmin/' + data["_id_m"]
				+ '/delete';

		data["_id"] = data["_id_m"];

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
				if (data['success'] == 'n') {
					layer.alert(data['message']);
					return;
				}

				wgwhomeslider_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];

				$("#list").flexReload(wgwhomeslider_manage.gridsetting);
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

		var url = $.getSitePath() + '/wgwhomeslideradmin/' + data["_id_m"]
				+ "/update";

		data["_id"] = data["_id_m"];

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

		var url = $.getSitePath() + '/wgwhomeslideradmin/' + data["_id_m"];

		toLog.push("toDetail--\n" + JSON.stringify(data));

		// console.log(toLog.join("\n"));
		// alert(toLog.join("\n"));

		window.location.href = url;
	},
	/***************************************************************************
	 * 编辑排序
	 * 
	 * @param data
	 */
	sort : function(data) {
		
		var order = data["ordernum"];
		var id =  data["_id_m"];
		
		var cellObj = $(arguments[1]);
		
		if ($(cellObj).attr("sortflg") && $(cellObj).attr("sortflg")=="1"){
			return;
		}
		
		$(cellObj).after("<span>&nbsp;<input type='text' value='"+order+"' size='3' class='toSpin' />&nbsp;<a href='#' data-id='"+ id +"'data-order='"+ order +"' class='btn btn-sm btn-success confirmPos'>确定</a>&nbsp;<a href='' class='btn btn-sm btn-danger cancelPos'>取消</a></span>");
		$(cellObj).attr("sortflg","1");
		
		
		$('.toSpin',$(cellObj).nextAll()).spinner({value:order});
		
		return;
	}
};

var wgwhomeslider_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	init : function() {
		// alert("11");
		var _range = $(window.document);

		wgwhomeslider_manage.gridsetting.url = $.getSitePath()
				+ '/wgwhomeslideradmin/list';
		//alert(wgwhomeslider_manage.gridsetting.url);
		$("#list").flexigrid(wgwhomeslider_manage.gridsetting);

		wgwhomeslider_manage.pageLayout();

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
		usepager : false,
		title : '微官网首页轮播图片列表',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		onSuccess:function(){
			wgwhomeslider_manage_functions.initSortEvent();
		},
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
			hide : true

		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center'
		}, {
			display : '标题',
			name : 'title',
			m_type : 'link',
			select : [ "_id_m" ],
			callback : wgwhomeslider_manage_functions.toDetail
		}, {
			display : '图片',
			m_type : 'image',
			name : 'img.compressedPath',
			width : 100
		}, {
			display : '排序序号',
			name : 'ordernum'
		}, {
			display : '简介',
			name : 'remark'
		}, {
			display : '状态',
			width: 50,
			name : 'status',
			condition : {
				'0' : '停用',
				'1' : '启用'
			}
		}, {
			display : '启用/停用',
			name : 'useflg',
			width : 60,
			m_type : 'buttons',
			align : 'center',
			buttons : [ {
				r_name : 'useflg',
				r_reltive : "status",
				text : {
					'0' : '启用',
					'1' : '停用'
				},
				css : {
					'0' : "btn btn-sm btn-primary",
					"1" : "btn btn-sm btn-danger"
				},
				callback : wgwhomeslider_manage_functions.doUseOp,
				paramConfig : [ "_id_m", "status" ]
			} ]
		},{
			display : '排序',
			name : 'sort',
			m_type : 'buttons',
			align : 'left',
			width : 180,
			buttons : [ {
				r_name : 'sort',
				text : '排序',
				callback : wgwhomeslider_manage_functions.sort,
				paramConfig : [ "_id_m", , "ordernum" ]
			} ]
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 150,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : wgwhomeslider_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : wgwhomeslider_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
