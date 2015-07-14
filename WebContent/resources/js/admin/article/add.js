$().ready(function() {

	// 栏目树初始化
	lmtree.loadTree();
	$("#lmname").click(lmtree.showMenu);

	// 初始化内容编辑器
	var editor = $('#content').xheditor({
		tools : 'full'
	});

	// 初始化关键字
	$("#keyword").keywordinput({
		autocomplete : {
			enable : true,
			url : $.getSitePath() + "/articleadmin/searchKeyword",
			minLength : 2
		}
	});

	$("#uploadFilebtn").click(function() {
		// 文件上传
		ajax_attach_uploader_mou.ajaxupload({
			fileid : "attach_file",
			//extallow : "txt,jpg",
			//maxsize : 5
			success:attachUpload_event_add.successUpload
		});
	})
	
	$("#ok_attach").on("click",".insertAttach",function(){
		var uploadPath = $.getSitePath() +"/resources/upload/150x120/";
		var node = "";
		var isImg = $(this).attr("isimg");
		if(isImg==1) {
			node = "<img src='"+uploadPath+$(this).attr("name")+"' id='attach_"+$(this).attr("title")+"'/>"
		} else {
			//alert("abc");
			node = "<a href='"+uploadPath+$(this).attr("name")+"' id='attach_"+$(this).attr("title")+"'>"+$(this).attr("oldName")+"</a>"
		}
		editor.pasteHTML(node);
	});
	$("#ok_attach").on("click",".deleteAttach",function(){
		var ad = this;
		var id = $(this).attr("title");
		
		//删除服务器的附件
		var url_to = $.getSitePath() + "/fileupload/ajaxDeleteOneAttachment?_id_m=" + id;
		$.ajax({
	        type:'POST',
	        url: url_to,
	        data : {
				ts : new Date().getTime()
			},
			dataType : 'json',
	        success: function(data){
	        	if (data['success'] == 'n'){
	        		layer.alert(data['message']);
	        	}else{
	        	//	layer.alert("删除附件成功");
	        	}
	        }
	    }); 
		
		//删除附件列表中的文件
		$(ad).parent("td").parent("tr").remove();
		
		// 删除文本编辑器中引用该文件的内容
		var todelinEdit	= $("#xhe0_iframe").contents().find("#attach_"+id);
		while(todelinEdit.length >0 ){
			todelinEdit.remove();
			
			todelinEdit	= $("#xhe0_iframe").contents().find("#attach_"+id);
		}
	});
	
	// 提交按钮
	$("#btn_save").bind("click", function() {
		
		var content = editor.getSource();
		if (content.trim() == ""){
			alert("文章内容不能为空");
			return false;
		}
		//alert(content);
		
		//获取关键字
		var aksIn = $.getMultiParamValue("aks");
		
		//获取附件主键
		var aIdsIn = $.getMultiParamValue("aids");
		
		//alert(aksIn.join("_____") + "\n" + aIdsIn.join("_____"));
		// var move_ids="";
        // $("[name='is_move'][checked]").each(function(){
        //    move_ids+=$(this).val()+"-";
        // })
		
		// 控制按钮为禁用
		$.disableButton("btn_save");

		var paramForm = $('form').getFormParam_ux();
		alert(JSON.stringify(paramForm));
		
		var url_to = $.getSitePath() + "/articleadmin/add";
		
		var articleid = $("input[name=articleid]").val();
		if (articleid != ""){
			alert(articleid);
			
			url_to = $.getSitePath() + "/articleadmin/"+ articleid +"/update";
		}
		
		$.ajax({
	        type:'POST',
	        url: url_to,
	        data : $.extend(paramForm,{
				ts : new Date().getTime(),
				content:content,
				keyword:aksIn.join("|"),
				attachIds:aIdsIn.join(",")
			}),
			type : 'POST',
			dataType : 'json',
	        success: function(data){
				if (data['success'] == 'n'){
	        		if (data['brErrors']){
	        			$.showBRErrors(data['brErrors'],$("#articleadmin_add_div"));
	        			
	        			if (data['brErrors']['content']){
	        				alert(data['brErrors']['content']);
	        			}
	        		}else {
	        			layer.alert(data['message']);
	        		}
	        	}else{
	        		var articleId= data['message'];
	        		$("input[name=articleid]").val(articleId);
	        		
	        		layer.alert( "保存成功\n" + articleId, 1);
	        	}
	        },
	        complete:function(XMLHttpRequest, textStatus){
	        	
	        	//layer.alert(JSON.stringify(XMLHttpRequest));
	        	// 控制按钮为可用
	        	$.enableButton("btn_save");
	        }
	    }); 
	});

});

