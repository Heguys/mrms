$(function() {
	//时间戳转化
	function getExamFormatDate(time) {
		var date = new Date(time);
		var month = date.getMonth() + 1;
		month = month < 10 ? "0" + month : month;
		var strDate = date.getDate();
		strDate = strDate < 10 ? "0" + strDate : strDate;
		var currentDate = date.getFullYear() + "-" + month + "-" + strDate;
		return currentDate;
	}

	//表格
	//根据窗口调整表格高度
	$(window).resize(function() {
		$('#asTab').bootstrapTable('resetView', {
			height: tableHeight()
		})
	})

	unfinishedTableLoad("showLocalDataUnfinished.ajax");

	function tableHeight() {
		return $(".dataAssignTab").height() + 10;
	}

	//时间范围选择器
	$('#endTime').datetimepicker({
		language: 'zh-CN', //显示中文
		format: 'yyyy-mm-dd', //显示格式
		minView: "month", //显示到日
		initialDate: new Date(), //初始化当前日期
		autoclose: true, //选中自动关闭
		todayBtn: true //显示今日按钮
	});
	$('#startTime').datetimepicker({
		language: 'zh-CN', //显示中文
		format: 'yyyy-mm-dd', //显示格式
		minView: "month", //显示到日
		initialDate: new Date(), //初始化当前日期
		autoclose: true, //选中自动关闭
		todayBtn: true //显示今日按钮
	});


	//待下载和未完成考试表格加载
	function unfinishedTableLoad(url,pageNum){
		$("#asTab").bootstrapTable('destroy');
		$('#asTab').bootstrapTable({
			url: url, //数据源
			dataField: "rows", //服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
			//height: tableHeight(), //高度调整
			search: false, //是否搜索
			pagination: true, //是否分页
			pageSize: 10, //单页记录数
			// smartDisplay: true,//智能显示分页按钮
			pageList: [10], //分页步进值
			sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			contentType: "application/x-www-form-urlencoded", //请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
			dataType: "json", //期待返回数据类型
			// toolbar: '#toolbar', //工具按钮用哪个容器
			method: "post", //请求方式
			cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			searchAlign: "left", //查询框对齐方式
			queryParamsType: "limit", //查询参数组织方式
			queryParams: function getParams(params) {
				offset = params.offset;
				params.pagesize = 10;
				params.querytext = $(".querytext").val();
				params.startTime = $("#startTime").val();
				params.endTime = $("#endTime").val();
				params.status = $("#operateState>option:selected").val();
				params.qualification = $("#examObject>option:selected").val();
				params.assessmentNature = $("#examNature>option:selected").val();
				params.pageno = offset / 10 + 1;
				return params;
			},
			onLoadSuccess: function(data){  //加载成功时执行
				if(!data.success){
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			//onLoadError: function(data){  //加载失败时执行
			//	alert(data);
			//	layer.msg("加载数据失败", {time : 1500, icon : 2});
			//},
			searchOnEnterKey: false, //回车搜索
			showRefresh: false, //刷新按钮
			showColumns: false, //列选择按钮
			buttonsAlign: "left", //按钮对齐方式
			columns: [{
				title: "序号", //标题
				field: "trainPlanUid",
				formatter: function(value, row, index) {
					trainPlanUid = value;
					return offset + index + 1;
				}
			}, {
				field: "trainInstitutionName",
				title: "培训机构"
			}, {
				field: "trainPlanName",
				title: "计划名称",
				formatter: function(value, row, index) {
					trainPlanName = value;
					return value;
				}
			}, {
				field: "qualificationName",
				title: "考核对象"
			}, {
				field: "assessmentName",
				title: "考试性质"
			}, {
				field: "examBeginDate",
				title: "考试开始时间",
				formatter: function(value, row, index) {
					return getExamFormatDate(value);
				}
			}, {
				field: "examEndDate",
				title: "考试结束时间",
				formatter: function(value, row, index) {
					return getExamFormatDate(value);
				}
			}, {
				field: "examLimit",
				title: "考试人数"
			}, {
				title: "操作",
				width: "180",
				field: "status",
				formatter: function(value, row, index) {
					if(value == "0"){
						return "<button class='commonBlueBtn beginExam' trainPlanUid='"+trainPlanUid+"' trainPlanName='"+trainPlanName+"' >开始考试</button>&emsp;"+
							"<button class='commonBlueBtn changePwd' trainPlanUid='"+trainPlanUid+"' >修改密码</button>";
					}else if(value == "1"){
						return "<button class='commonBlueBtn beginInvigilate' trainPlanUid='"+trainPlanUid+"' >开始监考</button>&emsp;";
						//+ "<button class='commonBlueBtn endExam' trainPlanUid='"+trainPlanUid+"' >结束考试</button>";
					}else if(value == "4"){
						return "<button class='commonBlueBtn download' trainPlanUid='"+trainPlanUid+"' trainPlanName='"+trainPlanName+"'>考试下载</button>";
					}else if(value == "5"){
						return "<span class='upLoadSuccessTxt'><i class='naike'></i>已下载</span>";
					}
				}
			}],
			locale: "zh-CN", //中文支持,
			detailView: false, //是否显示详情折叠
			detailFormatter: function(index, row, element) {
				var html = '';
				$.each(row, function(key, val) {
					html += "<p>" + key + ":" + val + "</p>"
				});
				return html;
			}
		})
	}

	//已完成考试加载
	function finishedTableLoad(){
		$("#asTab").bootstrapTable('destroy');
		$('#asTab').bootstrapTable({
			url: "showLocalDataFinished.ajax", //数据源
			dataField: "rows", //服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
			//height: tableHeight(), //高度调整
			search: false, //是否搜索
			pagination: true, //是否分页
			pageSize: 10, //单页记录数
			// smartDisplay: true,//智能显示分页按钮
			pageList: [10], //分页步进值
			sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
			contentType: "application/x-www-form-urlencoded", //请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
			dataType: "json", //期待返回数据类型
			// toolbar: '#toolbar', //工具按钮用哪个容器
			method: "post", //请求方式
			cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			searchAlign: "left", //查询框对齐方式
			queryParamsType: "limit", //查询参数组织方式
			queryParams: function getParams(params) {
				//params obj
				offset = params.offset;
				params.pagesize = 10;
				params.querytext = $(".querytext").val();
				params.startTime = $("#startTime").val();
				params.endTime = $("#endTime").val();
				params.status = $("#operateState>option:selected").val();
				params.qualification = $("#examObject>option:selected").val();
				params.assessmentNature = $("#examNature>option:selected").val();
				params.pageno = offset / 10 + 1;
				return params;
			},
			onLoadSuccess: function(data){  //加载成功时执行
				if(!data.success){
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			searchOnEnterKey: false, //回车搜索
			showRefresh: false, //刷新按钮
			showColumns: false, //列选择按钮
			buttonsAlign: "left", //按钮对齐方式
			columns: [
				{
				title: "序号", //标题
				field: "trainPlanUid",
				formatter: function(value, row, index) {
					trainPlanUid = value;
					return offset + index + 1;
				}
			}, {
				field: "trainInstitutionName",
				title: "培训机构"
			}, {
				field: "trainPlanName",
				title: "计划名称",
				formatter: function(value, row, index) {
					trainPlanName = value;
					return value;
				}
			}, {
				field: "qualificationName",
				title: "考核对象"
			}, {
				field: "assessmentName",
				title: "考试性质"
			}, {
				field: "examBeginDate",
				title: "考试开始时间",
				formatter: function(value, row, index) {
					return getExamFormatDate(value);
				}
			}, {
				field: "examEndDate",
				title: "考试结束时间",
				formatter: function(value, row, index) {
					return getExamFormatDate(value);
				}
			}, {
				field: "examLimit",
				title: "总人数"
			}, {
				field: "missExamCount",
				title: "缺考人数"
			}, {
				field: "brokenRuleCount",
				title: "违纪人数"
			}, {
				field: "invigilator",
				title: "监考员"
			}, {
				title: "操作",
				width: "120",
				field: "status",
				formatter: function(value, row, index) {
					if(value == "3"){
						return "<span class='upLoadSuccessTxt'><i class='naike'></i>上传成功</span>"
					}else if(value == "2"){
						return "<button class='commonBlueBtn examUpload' trainPlanUid='"+trainPlanUid+"'  trainPlanName='"+trainPlanName+"'>考试上传</button>&ensp;"+
							"<span class='edit' trainPlanUid='"+trainPlanUid+"'>编辑</span>";
					}
				}
			}],
			locale: "zh-CN", //中文支持,
			detailView: false, //是否显示详情折叠
			detailFormatter: function(index, row, element) {
				var html = '';
				$.each(row, function(key, val) {
					html += "<p>" + key + ":" + val + "</p>"
				});
				return html;
			}
		})
	}

	//开始考试确认
	$(document).on("click",".startExamMask .confBtn",function(){
		var that = this;
			$.ajax({
				url:"teacherAllowExamStart.ajax",
				type:"post",
				data:{
					trainPlanUid:$(this).attr("trainPlanUid")
				},
				success:function(data){
					if(data.success){
						$(".confMask").hide();
						$(".mask").show();
						$(".hintModHead").text("成功提示").removeClass("hintModHeadFail");
						$(".mask .modCont").text("数据初始化成功，考试正常可以进行!");
						setTimeout(function(){
							window.location.href = "toInvigilatorPage.html?trainPlanUid="+$(that).attr('trainPlanUid')
						},2000);
					}else{
						$(".confMask").hide();
						$(".mask").show();
						$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
						$(".mask .modCont").text(data.error);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown){
					//捕获断网情况
					if(XMLHttpRequest.readyState == 0){
						$(".mask").show();
						$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
						$(".mask .modCont").text("网络已断开,请检查网络");
					}
				}
			})
	})

	//开始考试按钮
	$(document).on("click",".beginExam",function(){
		$(".confMask").show().addClass("startExamMask");
		$(".confCont").text("是否确定开始'"+$(this).attr("trainPlanName")+"'考试?");
		$(".confMask .confBtn").attr("trainPlanUid",$(this).attr("trainPlanUid"));
		document.documentElement.style.overflow='hidden';
	})

	//确认上传
	$(document).on("click",".uploadMask .confBtn",function(){
		$(".confMask").fadeOut().removeClass("uploadMask");
		$.ajax({
			url:"upLoadExamInfo.ajax",
			type:"post",
			data:{
				trainPlanUid:$(this).attr("trainPlanUid")
			},
			beforeSend: function () {
				$(".hintMod").hide();
				$(".mask").show();
				$(".loadingMod").show();
				$(".loadingTxtChange").text("上传");
			},
			success:function(data){
				$(".loadingMod").hide();
				$(".hintMod").show();
				if(data.success){
					$(".mask .hintModHead").text("成功提示").removeClass("hintModHeadFail");
					$(".mask.mask .modCont").text("考试信息上传成功!");
					$("#asTab").bootstrapTable('refresh');
				}else{
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".hintMod").show();
					$(".loadingMod").hide();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络");
				}
			}
		})
	})

	//考试上传按钮
	$(document).on("click",".examUpload",function(){
		$(".confMask").show().addClass("uploadMask");
		$(".confCont").text("是否确定上传'"+$(this).attr("trainPlanName")+"'考试?");
		$(".confMask .confBtn").attr("trainPlanUid",$(this).attr("trainPlanUid"));
		document.documentElement.style.overflow='hidden';
	})

	//修改密码
	$(document).on("click",".changePwd",function(){
		$(".pwdMask").show();
		$(".confirm").attr("trainPlanUid",$(this).attr("trainPlanUid"));
		$(".hintInfo").html("");
	})

	//关闭修改密码框
	$(".closeButton").on("click",function(){
		$(".pwdMask").fadeOut();
	})

	//修改密码确定
	$(".confirm").on("click",function(){
		$.ajax({
			url:"setMakeUpPassword.ajax",
			type:"post",
			data:{
				uid:$(this).attr("trainPlanUid"),
				makeUpPassword:$(".pwdMask .modCont input").val()
			},
			beforeSend:function(){
				if($.trim($(".pwdMask .modCont input").val()) == ""){
					$(".hintInfo").text("密码不能为空");
					return false;
				}else if($(".pwdMask .modCont input").val().length != 6 ){
					$(".hintInfo").text("请输入6位密码");
					return false;
				}else if( isNaN($(".pwdMask .modCont input").val())){
					$(".hintInfo").text("请输入纯数字密码");
					return false;
				}
			},
			success:function(data){
				if(data.success){
					$(".pwdMask").hide();
					$(".mask").show();
					$(".mask .hintModHead").text("成功提示").removeClass("hintModHeadFail");
					$(".mask .modCont").text("密码修改成功");
				}else{
					$(".pwdMask").hide();
					$(".mask").show();
					$(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络");
				}
			}
		})
	})

	//开始监考按钮
	$(document).on("click",".beginInvigilate",function(){
		window.location.href='toInvigilatorPage.html?trainPlanUid='+$(this).attr("trainPlanUid");
	})

	//编辑按钮
	$(document).on("click",".edit",function(){
		window.location.href='toInvigilatorPage.html?trainPlanUid='+$(this).attr("trainPlanUid");
	})

	//考试类型标签点击
	$(".examTypeTab>button").on("click",function(){
		$(this).addClass("act").siblings("button").removeClass("act");
		$(".resetBtn").click();
	})

	//未完成考试标签点击
	$(".noExamBtn").on("click",function(){
		unfinishedTableLoad("showLocalDataUnfinished.ajax");
		//$(".startExamOp").siblings("option[class]").attr("disabled","disabled").end().removeAttr("disabled");
		$("#operateState option[class]").remove();
		$("#operateState").append('<option value="0" class="startExamOp">开始考试</option> <option value="1" class="startExamOp">开始监考</option>');
	})

	//已完成考试标签点击
	$(".examedBtn").on("click",function(){
		finishedTableLoad();
		//$(".uploadOp").siblings("option[class]").hide().end().show();
		//$(".uploadOp").siblings("option[class]").attr("disabled","disabled").end().removeAttr("disabled");
		$("#operateState option[class]").remove();
		$("#operateState").append('<option value="2"  class="uploadOp">考试上传</option> <option value="3"  class="uploadOp">上传成功</option>');
	})

	//待下载考试标签点击
	$(".downloadExamBtn").on("click",function(){
		unfinishedTableLoad("trainPlanPageQuery.ajax");
		$("#operateState option[class]").remove();
		//$(".downloadOp").siblings("option[class]").hide().end().show();
		//$(".downloadOp").siblings("option[class]").attr("disabled","disabled").end().removeAttr("disabled");

	})

	//查询功能
	$(".seaBtn").click(function(){
		if($(".noExamBtn").hasClass("act")){ // 未完成考试查询
			unfinishedTableLoad("showLocalDataUnfinished.ajax");
		}else if($(".examedBtn").hasClass("act")){ // 已完成考试查询
			finishedTableLoad();
		}else if($(".downloadExamBtn").hasClass("act")){ // 待下载考试查询
			unfinishedTableLoad("trainPlanPageQuery.ajax");
		}
	})

	//下载确认
	$(document).on("click",".downloadMask .confBtn",function(){
		$(".confMask").fadeOut().removeClass("downloadMask");
		$.ajax({
			url:"examInfoDownLoad.ajax",
			type:"post",
			data:{
				trainPlanUid:$(this).attr("trainPlanUid")
			},
			beforeSend: function () {
				$(".hintMod").hide();
				$(".mask").show();
				$(".loadingMod").show();
				$(".loadingTxtChange").text("下载");
			},
			success:function(data){
				$(".loadingMod").hide();
				$(".hintMod").show();
				$(".mask");
				if(data.success){
					$(".hintModHead").text("成功提示").removeClass("hintModHeadFail");
					$(".mask .modCont").text("考试信息下载成功!");
					//var pageNum = ($(".page-number.active a").text() - 1) * 10;
					unfinishedTableLoad("trainPlanPageQuery.ajax");
				}else{
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".hintMod").show();
					$(".loadingMod").hide();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".mask .modCont").text("网络已断开,请检查网络");
				}
			}
		})
	})

	//下载功能
	$(document).on("click",".download",function(){
		$(".confMask").show().addClass("downloadMask");
		$(".confCont").text("是否确定下载"+$(this).attr("trainPlanName")+"考试?");
		$(".confMask .confBtn").attr("trainPlanUid",$(this).attr("trainPlanUid"));
	})

	//阻止用户手动输入日期
	$('#startTime,#endTime').on("keydown",function(e){
		e.preventDefault();
	})

})