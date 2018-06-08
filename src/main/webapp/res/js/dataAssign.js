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

	$('#asTab').bootstrapTable({
		url: "trainPlanPageQuery.ajax", //数据源
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
		method: "get", //请求方式
		cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		searchAlign: "left", //查询框对齐方式
		queryParamsType: "limit", //查询参数组织方式
		queryParams: function getParams(params) {
			//params obj
			offset = params.offset;
			params.pagesize = 10;
			params.pageno = offset / 10 + 1;
			return params;
		},
		onLoadSuccess: function(data){  //加载成功时执行
			alert("加载成功"+data);
		},
		onLoadError: function(data){  //加载失败时执行
			alert(data);
			layer.msg("加载数据失败", {time : 1500, icon : 2});
		},
		searchOnEnterKey: false, //回车搜索
		showRefresh: false, //刷新按钮
		showColumns: false, //列选择按钮
		buttonsAlign: "left", //按钮对齐方式
		columns: [{
			title: "序号", //标题
			formatter: function(value, row, index) {
				return offset + index + 1;
			}
		}, {
			field: "trainInstitutionName",
			title: "培训机构"
		}, {
			field: "trainPlanName",
			title: "计划名称"
		}, {
			field: "qualificationName",
			title: "三项岗位人员"
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
			width: "120",
			field: "trainPlanUid",
			formatter: function(value, row, index) {
				return "<button class='commonBlueBtn download' trainPlanUid='"+value+"' >下载</button>";
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
	});

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

	//关闭成功提示模态框
	$(".modHead i").on("click",function(){
    	$(".hintMod").fadeOut();
    })

	//查询功能
	$(".seaBtn").click(function (){

		$("#asTab").bootstrapTable('destroy');
		$('#asTab').bootstrapTable({
			url: "trainPlanPageQuery.ajax", //数据源
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
			method: "get", //请求方式
			cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			searchAlign: "left", //查询框对齐方式
			queryParamsType: "limit", //查询参数组织方式
			queryParams: function getParams(params) {
				//params obj
				offset = params.offset;
				params.pagesize = 10;
				params.pageno = offset / 10 + 1;
				params.querytext = $(".querytext").val();
				params.startTime = $("#startTime").val();
				params.endTime = $("#endTime").val();
				return params;
			},
			searchOnEnterKey: false, //回车搜索
			showRefresh: false, //刷新按钮
			showColumns: false, //列选择按钮
			buttonsAlign: "left", //按钮对齐方式
			columns: [{
				title: "序号", //标题
				formatter: function(value, row, index) {
					return offset + index + 1;
				}
			}, {
				field: "trainInstitutionName",
				title: "培训机构"
			}, {
				field: "trainPlanName",
				title: "计划名称"
			}, {
				field: "qualificationName",
				title: "三项岗位人员"
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
				width: "120",
				field: "trainPlanUid",
				formatter: function(value, row, index) {
					return "<button class='commonBlueBtn download' trainPlanUid='"+value+"' >下载</button>";
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
		});

	});

	//下载确认
	$(document).on("click",".downloadMask .confBtn",function(){
		$(".confMask").fadeOut().removeCLass("downloadMask");
		$.ajax({
			url:"examInfoDownLoad.ajax",
			type:"post",
			data:{
				trainPlanUid:$(this).attr("trainPlanUid")
			},
			success:function(data){
				if(data.success){
					$(".mask").show();
					$(".hintModHead").text("成功提示").removeClass("hintModHeadFail");
					$(".modCont").text("下载成功");
				}else{
					//alert(data.error);
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".modCont").text(data.error);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				//捕获断网情况
				if(XMLHttpRequest.readyState == 0){
					$(".mask").show();
					$(".hintModHead").text("失败提示").addClass("hintModHeadFail");
					$(".modCont").text("网络已断开,请联系软件供应商");
				}
			}
		})
	})

	//下载功能
	$(document).on("click",".download",function(){
		$(".confMask").show().addClass("downloadMask");
		$(".confCont").text("是否确定下载?");
		$(".confMask .confBtn").attr("trainPlanUid",$(this).attr("trainPlanUid"));
	})

})