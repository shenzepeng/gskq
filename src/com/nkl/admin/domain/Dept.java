package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class Dept extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1770185824735782580L;
	private Integer dept_id; // 
	private String dept_name; // 
	private String dept_note; // 

	private String ids;

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_note() {
		return dept_note;
	}

	public void setDept_note(String dept_note) {
		this.dept_note = dept_note;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
