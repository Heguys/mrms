package com.hxminco.mrms.comm.utils;

public interface ExamConstants {
	/**
	 * 启动时间
	 */
	final long t = System.currentTimeMillis();

	/**
	 * 超级用户账户
	 */
	String SUPER_ACCOUNT = "admin";

	/**
	 * 富文本
	 */
	String CONTENT_TYPE_HTML = "html";
	/**
	 * 附件
	 */
	String CONTENT_TYPE_FILE = "file";
	/**
	 * 内容类型
	 */
	String[] CONTENT_TYPES = new String[] { CONTENT_TYPE_HTML, CONTENT_TYPE_FILE };

	/**
	 * 取证类型 : 初领
	 */
	int ASSESSMENT_TYPE_FIRST = 1;
	/**
	 * 取证类型 : 复审
	 */
	int ASSESSMENT_TYPE_AGAIN = 2;
	/**
	 * 取证类型 : 换证
	 */
	int ASSESSMENT_TYPE_CHANGE = 3;

	/**
	 * 取证类型
	 */
	int[] ASSESSMENT_TYPES = new int[] { ASSESSMENT_TYPE_FIRST, ASSESSMENT_TYPE_AGAIN, ASSESSMENT_TYPE_CHANGE };

	/**
	 * 题型：单选题
	 */
	int QUESTION_TYPE_CHOICE_SINGLE = 1;

	/**
	 * 题型：多选题
	 */
	int QUESTION_TYPE_CHOICE_MULTI = 2;

	/**
	 * 题型：判断题
	 */
	int QUESTION_TYPE_JUDGE = 3;

	/**
	 * 试题来源：总局
	 */
	int QUESTION_SOURCE_CENTRAL = 0;

	/**
	 * 试题来源：地方
	 */
	int QUESTION_SOURCE_LOCAL = 1;

	/**
	 * 考试状态：等待开始
	 */
	String EXAM_STATUS_TODO = "1";

	/**
	 * 考试状态：正在考试
	 */
	String EXAM_STATUS_DOING = "2";

	/**
	 * 考试状态：已完成
	 */
	String EXAM_STATUS_COMPLETED = "3";

	/**
	 * 学生整场考试状态：等待开始
	 */
	String STUDENT_EXAM_TODO = "1";

	/**
	 * 学生整场考试状态：正在考试
	 */
	String STUDENT_EXAM_DOING = "2";

	/**
	 * 学生整场考试状态：已完成
	 */
	String STUDENT_EXAM_COMPLETED = "3";

	/**
	 * 学生对象存在session中的key
	 */
	String USER_SESSION_KEY = "currentUser";

	/**
	 * 学生这次考试是补考
	 */
	String STUDENT_MAKEUP_YES = "1";

	/**
	 * 学生这次考试不是补考
	 */
	String STUDENT_MAKEUP_NO = "0";

	/**
	 * 本场考试为补考
	 */
	String EXAM_MAKEUP_YES = "1";

	/**
	 * 本场考试不是补考
	 */
	String EXAM_MAKEUP_NO = "0";

	/**
	 * 考试分数及格
	 */
	String EXAM_PASSED_YES = "1";

	/**
	 * 考试分数不及格
	 */
	String EXAM_PASSED_NO = "0";

}
