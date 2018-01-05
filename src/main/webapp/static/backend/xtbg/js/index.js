$(".nav").on("click","li",function(){
	$(this).siblings().removeClass("current");
	var hasChild = !!$(this).find(".subnav").size();
	if(hasChild){
		$(this).toggleClass("hasChild");
	}
	$(this).addClass("current");
});


$(window).resize(function(e) {
    $("#bd").height($(window).height() - $("#hd").height() - $("#ft").height()-6);
	$(".wrap").height($("#bd").height()-6);
	$(".nav").css("minHeight", $(".sidebar").height() - $(".sidebar-header").height()-1);
	$("#iframe").height($(window).height() - $("#hd").height() - $("#ft").height()-12);
}).resize();

$(".nav>li").css({"borderColor":"#dbe9f1"});
$(".nav>.current").prev().css({"borderColor":"#7ac47f"});
$(".nav").on("click",".subnav li ",function(e){
	var aurl = $(this).find("a").attr("date-src");
	var title = $(this).find("a").text();
	$("#iframe")[0].contentWindow.addPanel(title,aurl);
	$(".nav>li").css({"borderColor":"#dbe9f1"});
	$(".nav>.current").prev().css({"borderColor":"#7ac47f"});
	return false;
});


$(".toolbar").on("click",".home-btn",function(e){
	var aurl = $(this).attr("date-src");
	var title = $(this).text();
	$("#iframe")[0].contentWindow.addPanel(title,aurl);
	$(".nav>li").css({"borderColor":"#dbe9f1"});
	$(".nav>.current").prev().css({"borderColor":"#7ac47f"});
	return false;
});

$('.exitDialog input[type=button]').click(function(e) {
    $('.exitDialog').Dialog('close');
	
	if($(this).hasClass('ok')){
		window.location.href = "login.jsp"	;
	}
});