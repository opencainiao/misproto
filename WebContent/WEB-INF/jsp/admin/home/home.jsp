<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>首页</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<jsp:include page="/WEB-INF/jsp/include/common_css.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/include/common_js.jsp"></jsp:include>

<link href="<%=request.getContextPath()%>/resources/css/admin/dashboard.css" rel="stylesheet">
</head>
<body>

	<input type="hidden" name="ctx" value="<%=request.getContextPath()%>" />
	
	<jsp:include page="/WEB-INF/jsp/admin/include/common_nav_header.jsp"></jsp:include>
	<!-- 框架体 -->
	<div class="container-fluid">
      <div class="row">
      
      	<!-- 左侧菜单 -->
        <jsp:include page="/WEB-INF/jsp/admin/include/common_nav_left.jsp"></jsp:include>
        
        <!-- 内容 -->
        <div id="home_content" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2" style="padding:0px">
	    	<iframe id="frame_content_id" name="frame_content_id"  class="frame_content_container" width="100%" frameborder="0"  onload="handIframeLoad()">
	    	</iframe>
	    </div>
      </div>
    </div>
</body>
<script type="text/javascript">
    function initFrameLayoutH() {
        var toShow = [];
        var windowH = $(window).height();
        toShow.push("windowH[" + windowH + "]");

        var divH = $("#home_content").height();
        toShow.push("divH[" + divH + "]");

        var frame_topH = $("#header_top").height();
        toShow.push("frame_topH[" + frame_topH + "]");

        //alert(toShow.join("\n"));
        var topSet = frame_topH;

        var toSet = windowH - frame_topH;
        $("#home_content").height(toSet);
    }

    function autoHeight() {
        var iframe = document.getElementById("frame_content_id");

        var toShow = [];

        var iframeContentH = $(window.frames["frame_content_id"].document).height();

        toShow.push("iframeContentH[" + iframeContentH + "]");

        var toSetH = 0;

        var iframeContentH = 0;

        if (iframe.Document) { //ie自有属性
            iframeContentH = iframe.Document.documentElement.scrollHeight;
        } else if (iframe.contentDocument) { //ie,firefox,chrome,opera,safari
            iframeContentH = iframe.contentDocument.body.offsetHeight;
        }

        var divH = $("#home_content").height();

        toShow.push("divH[" + divH + "]");

        if (iframeContentH > divH) {
            toSetH = iframeContentH;
        } else {
            toSetH = divH - 5;
        }
        
        // alert(toShow.join("\n"));
        iframe.height = toSetH;
        
        if (iframe.Document) { //ie自有属性
            iframeDoc = iframe.Document;
        } else if (iframe.contentDocument) { //ie,firefox,chrome,opera,safari
            iframeDoc = iframe.contentDocument;
        }
        
        iframeDoc.height = toSetH - 5;
    }

    function handIframeLoad() {
        autoHeight();

        return;

        var iframe = document.getElementById("frame_content_id");
        var iframeWindow = iframe.contentWindow;

        if (iframe.Document) { //ie自有属性
            iframeDoc = iframe.Document;
        } else if (iframe.contentDocument) { //ie,firefox,chrome,opera,safari
            iframeDoc = iframe.contentDocument;
        }
    }

    function clearActiveClass() {
        $("a", $("#home_sidebar_nav")).each(function() {
            $thisli = $(this).parent();

            if ($thisli.hasClass("active")) {
                $thisli.removeClass("active");
            }
        });
    }

    $().ready(function() {

        initFrameLayoutH();

        $("a", $("#home_sidebar_nav")).click(function(e) {
            $this = $(this);

            if ($this.attr("data-link") && $this.attr("data-link").indexOf("#") < 0) {

                clearActiveClass();

                $this.parent().addClass("active");

                var url = "<%=request.getContextPath()%>/" + $this.attr("data-link");

                $("#frame_content_id")[0].src = url;
            }
        });
    });
</script>
</html>


