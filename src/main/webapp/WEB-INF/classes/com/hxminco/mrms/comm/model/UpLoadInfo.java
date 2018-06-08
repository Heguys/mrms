package com.hxminco.mrms.comm.model;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.QbExamQuestions;
import com.hxminco.mrms.comm.entry.RsStudentInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Employee on 2017/7/25.
 */
@XmlRootElement
public class UpLoadInfo {
    private List<QbExamInfo> lstQbExamInfos;
    private List<QbExamQuestions> lstQbExamQuestions;
    private List<RsStudentInfo> lstRsStudentInfos;

    public List<QbExamInfo> getLstQbExamInfos() {
        return lstQbExamInfos;
    }

    public void setLstQbExamInfos(List<QbExamInfo> lstQbExamInfos) {
        this.lstQbExamInfos = lstQbExamInfos;
    }

    public List<QbExamQuestions> getLstQbExamQuestions() {
        return lstQbExamQuestions;
    }

    public void setLstQbExamQuestions(List<QbExamQuestions> lstQbExamQuestions) {
        this.lstQbExamQuestions = lstQbExamQuestions;
    }

    public List<RsStudentInfo> getLstRsStudentInfos() {
        return lstRsStudentInfos;
    }

    public void setLstRsStudentInfos(List<RsStudentInfo> lstRsStudentInfos) {
        this.lstRsStudentInfos = lstRsStudentInfos;
    }
}
