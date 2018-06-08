$(function () {

    //旗帜点击
    $(document).on("click", ".flag", function () {
        var No = $(this).next(".orderNum").text();
        if ($(this).hasClass("flagW")) {
            $(this).removeClass("flagW").addClass("flagR");
            $(".questionNum li.answer" + No).addClass("marked");
        } else {
            $(this).removeClass("flagR").addClass("flagW");
            $(".questionNum li.answer" + No).removeClass("marked");
        }
    })

    //创建题目答案队列
    var answerArr = localStorage.restAnswerArr == undefined || localStorage.restAnswerArr == "undefined" ? [] : JSON.parse(localStorage.restAnswerArr);

    //断网后答案队列回显
    if (localStorage.restAnswerArr != undefined && localStorage.restAnswerArr != "undefined") {
        setTimeout(function () {
            questionReviewMethod(JSON.parse(localStorage.restAnswerArr));
        }, 1000);
    }

    //加载题目及答题卡
    $.ajax({
        url: "startExam.ajax",
        type: "POST",
        success: function (data) {
            if (data.success) {
                var questionNumStr = ''; // 答题卡题号字符串
                var allQuestionStr = ''; // 所有题目字符串
                var quesTypeStr = ''; // 题目类型字符串
                var optionStr = ''; // 选项字符串
                //时间格式化
                function format(time) {
                    time = time < 10 ? '0' + time : time;
                    return time;
                }
                var hour = parseInt(data.timeLong / 60 / 60);
                var min = parseInt(data.timeLong / 60) % 60;
                var sec = data.timeLong % 60;
                var examTime = setInterval(function () {
                    if ((hour == 0 && min == 0 && sec == 0) || hour < 0 || min < 0) {
                        $(".restTime").html("时间结束,禁止答题!");
                        $("input").attr("disabled", "disabled");
                        //检测队列是否为空
                        while (answerArr.length == 0) {
                            clearInterval(examTime);
                            // 队列为空,即说明所有答案已传到后台
                            $.ajax({
                                url: "submitExamPaper.ajax",
                                data: {},
                                success: function (data) {
                                    if (data.success) {
                                        $(".mask").show();
                                        $(".hintModHead").text("考试结束,禁止答题").removeClass("hintModHeadFail");
                                        $(".modCont").text("考试结束,即将跳转到考生信息页面");
                                        //localStorage.restAnswerArr = "undefined";
                                        setTimeout(function () {
                                            window.location.href = "studentInfo.html"
                                        }, 3000);
                                    } else {
                                        $(".mask").show();
                                        $(".mask .hintModHead").text("考试结束,禁止答题").addClass("hintModHeadFail");
                                        $(".mask .modCont").text(data.error);
                                        if (data.skip) {
                                            setTimeout(function () {
                                                window.location.href = "studentInfo.html"
                                            }, 3000);
                                        }
                                    }
                                },
                                error: function (XMLHttpRequest, textStatus, errorThrown) {
                                    //捕获断网情况
                                    if (XMLHttpRequest.readyState == 0) {
                                        $(".mask").show();
                                        $(".mask .hintModHead").text("考试结束,禁止答题").addClass("hintModHeadFail");
                                        $(".mask .modCont").text("网络已断开,请联系监考老师");
                                    }
                                }
                            })
                            break;
                        }
                    } else {
                        if (sec == 0) {
                            sec = 59;
                            if (min == 0) {
                                min = 59;
                                hour--;
                            } else {
                                min--;
                            }
                        } else {
                            sec--;
                        }
                        var countDown = format(hour) + ' : ' + format(min) + ' : ' + format(sec);
                        $(".restTime").html(countDown);
                    }
                }, 1000);
                var inputType = "";
                //渲染题目
                for (var i = 0; i < data.questions.length; i++) {
                    questionNumStr += '<li class="quesTag answer' + (i + 1) + '">' + (i + 1) + '</li>';
                    //拼写题型字符串
                    if (data.questions[i].questionTypeCode == 3) {
                        quesTypeStr = '<span class="quesType judge">【判断题】 </span>';
                        inputType = "radio";
                    } else if (data.questions[i].questionTypeCode == 1) {
                        quesTypeStr = '<span class="quesType singleSelect">【单选题】</span>';
                        inputType = "radio";
                    } else if (data.questions[i].questionTypeCode == 2) {
                        quesTypeStr = '<span class="quesType multSel">【多选题】</span>';
                        inputType = "checkbox";
                    }

                    //拼写选项字符串
                    optionStr = '';
                    $.each(data.questions[i].answers, function (index, value) {
                        optionStr += '<li class="option"><input type="' + inputType + '" questionId="' + data.questions[i].id + '" quesType="' + data.questions[i].questionTypeCode + '" value="' + value.id + '" score="' + data.questions[i].score + '" id="answer_' + data.questions[i].sort + '_option_' + (index + 1) + '" name="answer' + data.questions[i].sort + '"/>' +
                            ' <label for="answer_' + data.questions[i].sort + '_option_' + (index + 1) + '" name="answer' + data.questions[i].sort + '">' + value.description + '</label></li>';
                    })

                    allQuestionStr +=
                        '<form class="singleQuestion" id="' + data.questions[i].id + '" sort="' + data.questions[i].sort + '">'
                        + '<div class="quesAsk">'
                        + '<i class="flag flagW"></i> <span class="orderNum">' + data.questions[i].sort + '</span>. '
                        + quesTypeStr
                        + '  <span class="askText">' + data.questions[i].questionDescription + '(' + data.questions[i].score + '分)</span>'
                        + '</div>'
                        + '<ul class="allOption">'
                        + optionStr
                        + '</ul>'
                        + '</form>';
                }
                $(".questionNum ul").append(questionNumStr);
                $(".allQuestion").append(allQuestionStr);
                questionReviewMethod(data.questionReview);
            } else {
                $(".mask").show();
                $(".hintModHead").text("失败提示").addClass("hintModHeadFail");
                $(".modCont").text(data.error);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //捕获断网情况
            if (XMLHttpRequest.readyState == 0) {
                $(".mask").show();
                $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                $(".mask .modCont").text("网络已断开,请联系监考老师");
            }
        }
    })

    //题目回显
    function questionReviewMethod(review) {
        if (Boolean(review) && review.length != 0) {
            $.each(review, function (i, v) {
                $("#" + v.questionid + " input[value=" + v.answerid + "]").attr("checked", true);
                //答题卡回显
                var No = $("#" + v.questionid + " .orderNum").text();
                $(".questionNum li.answer" + No).addClass("answered");
            });
        }
    }

    //答题
    $(document).on("click", ".option input", function () {
        var checked = 1;
        var answerObj = {};
        //多选取消功能
        if ($(this).attr("type") == "checkbox" && $(this).is(":checked")) {
            checked = 1;
        } else if ($(this).attr("type") == "checkbox" && !$(this).is(":checked")) {
            checked = 0;
        }
        //直接标记已答
        var No = $(this).attr("name");
        $(".questionNum li." + No).addClass("answered");
        //多选如果都没有被选中,则取消标蓝
        if ($(this).attr("type") == "checkbox") {
            var noCheckFlag = true;
            $.each($(this).parents(".allOption").children("li"), function (i, v) {
                if ($(this).children("input").is(":checked")) {
                    noCheckFlag = false;
                }
            })
            if (noCheckFlag) {
                $(".questionNum li." + No).removeClass("answered");
            }
        }
        var questionid = $(this).parents(".singleQuestion").attr("id");
        var type = $(this).attr("quesType");
        var answers = '';
        if (type == 2) {
            var checkedOptions = $("#" + questionid + " input:checked");
            for (var i = 0; i < checkedOptions.length; i++) {
                var answer = $(checkedOptions[i]).val();
                if (i > 0) {
                    answers += ",";
                }
                answers += answer;
            }
        } else {
            answers = $(this).attr("value");
        }
        answerObj = {
            questionid: questionid,
            score: $(this).attr("score"),
            answerid: answers,
            type: type
        };
        answerArr.push(answerObj);
        $(this).attr("isChecked", "true");
    })

    sendAnswer();

    //发送答案函数
    function sendAnswer() {
        if (answerArr.length != 0) {
            $.ajax({
                url: 'saveQuestionAnswer.ajax?_dc=' + new Date().getTime(),
                type: 'post',
                data: answerArr[0],
                success: function (data) {
                    if (data.success) { // 成功则删除第一项
                        answerArr.shift();
                        sendAnswer();
                    } else {
                        if (data.skip) {
                            //localStorage.restAnswerArr = "undefined";
                            answerArr.splice(0, answerArr.length);
                            localStorage.clear();
                            $(".mask").show();
                            $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                            $(".mask .modCont").text(data.error);
                            setTimeout(function () {
                                window.location.href = "studentInfo.html"
                            }, 3000)
                        } else {
                            //失败则将此项放在队列末端
                            answerArr.push(answerArr.shift());
                            sendAnswer();
                            if (doSubmit == true) {
                                $(".mask").show();
                                $(".mask .hintModHead").text("失败提示").addClass("hintModHeadFail");
                                $(".mask .modCont").text("试卷提交失败!");
                            }
                        }
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //捕获断网情况
                    if (XMLHttpRequest.readyState == 0) {
                        //3秒后再发送
                        setTimeout(sendAnswer, 3000);
                    }
                }
            })
        } else {
            setTimeout(sendAnswer, 3000);
        }
    }

    //提交试卷
    $(".subPage").on("click", function () {
        $(".subPage").attr('disabled', true);
        //在提交试卷时答题出错,或是提交试卷失败,给出相应的提示.
        doSubmit = true;
        var submitTime = setInterval(function () {
            //检测队列是否为空
            if (answerArr.length == 0) {
                clearInterval(submitTime);
                $.ajax({
                    url: 'submitExamPaper.ajax?_dc=' + new Date().getTime(),
                    data: {},
                    success: function (data) {
                        if (data.success) {
                            $(".mask").show();
                            $(".hintModHead").text("考试结束,禁止答题").removeClass("hintModHeadFail");
                            $(".modCont").text("考试结束,即将跳转到考生信息页面");
                            //localStorage.restAnswerArr = "undefined";
                            setTimeout(function () {
                                window.location.href = "studentInfo.html"
                            }, 3000);
                        } else {
                            $(".subPage").attr('disabled', false);
                            $(".mask").show();
                            $(".mask .hintModHead").text("考试结束,禁止答题").addClass("hintModHeadFail");
                            $(".mask .modCont").text(data.error);
                            if (data.skip) {
                                setTimeout(function () {
                                    window.location.href = "studentInfo.html"
                                }, 3000);
                            }
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        $(".subPage").attr('disabled', false);
                        //捕获断网情况
                        if (XMLHttpRequest.readyState == 0) {
                            $(".mask").show();
                            $(".mask .hintModHead").text("考试结束,禁止答题").addClass("hintModHeadFail");
                            $(".mask .modCont").text("网络已断开,请联系监考老师");
                        }
                    }
                })
            }
        }, 3000);
    })

//点击答题卡跳到相应题目位置
    $(document).on("click", ".questionNum li", function () {
        var scroll_offset = $("form[sort=" + $(this).text() + "]").offset(); //得到pos这个div层的offset，包含两个值，top和left
        $("body,html").animate({
            scrollTop: scroll_offset.top - 169 //让body的scrollTop等于pos的top，就实现了滚动
        });
    })

//测试localStorage
    if (!window.localStorage) {
        alert("浏览器不支持localstorage,断网后关闭页面将无法保存数据");
    } else {
        //主逻辑业务
        window.onbeforeunload = function (event) {
            console.log(JSON.stringify(answerArr));
            window.localStorage.restAnswerArr = JSON.stringify(answerArr);
            //alert("数据队列保存成功");
        }
    }
})