/*
package com.hxminco.mrms.ext.task;

import com.hxminco.mrms.comm.entry.QbExamInfo;
import com.hxminco.mrms.comm.entry.RsStudentInfo;
import com.hxminco.mrms.comm.model.QbExamInfoStorage;
import com.hxminco.mrms.ioc.s.QbExamInfoService;
import com.hxminco.mrms.ioc.s.RsStudentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AutoCommitExamTask {
	private static final Logger logger = LoggerFactory.getLogger(AutoCommitExamTask.class);

	@Autowired
	QbExamInfoService qbExamInfoService;
	@Autowired
	RsStudentInfoService studentInfoService;

	@Scheduled(fixedRate = 60000)
	public void work() {
		if (logger.isInfoEnabled()) {
			logger.info("work() - start");
		}
		int page = 0;
		int size = 5;
		Date now = new Date();
		Calendar startCalendar = Calendar.getInstance();
		while (true) {
			Map<String,Object> params = new HashMap<>();
			params.put("start",page*size);
			params.put("limit",size);
			params.put("status","2");
			List<QbExamInfo> examInfos = qbExamInfoService.selectByExamDoing4Page(params);
			if (examInfos.size() == 0) {
				break;
			}
			boolean haveCommitted = false;
			for (QbExamInfo examInfo : examInfos) {
				Date startDate = examInfo.getBegintime();
				if (startDate != null) {
					startCalendar.setTime(startDate);
					startCalendar.add(Calendar.MINUTE, examInfo.getDuration());
					if (startCalendar.getTime().before(now)) {
						// 应该交卷
						haveCommitted = true;
						RsStudentInfo student = studentInfoService.selectByExamCode(examInfo.getExamcode());
						qbExamInfoService.commitMockStudentExam(mockStudentExam.getExamId(), student);
					}
				}
			}
			if (!haveCommitted) {
				page++;
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("work() - end");
		}
	}
}
*/
