package com.nkl.admin.domain;

import java.util.Date;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class Fill extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer fill_id; // 
	private User user; // 
	private Date fill_date; // 
	private Integer fill_lesson; //1:上午 2:下午 
	private String fill_reason;
	private Integer fill_flag; // 1:待审核 2:审核通过 3:审核未通过

	private int user_id; // 
	private String user_no; // 
	private String real_name; // 
	private Integer user_sex; // 
	private int dept_id; // 
	private String dept_name; // 
	
	private String ids;
	private String random;
	
	public String getFill_dateDesc(){
		if (fill_date!=null) {
			return DateUtil.dateToDateString(fill_date);
		}else {
			return null;
		}
	}
	
	public String getUser_sexDesc(){
		switch (user_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "";
		}
	}
	
	public String getFill_lessonDesc(){
		switch (fill_lesson) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getFill_flagDesc(){
		switch (fill_flag) {
		case 1:
			return "待审核";
		case 2:
			return "审核通过";
		case 3:
			return "审核未通过"; 
		default:
			return "";
		}
	}

	public Integer getFill_id() {
		return fill_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public Date getFill_date() {
		return fill_date;
	}

	public Integer getFill_lesson() {
		return fill_lesson;
	}

	public String getFill_reason() {
		return fill_reason;
	}

	public Integer getFill_flag() {
		return fill_flag;
	}

	public String getUser_no() {
		return user_no;
	}

	public String getReal_name() {
		return real_name;
	}

	public Integer getUser_sex() {
		return user_sex;
	}

	public int getDept_id() {
		return dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public String getIds() {
		return ids;
	}

	public String getRandom() {
		return random;
	}

	public void setFill_id(Integer fill_id) {
		this.fill_id = fill_id;
	}

	public void setFill_date(Date fill_date) {
		this.fill_date = fill_date;
	}

	public void setFill_lesson(Integer fill_lesson) {
		this.fill_lesson = fill_lesson;
	}

	public void setFill_reason(String fill_reason) {
		this.fill_reason = fill_reason;
	}

	public void setFill_flag(Integer fill_flag) {
		this.fill_flag = fill_flag;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
