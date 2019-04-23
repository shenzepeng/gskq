package com.nkl.admin.domain;

import com.nkl.common.domain.BaseDomain;
import com.nkl.common.util.DateUtil;

public class Holiday extends BaseDomain {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1770185824735782580L;
	private Integer holiday_id; // 
	private String holiday_date; // 
	private String holiday_note; // 

	private String holiday_date1; //
	private String holiday_date2; //
	private String ids;

	public String getHoliday_week() {
		String[] weeks = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		return weeks[DateUtil.getWeek(DateUtil.getDate(holiday_date))];
	}

	public Integer getHoliday_id() {
		return holiday_id;
	}

	public String getHoliday_date() {
		return holiday_date;
	}

	public String getHoliday_note() {
		return holiday_note;
	}

	public String getIds() {
		return ids;
	}

	public void setHoliday_id(Integer holiday_id) {
		this.holiday_id = holiday_id;
	}

	public void setHoliday_date(String holiday_date) {
		this.holiday_date = holiday_date;
	}

	public void setHoliday_note(String holiday_note) {
		this.holiday_note = holiday_note;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getHoliday_date1() {
		return holiday_date1;
	}

	public String getHoliday_date2() {
		return holiday_date2;
	}

	public void setHoliday_date1(String holiday_date1) {
		this.holiday_date1 = holiday_date1;
	}

	public void setHoliday_date2(String holiday_date2) {
		this.holiday_date2 = holiday_date2;
	}

	 
}
