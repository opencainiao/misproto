$().ready(function() {

	specification_search.search();
	
	$("#btn_search").click(function() {
		specification_search.search();
	});
	
	$("#add_specification").click(function() {
		specification_manage_functions.toAddSpecification();
	});
	
	$("#btn_confirm").click(function() {
		specification_manage_functions.confirm_add_spec();
	});
	
	$("#btn_cancel").click(function() {
		window.top.closeChooseSepcWindow();
	});
});

// 刷新本页面
var refreshPage = function(){
	specification_search.search();
}

var specification_manage_functions = {
		
	//查询使用该规格的材料类型信息
	showUsedMaterialtypes:function(data){
		parent.pupUpUsedMaterialtypes(data["_id_m"]);
	},
	// 查看规格的选项值列表
	showSpecItems:function(data){
		parent.pupUpShowSpecItems(data["_id_m"]);
	},
	// 添加规格
	toAddSpecification:function(){
		window.top.pupUpAddSpc();
	},
	pageLayout : function() {
		var inPage_h = $(window).height();
		var table_h = mou_grid_ux.getTableH($("#specification_search"), inPage_h - 230);
		
		//alert("inpage_h[" + inPage_h + "]\ntableh["+table_h+"]");
		
		//mou_grid_ux.resetHeight_grid($("#specification_search"), table_h);
		
		$(".bDiv").height(table_h);
		//$("#specification_search_div").height(table_h);
	},
	// 确认选择一个规格
	confirm_add_spec:function(){
		$.disableButton("btn_confirm");
		
		// 1.如果没有选择，报错：必须选择一个规格
		var checked = $('input:radio[name="radioname"]:checked');
		var val=$('input:radio[name="radioname"]:checked').val();
        if(val==null){
            layer.alert("请选择一个规格!");
            $.enableButton("btn_confirm");
            return false;
        }
    	var specid = checked.attr("_id_m");
    	var specname = checked.attr("specname");
    	var materialtypeid = $('input[name="materialtypeid"]').val();
    	var materialtypename = $('input[name="materialtypename"]').val();
		
		// 2.添加类型与规格信息
		var url_to = $.getSitePath() + "/materialtypehome/addspec?specid=" + specid+ "&specname=" + specname + "&materialtypeid=" + materialtypeid  + "&materialtypename=" + materialtypename;
		$.ajax({
	        type:'POST',
	        url: url_to,
	        data : {
				ts : new Date().getTime()
			},
			type : 'POST',
			dataType : 'json',
	        success: function(data){
	        	if (data['success'] == 'n'){
	        		layer.alert(data['message']);
	        	}else{
	        		parent.refreshAddMaterial();
	    			window.top.closeChooseSepcWindow();
	        	}
	        },
	        complete:function(XMLHttpRequest, textStatus){
	        	
	        	// 控制按钮为可用
	        	$.enableButton("btn_confirm");
	        }
	    }); 
	}
};

var specification_search = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {
		
		var searchcondition = {};
		searchcondition["name"] = "specname";
		searchcondition["value"] = $("#specname").val().trim();
		
		var params = [];
		params.push(searchcondition);
		
		specification_search.gridsetting.url = $.getSitePath() + '/specificationfront/list?ts=' + new Date().getTime();
		
		if ($("#specification_search_div").attr("s_times")){
			params.push({
				name : 'reload',
				value : true
			});
			specification_search.gridsetting.params = params;
			
			$("#specification_search").flexReload(specification_search.gridsetting);
		}else{
			specification_search.gridsetting.params = params;
			$("#specification_search").flexigrid(specification_search.gridsetting);
		}

		$("#specification_search_div").attr("s_times",1);
	},
	
	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : true,
		resizable : false,
		grid_id:'list',
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
		nomsg : '没有符合条件的数据',
		onSuccess : specification_manage_functions.pageLayout,
		colModel : [ {
			display : '选择',
			hide : false,
			m_type : 'radio',
			align : 'center',
			valname : '_id_m,specname',
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
			display : '规格名称',
			name : 'specname',
			m_type : 'link',
			width : 200,
			select : [ "_id_m" ],
			callback : specification_manage_functions.showUsedMaterialtypes
		}, {
			display : '规格描述',
			name : 'description',
			width : 200
		},{
			display : '操作',
			name : 'operation',
			m_type : 'buttons',
			width : 200,
			hide:false,
			buttons : [  {
				r_name : 'search',
				text : '查看规格选项',
				callback : specification_manage_functions.showSpecItems,
				paramConfig : [ "_id_m" ],
				css : "btn btn-small btn-primary"
			} ]
		}]
	}
};
