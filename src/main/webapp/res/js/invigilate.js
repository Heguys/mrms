$(function() {

	var init = function(){
		//编辑页重置考试置灰
		//if( $(".enterStyle").val() == 2 ){ $(".resetTrainPlan").attr("disabled","disabled") };
		//每隔5s刷新页面
		var timer = setInterval(function(){
			refreshStuInfo($(".numTypeHidden").val());
		},5000);

		if($(".enterStyle").val() == 2){
			$(".reexamPwd .endExam,.reexamPwd .reexamPwdBtn").attr("disabled","disabled");
			$(".cancelDiscipline").attr("disabled","disabled");
			clearInterval(timer);
		}
	}
	init();
    
    //标记违纪按钮点击
    $(document).on("click",".markDiscipline",function(){
		//违纪原因弹框
		$(".confMask").show().addClass("disciplineReasonMask");
		$(".confCont").html("如确认标记考生'"+$(this).attr("name")+"'违纪,请填写违纪原因:</br><textarea placeholder='违纪原因(最长10字)' maxlength='10' class='reasonTxt'></textarea>");
		$(".confMask .confBtn").attr("uid",$(this).val());
		$(".confMod .errorHint").text("");
		document.documentElement.style.overflow='hidden';
    })

	//违纪确认
	$(document).on("click",".disciplineReasonMask .confBtn",function(){
		var that = this;
		$.ajax({
			url:"updateBrokenRuleStatus.ajax",
			type: "post",
			data:{
				"uid":$(this).attr("uid"),
				"ruleBroken":1,
				"ruleBrokenAbout":$(".reasonTxt").val()
			},
			beforeSend:function(){
				if($.trim($(".reasonTxt").val()) == ""){
					$(".confMod .errorHint").text("违纪原因不能为空");
					return false;
				}
			},
			success:function(data) {
				$(".confMask").hide().removeClass("disciplineReasonMask");
				document.documentElement.style.overflow='auto';
				if (data.success) {
					refreshNum(data);
					$("#"+$(that).attr("uid")).text(data.invigilatePageModel.trainPlanUid);
					$("button[value='"+$(that).attr("uid")+"']").removeClass("markDiscipline").addClass("cancelDiscipline").text("取消违纪");
					if($(".enterStyle").val() == 2){
						$(".cancelDiscipline").attr("disabled","disabled");
					}
					$("span[stateUid='"+$(that).attr("uid")+"']").removeClass("examingIcon").addClass("discIcon");
					$("span[reasonUid='"+$(that).attr("uid")+"']").show().text("违纪原因:"+$(".reasonTxt").val());
				}else{
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络连接");
				}
			}
		})
	})

	//取消违纪
	$(document).on("click",".cancelDiscipline",function(){
		var that = this;
		$.ajax({
			url:"updateBrokenRuleStatus.ajax",
			type: "post",
			data:{
				"uid":$(this).val(),
				"ruleBroken":0,
				"ruleBrokenAbout":""
			},
			success:function(data) {
				if (data.success) {
					refreshNum(data);
					$("#"+$(that).val()).text(data.invigilatePageModel.trainPlanUid);
					$("button[value='"+$(that).val()+"']").removeClass("cancelDiscipline").addClass("markDiscipline").text("标记违纪");
					$("span[stateUid='"+$(that).val()+"']").removeClass("discIcon").addClass(data.invigilatePageModel.status);
					$("span[reasonUid='"+$(that).val()+"']").hide();
				}else{
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络连接");
				}
			}
		})
	})

	//刷新统计数字
	function refreshNum(data){
		$(".disMan span").text(data.invigilateStatistics.brokenRuleStudentCount);
		$(".examingNum span").text(data.invigilateStatistics.ontestStudentCount);
		$(".allNum span").text(data.invigilateStatistics.totalStudentCount);
		$(".noExamNum span").text(data.invigilateStatistics.untestedStudentCount);
		$(".examedNum span").text(data.invigilateStatistics.testedStudentCount);
	}


    //补考密码点击
    $(".reexamPwdBtn").on("click",function(){
    	$(".reexamPwdMod").slideDown();
    })
    
    //关闭弹框
    $(".closeButton").on("click",function(){
    	$(".reexamPwdMod").slideUp();
    })
    $(".confirm").on("click",function(){
    	$(".reexamPwdMod").slideUp();
    })

	//点击标签条件查询
	$(".manNumber>div").on("click",function(){
		var index = $(this).index() + 1;
		$(".numTypeHidden").val(index);
		refreshStuInfo(index);
	})

	//关键字搜索
	$(".inviSearchBtn").on("click",function(){
		refreshStuInfo($(".numTypeHidden").val());
	});

	//局部刷新考生信息
	function refreshStuInfo(index){
		$.ajax({
			url: "keyWordSearchAndRefreshing.ajax",
			type: "post",
			data: {
				trainPlanUid: $(".inviPlanUid").val(),
				keyWord: $(".inviKeyword").val(),
				labelNumber: index
			},
			success: function(data){
				if (data.success) {
					refreshNum(data);
					var someOneStr = "",
						scoreClass = "",
						scoreTxt = "",
						discClass = "",
						discTxt = "",
						reasonHiddenClass = "",
						isDisabled = "";
					$.each(data.lstInvigilatePageModel,function(i,v){
						scoreClass = v.passed == "1" ? "passGrade":"nopassGrade";
						scoreTxt = v.score == null ? "":v.score;
						discClass = v.ruleBroken == "1" ? "cancelDiscipline" : "markDiscipline";
						discTxt = v.ruleBroken == "1" ? "取消" : "标记";
						reasonHiddenClass = v.ruleBroken !== "1" ? "hide" : "";
						if(v.ruleBroken == "1" && $(".enterStyle").val() == 2){ isDisabled = "disabled" };
						someOneStr += '<div class="someone fl">'
										+'<img src="'+v.photoUrl+'" class="headPhoto fl"/>'
										+'<div class="basicInfo fl">'
										+'<div class="nameInfo">姓名: <span class="name">'+v.name+'</span></div>'
										+'<div class="genderInfo">性别: <span class="gender">'+v.genderId+'</span></div>'
										+'<div class="idInfo">身份证号: <span class="idNumber">'+v.identityId+'</span></div>'
										+'<div class="licenseInfo">准考证号: <span class="license">'+v.examCode+'</span></div>'
										+'<div class="gradeInfo">考试成绩: <span class="grade '+scoreClass+'">'+scoreTxt+'</span></div>'
										+'<div class="gradeInfo">考试场次: 第<span class="examSession">'+v.examtime+'</span>次</div>'
										+'<div class="stateInfo">当前状态: <span stateUid="'+v.uid+'" class="state '+v.status+'"></span></div>'
										+'<div style="display:none" id='+v.uid+'>'+v.trainPlanUid+'</div>'
										+'</div>'
										+'<div class="markBtn invigilateMarkBtn">'
										+'<button value="'+v.uid+'" name="'+v.name+'" '+isDisabled+' class="'+discClass+' disciplineBtn">'+discTxt+'违纪</button>'
										+'</div>'
										+'<span class="disciplineReason '+reasonHiddenClass+'" reasonUid="'+v.uid+'">违纪原因:'+v.ruleBrokenAbout+'</span>'
										+'</div>';
					})
					$(".examineeInfo").html(someOneStr);
				}else{
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络连接");
				}
			}
		})
	}

	//重置考试按钮
	//$(".resetTrainPlan").on("click",function(){
	//	$(".confMask").show().addClass("resetExamMask");
	//	document.documentElement.style.overflow='hidden';
	//	$(".confCont").html("是否确定重置该考试?");
	//	$(".confMask .confBtn").attr("trainPlanUid",$(".inviPlanUid").val());
	//})

	//重置考试确认
	//$(document).on("click",".resetExamMask .confBtn",function(){
	//	$(".confMask").hide().removeClass("resetExamMask");
	//	$.ajax({
	//		url:"resetTrainPlan.ajax",
	//		data:{
	//			trainPlanUid:$(this).attr("trainPlanUid")
	//		},
	//		success:function(data){
	//			if(data.success){
	//				$(".mask").show();
	//				$(".hintModHead").text("成功提示").removeClass("hintModHeadFail");
	//				$(".modCont").text("考试已经重置!即将跳转到考试管理页面");
	//				setTimeout(function(){ window.location.href="localdata.html" },3000);
	//			}else{
	//				$(".mask").show();
	//				$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
	//				$(".mask .modCont").text(data.error);
	//			}
	//		},
	//		error: function(XMLHttpRequest, textStatus, errorThrown){
	//			//捕获断网情况
	//			if(XMLHttpRequest.readyState == 0){
	//				$(".mask").show();
	//				$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
	//				$(".mask .modCont").text("网络已断开,请查看网络连接");
	//			}
	//		}
	//	})
	//})

	//结束考试按钮
	$(".endExam").on("click",function(){
		$(".confMask").show().addClass("endExamMask");
		document.documentElement.style.overflow='hidden';
		$(".confCont").html("还有<span class='examingNumber'>"+$(".examingNum span").text()+"</span>人正在考试,是否确定结束该考试?");
		$(".confMask .confBtn").attr("trainPlanUid",$(".inviPlanUid").val());
	})

	//结束考试确认
	$(document).on("click",".endExamMask .confBtn",function(){
		$(".confMask").hide().removeClass("endExamMask");
		$.ajax({
			url:"teacherEndExam.ajax",
			type:"post",
			data:{
				trainPlanUid:$(this).attr("trainPlanUid")
			},
			success:function(data){
				if(data.success){
					$(".mask").show();
					$(".mask .hintModHead").text("成功提示").removeClass("hintModHeadFail");
					$(".mask .modCont").text("考试已经结束!即将跳转到考试管理页面");
					setTimeout(function(){ window.location.href="localdata.html" },3000);
				}else{
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络连接");
				}
			}
		})
	})



	//返回顶部显示与隐藏
	$(window).scroll(function() {
		if ($(window).scrollTop() >= 200) {
			$(".backToTop").show();
		} else {
			$(".backToTop").hide();
		}
	})

	//返回顶部点击
	$(".backToTop").on("click", function() {
		$("html,body").animate({ scrollTop: 0 }, "normal")
	})

})