var attachUpload_event_add = {
	successUpload:function(data){
		
		//layer.alert( "attachUpload_event_add.successUpload\n" + JSON.stringify(data),1);
		
		var node = attachUpload_event_add.createAttachNode(data.attach);
		$("#ok_attach").find("tbody").append(node);
	},

 	createAttachNode:function(attach) {
 		//layer.alert( "attachUpload_event_add.successUpload\n" + JSON.stringify(attach),1);
 		var uploadPath = $.getSitePath() +"/resources/upload/150x120/";
		var node = "<tr>";
		if(attach.isImg == "1") {
			node+="<td><img src='"+uploadPath + attach.newName+"'/></td>";
		} else {
			node+="<td>普通类型附件</td>";
		}
		node+="<td style='display:none'>"+attach._id_m+"</td>";
		node+="<td>"+attach.oriName+"</td>";
		node+="<td>"+Math.round(attach.size/1024)+"K</td>";
		if(attach.isImg == "1") {
			node+="<td><input type='checkbox' value='"+attach._id_m+"' name='indexPic' class='indexPic'></td>";
			node+="<td><input type='radio' value='"+attach._id_m+"' name='channelPicId'></td>";
		} else {
			node+="<td>&nbsp;</td><td>&nbsp;</td>";
		}
		node+="<td><input type='checkbox' value='"+attach._id_m+"' name='isAttach' class='isAttach'><input type='hidden' name='aids' value='"+attach._id_m+"'/></td>";
		node+="<td><a href='#' class='btn btn-sm btn-primary insertAttach' title='"+attach._id_m+"' isImg='"+attach.isImg+"' name='"+attach.newName+"' oldName='"+attach.oriName+"'>插入附件</a>&nbsp;<a href='#' title='"+attach._id_m+"' class='btn btn-sm btn-danger deleteAttach'>删除附件</a></td>";
		node+="</tr>";
		return node;
	}
}


/*******************************************************************************
 * 栏目树
 */
var lmtree = {
	/***************************************************************************
	 * 初始化栏目树
	 */
	loadTree : function() {
		var url = $.getSitePath() + "/articleadmin/alllmtree"
		$.getJSON(url, {
		// ts : new Date().getTime()
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
		});
	},
	menuOnClick : function(event, treeId, treeNode) {
		event.preventDefault();
		if (!treeNode.isParent) {
			lmtree.clickMenuNode(event, treeId, treeNode);
		}
	},
	clickMenuNode : function(event, treeId, treeNode) {
		$("#lmname").val(treeNode.name);
		$("#lmid").val(treeNode._id_m);
		lmtree.hideMenu();
	},
	onBodyDown : function(event) {
		if (!(event.target.id == "lmtreeContent" || $(event.target).parents(
				"#lmtreeContent").length > 0)) {
			lmtree.hideMenu();
		}
	},
	showMenu : function() {
		$("#lmtree").width($(this).width() - 9);
		var cObj = $("#lmname");
		var cOffset = $("#lmname").offset();
		$("#lmtreeContent").css({
			left : cOffset.left + "px",
			top : cOffset.top + cObj.outerHeight() + "px"
		}).slideDown("fast");

		$("body").bind("mousedown", lmtree.onBodyDown);
	},
	hideMenu : function() {
		$("#lmtreeContent").fadeOut("fast");
		$("body").unbind("mousedown", lmtree.onBodyDown);
	}
};
