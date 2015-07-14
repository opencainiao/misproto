$().ready(function() {

	$("#btn_search").click(function() {
		unit_search.search();
	});

	$("#btn_confirm").click(function() {
		unit_manage_functions.confirm_choose_unit();
	});

	$("#btn_cancel").click(function() {
		window.top.closeChooseUnitWindow();
	});
	
	unit_search.search();
});

// 刷新本页面
var refreshPage = function() {
	unit_search.search();
}

var unit_manage_functions = {
	pageLayout : function() {
		var inPage_h = $(window).height();
		var table_h = mou_grid_ux.getTableH($("#unit_search"), inPage_h - 230);

		// alert("inpage_h[" + inPage_h + "]\ntableh["+table_h+"]");

		// mou_grid_ux.resetHeight_grid($("#unit_search"), table_h);

		$(".bDiv").height(table_h);
		// $("#unit_search_div").height(table_h);
	},
	// 确认选择一个规格
	confirm_choose_unit : function() {
		
		
		$.disableButton("btn_confirm");

		// 1.如果没有选择，报错：必须选择一个规格
		var checked = $('input:radio[name="radioname"]:checked');
		var val = $('input:radio[name="radioname"]:checked').val();
		if (val == null) {
			layer.alert("请选择一个单位!");
			$.enableButton("btn_confirm");
			return false;
		}

		var _grid = $("#unit_search");
		_grid.data("SELECTFLG","radio");
		var selected = mou_grid_ux.getSelectedAllGrid("unit_search");
		
		//layer.alert(JSON.stringify(selected));
		var unitname =selected[0].name;
		//alert(unitname);
		window.top.setUnit(unitname);
		window.top.closeChooseUnitWindow();
	}
};

var unit_search = {

	/***************************************************************************
	 * 页面加载后的初始化方法
	 */
	search : function() {

		var searchcondition = {};
		searchcondition["name"] = "unitname";
		searchcondition["value"] = $("#unitname").val().trim();

		var params = [];
		params.push(searchcondition);

		unit_search.gridsetting.url = $.getSitePath() + '/unitfront/list?ts=' + new Date().getTime();

		if ($("#unit_search_div").attr("s_times")) {
			params.push({
				name : 'reload',
				value : true
			});
			unit_search.gridsetting.params = params;

			$("#unit_search").flexReload(unit_search.gridsetting);
		} else {
			unit_search.gridsetting.params = params;
			$("#unit_search").flexigrid(unit_search.gridsetting);
		}

		$("#unit_search_div").attr("s_times", 1);
	},

	/***************************************************************************
	 * 表格配置
	 */
	gridsetting : {
		showTableToggleBtn : true,
		resizable : false,
		grid_id : 'list',
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
		onSuccess : unit_manage_functions.pageLayout,
		colModel : [ {
			display : '选择',
			hide : false,
			m_type : 'radio',
			align : 'center',
			select : [ "name" ],
			callback : unit_manage_functions.setunitname
		}, {
			display : '序号',
			m_type : 'sno',
			align : 'center',
			hide : true
		}, {
			display : '名称',
			name : 'name',
			width : 300
		} ]
	}
};
