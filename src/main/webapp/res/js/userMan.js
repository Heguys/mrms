$(function() {
//表格
    //根据窗口调整表格高度
    $(window).resize(function() {
        $('#umTab').bootstrapTable('resetView', {
            height: tableHeight()
        })
    })

    $('#umTab').bootstrapTable({
        url: "teacherPageQuery.ajax", //数据源
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
//				params.other = "otherInfo";
            params.pagesize = 10;
            params.pageno = offset / 10 + 1;
            return params;
        },
        searchOnEnterKey: false, //回车搜索
        showRefresh: false, //刷新按钮
        showColumns: false, //列选择按钮
        buttonsAlign: "left", //按钮对齐方式
        columns: [ {
            title: "序号", //标题
            formatter: function (value, row, index) {
                return offset+index+1;
            }
        }, {
            field: "username",
            title: "用户名",
            formatter: function (value, row, index) {
                nameId = value;
                return nameId;
            }
        }, {
            field: "institutionname",
            title: "所属机构"
        }, {
            title: "操作",
            width: "120",
            field: "uid",
            formatter: function (value, row, index) {
                return "<a href='javascript:void(0)' class='modify' uid='"+value+"'>修改</a>" +
                    "&emsp;<a href='javascript:void(0)' class='delete' uid='"+value+"'>删除</a>";
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
        return $(".caseManTab").height() + 10;
    }

    //关闭新增用户模态框
    $(".userAddMod i,.back").on("click",function(){
    	$(".userAddMod").fadeOut();
    })

    //查询功能
    $(".seaBtn").click(function (){
        $("#umTab").bootstrapTable('destroy');
        $('#umTab').bootstrapTable({
            url: "teacherPageQuery.ajax", //数据源
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
//				params.other = "otherInfo";
                params.pagesize = 10;
                params.pageno = offset / 10 + 1;
                params.querytext = $(".keywordSearch input").val();
                return params;
            },
            searchOnEnterKey: false, //回车搜索
            showRefresh: false, //刷新按钮
            showColumns: false, //列选择按钮
            buttonsAlign: "left", //按钮对齐方式
            columns: [ {
                title: "序号", //标题
                formatter: function (value, row, index) {
                    return offset+index+1;
                }
            }, {
                field: "username",
                title: "用户名",
                formatter: function (value, row, index) {
                    nameId = value;
                    return nameId;
                }
            }, {
                field: "institutionname",
                title: "所属机构"
            }, {
                title: "操作",
                width: "120",
                field: "uid",
                formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' class='modify' uid='"+value+"'>修改</a>" +
                        "&emsp;<a href='javascript:void(0)' class='delete' uid='"+value+"'>删除</a>";
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

    //新增用户保存
    $(document).on("click",".addSave",function(){

        $.ajax({
            url: "insertTeacher.ajax",
            type: "post",
            data: {
                username: $(".userName input").val(),
                password: $(".pwd input").val(),
                institutionuid: $("#institutionId>option:selected").val(),
                institutionname: $("#institutionId>option:selected").text()
            },
            beforeSend: function() {
                //非空验证
                if ($.trim($(".userName input").val()) == "" || $.trim($(".pwd input").val()) == "") {
                    $(".mask").show();
                    $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                    $(".mask .modCont").text("输入框不能为空");
                    return false;
                }
            },
            success: function(data){
                if ( data.success ) {
                    $(".addSave").removeClass("addSave");
                    $(".userAddMod").fadeOut();
                    $("#umTab").bootstrapTable('refresh');
                } else {
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
                    $(".mask .modCont").text("网络已断开,请联系软件供应商");
                }
            }
        })
    });

    //修改用户保存
    $(document).on("click",".modSave",function(){

        $.ajax({
            url: "updateTeacher.ajax",
            data: {
                username: $(".userName input").val(),
                password: $(".pwd input").val(),
                institutionuid: $("#institutionId>option:selected").val(),
                institutionname: $("#institutionId>option:selected").text(),
                uid: $(this).attr("uid")
            },
            beforeSend: function() {
                //非空验证
                if ($.trim($(".userName input").val()) == "" || $.trim($(".pwd input").val()) == "") {
                    $(".mask").show();
                    $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                    $(".mask .modCont").text("输入框不能为空");
                    return false;
                }
            },
            success: function(data){
                if ( data.success ) {
                    $(".modSave").removeClass("modSave");
                    $(".userAddMod").fadeOut();
                    $("#umTab").bootstrapTable('refresh');
                } else {
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
                    $(".mask .modCont").text("网络已断开,请联系软件供应商");
                }
            }
        })
    });

    //确认删除
    $(document).on("click",".deleteUserMask .confBtn",function(){
        $(".confMask").fadeOut().removeClass("deleteUserMask");
        $.ajax({
            url: "deleteTeacher.ajax",
            data: {
                uid: $(this).attr("uid")
            },
            success: function(data){
                if ( data.success ) {
                    $("#umTab").bootstrapTable('refresh');
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                //捕获断网情况
                if(XMLHttpRequest.readyState == 0){
                    $(".mask").show();
                    $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                    $(".mask .modCont").text("网络已断开,请联系软件供应商");
                }
            }
        })
    })


    //删除用户
    $(document).on("click",".delete",function(){
        $(".confMask").show().addClass("deleteUserMask");
        $(".confCont").text("确定要删除该用户吗?");
        $(".confMask .confBtn").attr("uid",$(this).attr("uid"));
    })


})
