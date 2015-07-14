$().ready(function() {

	// 初始化内容编辑器
	var editor = $('#content').xheditor({
		tools : 'full'
	});

	$("#uploadFilebtn").click(function() {
		// 文件上传
		ajax_attach_uploader_mou.ajaxupload({
			fileid : "attach_file",
			iscompress : "1",
			//extallow : "txt,jpg",
			//maxsize : 5
			success:attachUpload_event_add.successUpload
		});
	})
	
	$("#ok_attach").on("click",".insertAttach",function(){
		var uploadPath = $.getSitePath() +"/resources/upload/150x120/";
		var uploadOriPath = $.getSitePath() +"/resources/upload/" + $(this).attr("uploadDir") + "/";
		var node = "";
		var isImg = $(this).attr("isimg");
		if(isImg==1) {
			node = "<img src='"+ uploadOriPath +$(this).attr("name")+"' id='attach_"+$(this).attr("title")+"'/>"
		} else {
			//alert("abc");
			node = "<a href='"+ uploadOriPath +$(this).attr("name")+"' id='attach_"+$(this).attr("title")+"'>"+$(this).attr("oldName")+"</a>"
		}
		editor.pasteHTML(node);
	});
	$("#ok_attach").on("click",".deleteAttach",function(){
		var ad = this;
		var id = $(this).attr("title");
		
		//删除服务器的附件
		var url_to = $.getSitePath() + "/attachmentupload/ajaxDeleteOneAttachment?_id_m=" + id;
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
			layer.alert("详细信息不能为空");
			return false;
		}
		
		// 控制按钮为禁用
		$.disableButton("btn_save");
		
		//获取附件主键
		var aIdsIn = $.getMultiParamValue("aids")||[];

		var paramForm = $('form').getFormParam_ux();
		//alert(JSON.stringify(paramForm));
		
		var url_to = $.getSitePath() + "/materialtypehome/add_detail";
		
		var materialtypedetailid = $("#materialtype_detial_id").val();
		if (materialtypedetailid != ""){
			//alert(materialtypedetailid);
			
			url_to = $.getSitePath() + "/materialtypehome/"+ materialtypedetailid +"/update_detail";
		}
		
		var materialtype_id = $("#materialtype_id").val();
		
		$.ajax({
	        type:'POST',
	        url: url_to,
	        data : $.extend(paramForm,{
				ts : new Date().getTime(),
				content:content,
				materialtypeid: materialtype_id,
				attachIds:aIdsIn.join(",")
			}),
			type : 'POST',
			dataType : 'json',
	        success: function(data){
				if (data['success'] == 'n'){
	        		if (data['brErrors']){
	        			$.showBRErrors(data['brErrors'],$("#materialtype_detail_edit_div"));
	        			
	        			if (data['brErrors']['content']){
	        				alert(data['brErrors']['content']);
	        			}
	        		}else {
	        			layer.alert(data['message']);
	        		}
	        	}else{
	        		var detailid= data['message'];
	        		$("#materialtype_detial_id").val(detailid);
	        		
	        		layer.alert( "保存成功" , 1);
	        		
	        		// 刷新父页面的详细信息
	        		parent.reset_materailtype_detail(content);
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
 		var compressedPath = $.getSitePath() +"/resources/upload/" + attach.compressedDir + "/"; 
		var node = "<tr>";
		if(attach.isImg == "1") {
			node+="<td><img src='"+compressedPath + attach.newName+"'/></td>";
		} else {
			node+="<td>普通类型附件</td>";
		}
		node+="<td style='display:none'>"+attach._id_m+"</td>";
		node+="<td>"+attach.oriName+"</td>";
		node+="<td>"+Math.round(attach.size/1024)+"K</td>";
		if(attach.isImg == "1") {
			node+="<td class='hidden'><input type='checkbox' value='"+attach._id_m+"' name='indexPic' class='indexPic'></td>";
			node+="<td class='hidden'><input type='radio' value='"+attach._id_m+"' name='channelPicId'></td>";
		} else {
			node+="<td class='hidden'>&nbsp;</td><td class='hidden'>&nbsp;</td>";
		}
		node+="<td class='hidden'><input type='checkbox' value='"+attach._id_m+"' name='isAttach' class='isAttach'><input type='hidden' name='aids' value='"+attach._id_m+"'/></td>";
		node+="<td><a href='#' class='btn btn-sm btn-primary insertAttach' title='"+attach._id_m+"' isImg='"+attach.isImg+"' name='"+attach.newName+"' oldName='"+attach.oriName+"' uploadDir='"+attach.uploadDir+"'>插入附件</a>&nbsp;<a href='#' title='"+attach._id_m+"' class='btn btn-sm btn-danger deleteAttach'>删除附件</a></td>";
		node+="</tr>";
		return node;
	}
}
