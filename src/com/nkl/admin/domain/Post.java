package com.nkl.admin.domain;

import java.util.Date;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class Post extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer post_id; // 
	private User user; // 
	private Date post_date; // 
	private Date post_date1; // 
	private Integer post_lesson1; //1:上午 2:下午 
	private Date post_date2; // 
	private Integer post_lesson2; //1:上午 2:下午 
	private String post_reason;
	private Integer post_flag; // 1:待审核 2:审核通过 3:审核未通过

	private int user_id; // 
	private String user_no; // 
	private String real_name; // 
	private Integer user_sex; // 
	private int dept_id; // 
	private String dept_name; // 
	
	private String ids;
	private String random;
	
	public String getPost_dateDesc(){
		if (post_date!=null) {
			return DateUtil.dateToDateString(post_date);
		}else {
			return null;
		}
	}
	
	public String getPost_date1Desc(){
		if (post_date1!=null) {
			return DateUtil.dateToDateString(post_date1);
		}else {
			return null;
		}
	}
	
	public String getPost_date2Desc(){
		if (post_date2!=null) {
			return DateUtil.dateToDateString(post_date2);
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
	
	public String getPost_lesson1Desc(){
		switch (post_lesson1) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getPost_lesson2Desc(){
		switch (post_lesson2) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getPost_flagDesc(){
		switch (post_flag) {
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

	public Integer getPost_id() {
		return post_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public Date getPost_date() {
		return post_date;
	}

	public Integer getPost_flag() {
		return post_flag;
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

	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	public void setPost_reason(String post_reason) {
		this.post_reason = post_reason;
	}

	public void setPost_flag(Integer post_flag) {
		this.post_flag = post_flag;
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

	public Date getPost_date1() {
		return post_date1;
	}

	public Integer getPost_lesson1() {
		return post_lesson1;
	}

	public Date getPost_date2() {
		return post_date2;
	}

	public Integer getPost_lesson2() {
		return post_lesson2;
	}

	public String getPost_reason() {
		return post_reason;
	}

	public void setPost_date1(Date post_date1) {
		this.post_date1 = post_date1;
	}

	public void setPost_lesson1(Integer post_lesson1) {
		this.post_lesson1 = post_lesson1;
	}

	public void setPost_date2(Date post_date2) {
		this.post_date2 = post_date2;
	}

	public void setPost_lesson2(Integer post_lesson2) {
		this.post_lesson2 = post_lesson2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
