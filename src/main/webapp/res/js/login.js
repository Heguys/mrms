$(function(){

	//点击身份切换标签
	$(".stuLog").on("click",function(){
		$(this).addClass("act").siblings().removeClass("act");
		$(".stuLogCont").show().siblings(".invigilateLogCont").hide();
	})
	$(".invigilate").on("click",function(){
		$(this).addClass("act").siblings().removeClass("act");
		$(".invigilateLogCont").show().siblings(".stuLogCont").hide();
	})


})
