package com.hxminco.mrms.ioc.s;

import com.hxminco.mrms.comm.model.ExamQuestion;

import java.util.List;
import java.util.Map;

/**
 * Created by Employee on 2017/7/26.
 */
public interface QuestionService {
    Map<Integer,List<ExamQuestion>> getQuestionMap(String trainPlanUid);

}
