<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>

</head>

<body>
<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />

<ul class="breadcrumb">
    <li><a href="<%=request.getContextPath()%>/backend/sysconsttype/list">系统常量类型管理</a> <span class="divider"></span></li>
    <li class="active">添加常量值</li>
</ul>
	
<div id="add_div" class="onepage">
    <input type="hidden" name="_id" />
    <sf:form modelAttribute="sysconst" class="form-horizontal">
    	
    		<div class="form-group ">
	            <label for="typecode" class="col-sm-2 control-label">
	                常量类型码
	            </label>
	            <div class="col-sm-5">
	                <input type="text" class="form-control" id="typecode" name="typecode" placeholder="" value="${sysconst.typecode}" readonly>
	            </div>
	        </div>
	        <div class="form-group ">
	            <label for="typename" class="col-sm-2 control-label">
	                常量类型
	            </label>
	            <div class="col-sm-5">
	                <input type="text" class="form-control" id="typename" name="typename" placeholder="" value="${sysconst.typename}" readonly>
	            </div>
	        </div>
      		<div class="form-group ">
	            <label for="val" class="col-sm-2 control-label">
	                常量值
	            </label>
	            <div class="col-sm-5">
	                <input type="text" class="form-control" id="val" name="val" placeholder="" >
	            </div>
	        </div>
      		<div class="form-group ">
	            <label for="dspval" class="col-sm-2 control-label">
	                常量显示值
	            </label>
	            <div class="col-sm-5">
	                <input type="text" class="form-control" id="dspval" name="dspval" placeholder="" >
	            </div>
	        </div>
      		<div class="form-group ">
	            <label for="valordernum" class="col-sm-2 control-label">
	                常量值顺序号
	            </label>
	            <div class="col-sm-5">
	                <input type="text" class="form-control" id="valordernum" name="valordernum" placeholder="" >
	            </div>
	        </div>
        
        <hr/>
        <div class="col-sm-7">
        	<button type="button" id="btn_save" class="btn btn-primary btn-lg center-block">提交</button>
        </div>
    </sf:form>
</div>
	
<script>
    $().ready(function() {
    	
        $("#btn_save").bind("click", save);
        
        document.onkeydown = function(event) {
    		if (event.keyCode == 13) {
    			return false;
    		}
    	}
    });

    //保存
    var save = function() {

        // 控制按钮为禁用
        $.disableButton("btn_save");

        var paramForm = $('form').getFormParam_ux();

        var successstr = "新增成功";

        var url_to = "<%=request.getContextPath()%>/backend/sysconst/add";
        //var url_success = "<%=request.getContextPath()%>/backend/sysconst/list";
        var url_success = "<%=request.getContextPath()%>/backend/sysconsttype/manage_sysconst";
        
        var params = [];
		params.push("typecode=${sysconst.typecode}");
		params.push("typename=${sysconst.typename}");
		params.push("ts=" + new Date().getTime());
		
		url_success = url_success + "?" + params.join("&");

        $.ajax({
            type: 'POST',
            url: url_to,
            data: $.extend({
                ts: new Date().getTime()
            },
            paramForm),
            type: 'POST',
            dataType: 'json',
            success: function(data) {

                if (data['success'] == 'n') {
                    if (data['brErrors']) {
                        $.showBRErrors_mou_abs(data['brErrors'], $("#add_div"));
                    } else {
                    	$.alertError(data['message']);
                    }
                } else {
                    $.alertSuccessNewPage("成功", successstr, url_success);
                }
            },
            complete: function(XMLHttpRequest, textStatus) {
                $.enableButton("btn_save");
            }
        });
    };
</script>
</body>
</html>