<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.3/jquery.js"></script>
<script>
	window.jQuery || document.write('<script src="<%=request.getContextPath()%>/resources/js/jquery-2.1.3.min.js" type="text/javascript"><\/script>');
</script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mou.ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/extend/layer.ext.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.nbq.ux.js"></script>
<script>

function check_low(){
	
	var url =  '<%=request.getContextPath()%>/toupdatebrowser';

	if ($.isLowerBrowser()){
		window.location.href = url;
	}
};

window.onload= check_low;
</script>