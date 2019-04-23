package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Holiday;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class HolidayDao extends BaseDao {

	public void addHoliday(Holiday holiday){
		super.add(holiday);
	}

	public void delHoliday(Integer holiday_id){
		super.del(Holiday.class, holiday_id);
	}

	public void delHolidays(String[] holiday_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <holiday_ids.length; i++) {
			sBuilder.append(holiday_ids[i]);
			if (i !=holiday_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Holiday WHERE holiday_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateHoliday(Holiday holiday){
		Holiday _holiday = (Holiday)super.get(Holiday.class, holiday.getHoliday_id());
		if (!StringUtil.isEmptyString(holiday.getHoliday_note())) {
			_holiday.setHoliday_note(holiday.getHoliday_note());
		}
		super.update(_holiday);
	}

	@SuppressWarnings("rawtypes")
	public Holiday getHoliday(Holiday holiday){
		Holiday _holiday=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Holiday WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (holiday.getHoliday_id()!=null && holiday.getHoliday_id()!=0) {
			sBuilder.append(" and holiday_id = ? ");
			paramsList.add(holiday.getHoliday_id());
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_date())) {
			sBuilder.append(" and holiday_date = '" + holiday.getHoliday_date() +"' ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		List list = super.executeQueryHql(sBuilder.toString(), params);
		if (list != null && list.size() > 0) {
			_holiday = (Holiday)list.get(0);
		}

		return _holiday;
	}

	@SuppressWarnings("rawtypes")
	public List<Holiday>  listHolidays(Holiday holiday){
		List<Holiday> holidays = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Holiday WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (holiday.getHoliday_id()!=null && holiday.getHoliday_id()!=0) {
			sBuilder.append(" and holiday_id = ? ");
			paramsList.add(holiday.getHoliday_id());
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_date())) {
			sBuilder.append(" and holiday_date = '" + holiday.getHoliday_date() +"' ");
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_date1())) {
			if (!StringUtil.isEmptyString(holiday.getHoliday_date1())) {
				sBuilder.append(" and (holiday_date between '" + holiday.getHoliday_date1() +"' and '" + holiday.getHoliday_date2() +"' ) ");
			}
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_note())) {
			sBuilder.append(" and holiday_note like '%" + holiday.getHoliday_note() +"%' ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by holiday_date desc,holiday_id asc ");

		List list = null;
		if (holiday.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, holiday.getStart(), holiday.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			holidays = new ArrayList<Holiday>();
			for (Object object : list) {
				holidays.add((Holiday)object);
			}
		}

		return holidays;
	}

	public int  listHolidaysCount(Holiday holiday){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Holiday WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (holiday.getHoliday_id()!=null && holiday.getHoliday_id()!=0) {
			sBuilder.append(" and holiday_id = ? ");
			paramsList.add(holiday.getHoliday_id());
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_date())) {
			sBuilder.append(" and holiday_date = '" + holiday.getHoliday_date() +"' ");
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_date1())) {
			if (!StringUtil.isEmptyString(holiday.getHoliday_date1())) {
				sBuilder.append(" and (holiday_date between '" + holiday.getHoliday_date1() +"' and '" + holiday.getHoliday_date2() +"' ) ");
			}
		}
		if (!StringUtil.isEmptyString(holiday.getHoliday_note())) {
			sBuilder.append(" and holiday_note like '%" + holiday.getHoliday_note() +"%' ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		long count = (Long)super.executeQueryCountHql(sBuilder.toString(), params);
		sum = (int)count;
		return sum;
	}

}
