package com.nkl.admin.domain;

import java.util.Date;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class Attend extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer attend_id; // 
	private User user; // 
	private Date attend_time; // 
	private Date attend_date; // 
	private Integer attend_lesson; //1:上午 2:下午 
	private Integer attend_type; // 1:未签到 2:已签到 3:迟签到 4:请假 5:离岗 6:早退

	private int user_id; // 
	private Integer attend_week;//签到星期
	private String attend_date1; // 
	private String attend_date2; // 
	private Integer attend_type1; //上午签到情况
	private Integer attend_type2; //下班签到情况
	private String user_no; // 
	private String real_name; // 
	private Integer user_sex; // 
	private int dept_id; // 
	private String dept_name; // 
	private String attend_month; // 
	
	private double attend_type1_days; // 未签到天数
	private double attend_type2_days; // 已签到天数
	private double attend_type3_days; // 迟签到天数
	private double attend_type4_days; // 请假天数
	private double attend_type5_days; // 离岗天数
	private double attend_type6_days; // 早退天数
	
	private String ids;
	private String random;
	
	public String getAttend_timeDesc(){
		if (attend_time!=null) {
			return DateUtil.dateToDateString(attend_time);
		}else {
			return null;
		}
	}
	
	public String getAttend_dateDesc(){
		if (attend_date!=null) {
			return DateUtil.dateToDateString(attend_date);
		}else {
			return null;
		}
	}
	
	public String getUser_sexDesc(){
		switch (user_sex==null?0:user_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "";
		}
	}
	
	public String getAttend_lessonDesc(){
		switch (attend_lesson==null?0:attend_lesson) {
		case 1:
			return "上班";
		case 2:
			return "下班";
		default:
			return "";
		}
	}
	
	public String getAttend_typeDesc(){
		switch (attend_type==null?0:attend_type) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		case 6:
			return "早退";
		default:
			return "";
		}
	}
	
	public String getAttend_type1Desc(){
		switch (attend_type1==null?0:attend_type1) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		default:
			return "";
		}
	}
	
	public String getAttend_type2Desc(){
		switch (attend_type2==null?0:attend_type2) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		case 6:
			return "早退";
		default:
			return "";
		}
	}
	
	public String getAttend_type1Color(){
		switch (attend_type1==null?0:attend_type1) {
		case 1:
			return "red";
		case 2:
			return "green";
		case 3:
			return "orange";
		case 4:
			return "silver";
		case 5:
			return "silver";
		default:
			return "";
		}
	}
	
	public String getAttend_type2Color(){
		switch (attend_type2==null?0:attend_type2) {
		case 1:
			return "red";
		case 2:
			return "green";
		case 3:
			return "orange";
		case 4:
			return "silver";
		case 5:
			return "silver";
		case 6:
			return "orange";
		default:
			return "";
		}
	}
	
	public String getAttend_weekDesc() {
		String[] weeks = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		if (getAttend_date()!=null) {
			return weeks[DateUtil.getWeek(getAttend_date())];
		}
		return "";
	}
	
	public Integer getAttend_id() {
		return attend_id;
	}
	public void setAttend_id(Integer attend_id) {
		this.attend_id = attend_id;
	}
	public Integer getAttend_lesson() {
		return attend_lesson;
	}
	public void setAttend_lesson(Integer attend_lesson) {
		this.attend_lesson = attend_lesson;
	}
	public Integer getAttend_type() {
		return attend_type;
	}
	public void setAttend_type(Integer attend_type) {
		this.attend_type = attend_type;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public Integer getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getAttend_date1() {
		return attend_date1;
	}

	public String getAttend_date2() {
		return attend_date2;
	}

	public void setAttend_date1(String attend_date1) {
		this.attend_date1 = attend_date1;
	}

	public void setAttend_date2(String attend_date2) {
		this.attend_date2 = attend_date2;
	}

	public Integer getAttend_week() {
		return attend_week;
	}

	public void setAttend_week(Integer attend_week) {
		this.attend_week = attend_week;
	}

	public Integer getAttend_type1() {
		return attend_type1;
	}

	public Integer getAttend_type2() {
		return attend_type2;
	}

	public void setAttend_type1(Integer attend_type1) {
		this.attend_type1 = attend_type1;
	}

	public void setAttend_type2(Integer attend_type2) {
		this.attend_type2 = attend_type2;
	}

	public double getAttend_type1_days() {
		return attend_type1_days;
	}

	public double getAttend_type2_days() {
		return attend_type2_days;
	}

	public double getAttend_type3_days() {
		return attend_type3_days;
	}

	public double getAttend_type4_days() {
		return attend_type4_days;
	}

	public double getAttend_type5_days() {
		return attend_type5_days;
	}

	public void setAttend_type1_days(double attend_type1_days) {
		this.attend_type1_days = attend_type1_days;
	}

	public void setAttend_type2_days(double attend_type2_days) {
		this.attend_type2_days = attend_type2_days;
	}

	public void setAttend_type3_days(double attend_type3_days) {
		this.attend_type3_days = attend_type3_days;
	}

	public void setAttend_type4_days(double attend_type4_days) {
		this.attend_type4_days = attend_type4_days;
	}

	public void setAttend_type5_days(double attend_type5_days) {
		this.attend_type5_days = attend_type5_days;
	}

	public double getAttend_type6_days() {
		return attend_type6_days;
	}

	public void setAttend_type6_days(double attend_type6_days) {
		this.attend_type6_days = attend_type6_days;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAttend_time() {
		return attend_time;
	}

	public Date getAttend_date() {
		return attend_date;
	}

	public void setAttend_time(Date attend_time) {
		this.attend_time = attend_time;
	}

	public void setAttend_date(Date attend_date) {
		this.attend_date = attend_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAttend_month() {
		return attend_month;
	}

	public void setAttend_month(String attend_month) {
		this.attend_month = attend_month;
	} 
}
