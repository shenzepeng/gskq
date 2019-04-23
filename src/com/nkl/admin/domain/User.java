package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class User extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	private Integer user_id; // 
	private String user_name; // 
	private String user_pass; // 
	private String user_no; // 
	private String real_name; // 
	private Integer user_sex; // 1：男  2：女
	private Dept dept; // 
	private Integer user_type; // 1:员工 2:管理员

	private int dept_id; // 
	private String dept_name; // 
	private String ids;
	private String random;

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
	
	public String getUser_typeDesc(){
		switch (user_type) {
		case 1:
			return "员工";
		case 2:
			return "管理员";
		default:
			return "";
		}
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUser_pass() {
		return user_pass;
	}

	public String getReal_name() {
		return real_name;
	}

	public Integer getUser_type() {
		return user_type;
	}

	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public int getDept_id() {
		return dept_id;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}

}
