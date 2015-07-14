$().ready(function() {
	
	registerlogin();

	$("#btn_search").click(function() {
		var searchcondition = $("#search_condition").val().trim();
		
		var url_to = $.getSitePath() + '/search?time=' +  new Date().getTime() + "&search_condition=" + searchcondition;
		window.location.href = url_to;
	});
});

