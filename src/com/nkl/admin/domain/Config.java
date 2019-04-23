package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;

public class Config extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1770185824735782580L;
	private Integer config_id; // 
	private String config_date1; // 
	private String config_date2; // 

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getConfig_id() {
		return config_id;
	}

	public String getConfig_date1() {
		return config_date1;
	}

	public String getConfig_date2() {
		return config_date2;
	}

	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}

	public void setConfig_date1(String config_date1) {
		this.config_date1 = config_date1;
	}

	public void setConfig_date2(String config_date2) {
		this.config_date2 = config_date2;
	}

}
