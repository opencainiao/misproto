$().ready(function() {
	
	specorder_manage.search();
});

var specorder_manage_functions = {
	
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
			var materialtypeid = $(this).attr("data-materialtypeid");
			var specid = $(this).attr("data-specid");
			
			var newp = $('input',$(this).prev('.spinner')).val();
			
			var toShow = [];
			toShow.push("id["+id+"]");
			toShow.push("orip["+orip+"]");
			toShow.push("newp["+newp+"]");
			
			var specnum = $("#maxordernum").val();
			
			if (newp > specnum){
				layer.alert("新序号不能大于" + specnum);
				return;
			}
			
			if(orip!=newp) {
				//通过dwr更新节点
				//dwrService.updatePicPos(id,op,np,function(){
				//	window.location.reload();
				//});
				var url_to =  $.getSitePath() + '/materialtypeadmin/' + id	+ '/set_spec_order';
				$.ajax({
					type : 'POST',
					url : url_to,
					data : {
						ts : new Date().getTime(),
						id : id,
						materialtypeid : materialtypeid,
						specid:specid,
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

				specorder_manage.gridsetting.params = [ {
					name : 'reload',
					value : true
				} ];

				$("#list").flexReload(specorder_manage.gridsetting);
			}
		});
	},
	/***************************************************************************
	 * 编辑排序
	 * 
	 * @param data
	 */
	sort : function(data) {
		
		var order = data["ordernum"];
		var id =  data["_id_m"];
		var specid = data["specid"];
		var materialtypeid = $("#materialtypeid").val();
		
		var cellObj = $(arguments[1]);
		
		if ($(cellObj).attr("sortflg") && $(cellObj).attr("sortflg")=="1"){
			return;
		}
		
		$(cellObj).after("<span>&nbsp;<input type='text' value='"+order+"' size='3' class='toSpin' />&nbsp;<a href='#'" + " data-id='"+ id + "' data-specid='"+ specid +"' data-materialtypeid='"+ materialtypeid +"'data-order='"+ order +"' class='btn btn-sm btn-success confirmPos'>确定</a>&nbsp;<a href='' class='btn btn-sm btn-danger cancelPos'>取消</a></span>");
		$(cellObj).attr("sortflg","1");
		
		
		$('.toSpin',$(cellObj).nextAll()).spinner({value:order});
		
		return;
	}
};

var specorder_manage = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {
		
		specorder_manage.gridsetting.url = $.getSitePath() + '/materialtypeadmin/'+ $("#materialtypeid").val() +'/search_all_specs?ts=' + new Date().getTime();
		
		if ($("#specorder_manage").attr("s_times")){
			
			$("#list").flexReload(specorder_manage.gridsetting);
		}else{
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
		usepager : false,
		title : '规格列表',
		useRp : true,
		rp : 20, // 默认分页条数
		pagestat : '共有{total}条记录，显示{from} - {to}条记录',
		procmsg : '正在查询，请稍候 ...',
		nomsg : '没有符合条件的数据',
		onSuccess:function(){
			specorder_manage_functions.initSortEvent();
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
		}, 
		{
			display : '材料类型id',
			name : 'materialtypeid',
			width : 200,
			hide:true
			//select : [ "_id_m" ],
			//callback : specorder_manage_functions.toDetail
		},
		{
			display : '规格id',
			name : 'specid',
			width : 200,
			hide : true
			//select : [ "_id_m" ],
			//callback : specorder_manage_functions.toDetail
		},
		{
			display : '规格名称',
			name : 'specname',
			width : 200
			//m_type : 'link',
			//select : [ "_id_m" ],
			//callback : specorder_manage_functions.toDetail
		}, {
			display : '顺序号',
			name : 'ordernum',
			width : 100
		}, {
			display : '排序',
			name : 'sort',
			m_type : 'buttons',
			align : 'left',
			width : 400,
			buttons : [ {
				r_name : 'sort',
				text : '排序',
				callback : specorder_manage_functions.sort,
				paramConfig : [ "_id_m", , "ordernum" ,"specid","materialtypeid"]
			} ]
		}, {
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 150,
			hide:true,
			buttons : [ {
				r_name : 'del',
				text : '删除',
				callback : specorder_manage_functions.delOne,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-danger"
			}, {
				r_name : 'toEdit',
				text : '修改',
				callback : specorder_manage_functions.toEdit,
				paramConfig : [ "_id_m" ]
			} ]
		} ]

	}
};
