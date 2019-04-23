package com.nkl.admin.domain;

import java.util.Date;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class Leave extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer leave_id; // 
	private User user; // 
	private Date leave_date; // 
	private Date leave_date1; // 
	private Integer leave_lesson1; //1:上午 2:下午 
	private Date leave_date2; // 
	private Integer leave_lesson2; //1:上午 2:下午 
	private Integer leave_type; //1:年假 2:病假 3:事假
	private String leave_reason;
	private Integer leave_flag; // 1:待审核 2:审核通过 3:审核未通过

	private int user_id; // 
	private String user_no; // 
	private String real_name; // 
	private Integer user_sex; // 
	private int dept_id; // 
	private String dept_name; // 
	
	private String ids;
	private String random;
	
	public String getLeave_dateDesc(){
		if (leave_date!=null) {
			return DateUtil.dateToDateString(leave_date);
		}else {
			return null;
		}
	}
	
	public String getLeave_date1Desc(){
		if (leave_date1!=null) {
			return DateUtil.dateToDateString(leave_date1);
		}else {
			return null;
		}
	}
	
	public String getLeave_date2Desc(){
		if (leave_date2!=null) {
			return DateUtil.dateToDateString(leave_date2);
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
	
	public String getLeave_lesson1Desc(){
		switch (leave_lesson1) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getLeave_lesson2Desc(){
		switch (leave_lesson2) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getLeave_typeDesc(){
		switch (leave_type) {
		case 1:
			return "年假";
		case 2:
			return "病假";
		case 3:
			return "事假";
		default:
			return "";
		}
	}
	
	public String getLeave_flagDesc(){
		switch (leave_flag) {
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

	public Integer getLeave_id() {
		return leave_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public Date getLeave_date() {
		return leave_date;
	}

	public Integer getLeave_flag() {
		return leave_flag;
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

	public void setLeave_id(Integer leave_id) {
		this.leave_id = leave_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setLeave_date(Date leave_date) {
		this.leave_date = leave_date;
	}

	public void setLeave_reason(String leave_reason) {
		this.leave_reason = leave_reason;
	}

	public void setLeave_flag(Integer leave_flag) {
		this.leave_flag = leave_flag;
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

	public Date getLeave_date1() {
		return leave_date1;
	}

	public Integer getLeave_lesson1() {
		return leave_lesson1;
	}

	public Date getLeave_date2() {
		return leave_date2;
	}

	public Integer getLeave_lesson2() {
		return leave_lesson2;
	}

	public Integer getLeave_type() {
		return leave_type;
	}

	public String getLeave_reason() {
		return leave_reason;
	}

	public void setLeave_date1(Date leave_date1) {
		this.leave_date1 = leave_date1;
	}

	public void setLeave_lesson1(Integer leave_lesson1) {
		this.leave_lesson1 = leave_lesson1;
	}

	public void setLeave_date2(Date leave_date2) {
		this.leave_date2 = leave_date2;
	}

	public void setLeave_lesson2(Integer leave_lesson2) {
		this.leave_lesson2 = leave_lesson2;
	}

	public void setLeave_type(Integer leave_type) {
		this.leave_type = leave_type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
