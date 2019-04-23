package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class AttendTemp extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	
	private String attend_date; // 
	private String course_name; // 
	private String clazz_name; // 
	private int user_sex; // 
	private int attend_type; // 1-迟到 2-早退 3-请假 4-旷课
	private int attend_count; // 
	
	public String getUser_sexDesc(){
		switch (user_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "男";
		}
	}
	
	public String getAttend_typeDesc(){
		switch (attend_type) {
		case 1:
			return "迟到";
		case 2:
			return "早退";
		case 3:
			return "请假";
		case 4:
			return "旷课";
		default:
			return "";
		}
	}

	public String getAttend_date() {
		return attend_date;
	}

	public void setAttend_date(String attend_date) {
		this.attend_date = attend_date;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getClazz_name() {
		return clazz_name;
	}

	public void setClazz_name(String clazz_name) {
		this.clazz_name = clazz_name;
	}

	public int getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(int user_sex) {
		this.user_sex = user_sex;
	}

	public int getAttend_type() {
		return attend_type;
	}

	public void setAttend_type(int attend_type) {
		this.attend_type = attend_type;
	}

	public int getAttend_count() {
		return attend_count;
	}

	public void setAttend_count(int attend_count) {
		this.attend_count = attend_count;
	}
	
	 
}
