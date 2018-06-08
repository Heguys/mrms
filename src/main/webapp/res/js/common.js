$(function() {
    //侧边栏计时器
    function getNowFormatDate() {
        var date = new Date();
        var month = date.getMonth() + 1;
        month = month < 10 ? "0" + month : month;
        var strDate = date.getDate();
        strDate = strDate < 10 ? "0" + strDate : strDate;
        var hour = date.getHours();
        hour = hour < 10 ? "0" + hour : hour;
        var min = date.getMinutes();
        min = min < 10 ? "0" + min : min;
        var day = date.getDay();
        switch(day){
        	case 0:
        	day = "日";
        	break;
        	case 1:
        	day = "一";
        	break;
        	case 2:
        	day = "二";
        	break;
        	case 3:
        	day = "三";
        	break;
        	case 4:
        	day = "四";
        	break;
        	case 5:
        	day = "五";
        	break;
        	case 6:
        	day = "六";
        	break;
        }
        var currentDate = date.getFullYear() + "/" + month + "/" + strDate + " " + hour + ":" + min
         + " " + "星期" + day;
        $(".timeTXT").html(currentDate);
    }
    getNowFormatDate();
    setInterval(getNowFormatDate, 10000);

	//通用弹出框关闭按钮
	$(".hintMod i").on("click",function(e){
		e.stopPropagation();
		$(this).parents(".mask").fadeOut();
		document.documentElement.style.overflow='auto';
	})

	//注销按钮点击
	$(".newTime a,.ruleLogout").on("click",function(){
		$(".confMask").show().addClass("logoutMask");
		$(".confCont").text("是否确认注销?");
		document.documentElement.style.overflow='hidden';
	})

	//通用确认弹出框关闭按钮
	$(".confCloseBtn,.cancelBtn").on("click",function(){
		$(this).parents(".confMask").fadeOut().removeClass("logoutMask startExamMask deleteUserMask uploadMask downloadMask startExamMask endExamMask resetExamMask");
		var that = this;
		setTimeout(function(){
			$(that).parents(".confMask").fadeOut().removeClass("disciplineReasonMask");
		},500);
		document.documentElement.style.overflow='auto';
		$(".errorHint").val("");
	})


})
