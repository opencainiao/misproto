$().ready(function() {
	// 获取全局参数
	var ctx = $("input:hidden[name=ctx]").val();

	// 页面布局
	pageLayout();
	$(window).resize(pageLayout);

	// 栏目树初始化
	lmtree.loadTree();
});

var zTree_Menu;
var curMenu;

function pageLayout() {

	//var mainHeight = $(window).height() - 30;
	
	var divH = $("#home_content",$(window.parent.document.body)).height();
	var breadH = $(".breadcrumb").outerHeight(true);
	var paddingH = 0 *2 ;
	
	var mainH = divH - breadH - paddingH - 5;
	
	//console.log("mH["+mainHeight+"]" + "\ndH["+divH+"]");

	$("div.mainContentLayout").css("height", mainH);

	$("div.leftTreeLayout").css("width", 200);
	$("div.leftTreeLayout").css("float", "left");
	
	$("div.contentLayout").css("width",
			$("div.mainContentLayout").width() - 200 - 8);
	$("div.contentLayout").css("height", mainH );
	$("div.contentLayout").css("float", "right");

	$("div.leftTreeLayout").css("height", mainH );

	// 设置iframe自适应
	$("#xtlmframeid").css("height", mainH );
}

function loadTree() {
	lmtree.loadTree();
}

/*******************************************************************************
 * 栏目树
 */
var lmtree = {
	/***************************************************************************
	 * 初始化栏目树
	 */
	loadTree : function() {
		var url = $.getSitePath() + "/lmgladmin/alllmtree"
		$.getJSON(url, {
			ts : new Date().getTime()
		}, function(data) {
			var setting = {
				data : {
					key : {
						name : "name"
					},
					simpleData : {
						enable : true,
						idKey : "_id_m",
						pIdKey : "parent_id",
						rootPID : "000"
					}
				},
				callback : {
					onClick : lmtree.menuOnClick
				}
			};

			$.fn.zTree.init($("#lmtree"), setting, data);
			
			var zTree_lm = $.fn.zTree.getZTreeObj("lmtree");
			
			zTree_lm.expandAll(true);
			
			var a = $("#" + zTree_lm.getNodes()[0].tId + "_a");
			a.addClass("cur");
		});
	},
	menuOnClick : function(event, treeId, treeNode) {
		event.preventDefault();
		lmtree.clickMenuNode(event, treeId, treeNode);
	},
	clickMenuNode : function(event, treeId, treeNode) {
		
//		var url = $.getSitePath() + "/lmgladmin/" + treeNode._id_m  + "?time=" + new Date().getTime();
		
		var url = $.getSitePath() + "/lmgladmin/" + treeNode._id_m ; 
		
		$("#contentframeid").attr("src",url) ;
	}
};