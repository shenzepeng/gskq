package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Fill;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class FillDao extends BaseDao {

	public void addFill(Fill fill){
		super.add(fill);
	}

	public void delFill(Integer fill_id){
		super.del(Fill.class, fill_id);
	}

	public void delFills(String[] fill_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <fill_ids.length; i++) {
			sBuilder.append(fill_ids[i]);
			if (i !=fill_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Fill WHERE fill_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateFill(Fill fill){
		Fill _fill = (Fill)super.get(Fill.class, fill.getFill_id());
		if (fill.getFill_date()!=null) {
			_fill.setFill_date(fill.getFill_date());
		}
		if (fill.getFill_lesson()!=null && fill.getFill_lesson()!=0) {
			_fill.setFill_lesson(fill.getFill_lesson());
		}
		if (!StringUtil.isEmptyString(fill.getFill_reason())) {
			_fill.setFill_reason(fill.getFill_reason());
		}
		if (fill.getFill_flag()!=null && fill.getFill_flag()!=0) {
			_fill.setFill_flag(fill.getFill_flag());
		}
		super.update(_fill);
	}

	@SuppressWarnings("rawtypes")
	public Fill getFill(Fill fill){
		Fill _fill=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Fill a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (fill.getFill_id()!=null && fill.getFill_id()!=0) {
			sBuilder.append(" and fill_id = ? ");
			paramsList.add(fill.getFill_id());
		}
		if (fill.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + fill.getUser_id() +" ");
		}
		if (fill.getFill_date()!=null) {
			sBuilder.append(" and a.fill_date = ? ");
			paramsList.add(fill.getFill_date());
		}
		if (fill.getFill_lesson()!=null && fill.getFill_lesson()!=0) {
			sBuilder.append(" and a.fill_lesson = " + fill.getFill_lesson() +" ");
		}
		if (fill.getFill_flag()!=null && fill.getFill_flag()!=0) {
			sBuilder.append(" and a.fill_flag = " + fill.getFill_flag() +" ");
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
			_fill = (Fill)list.get(0);
		}

		return _fill;
	}

	@SuppressWarnings("rawtypes")
	public List<Fill>  listFills(Fill fill){
		List<Fill> fills = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Fill a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (fill.getFill_id()!=null && fill.getFill_id()!=0) {
			sBuilder.append(" and fill_id = ? ");
			paramsList.add(fill.getFill_id());
		}
		if (fill.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + fill.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(fill.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + fill.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(fill.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + fill.getReal_name() +"%' ");
		}
		if (fill.getFill_date()!=null) {
			sBuilder.append(" and a.fill_date = ? ");
			paramsList.add(fill.getFill_date());
		}
		if (fill.getFill_lesson()!=null && fill.getFill_lesson()!=0) {
			sBuilder.append(" and a.fill_lesson = " + fill.getFill_lesson() +" ");
		}
		if (fill.getFill_flag()!=null && fill.getFill_flag()!=0) {
			sBuilder.append(" and a.fill_flag = " + fill.getFill_flag() +" ");
		}
		if (fill.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + fill.getDept_id() +" ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by a.fill_date desc,a.user.real_name,a.fill_id asc) t");

		List list = null;
		if (fill.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, fill.getStart(), fill.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			fills = new ArrayList<Fill>();
			for (Object object : list) {
				fills.add((Fill)object);
			}
		}

		return fills;
	}

	public int  listFillsCount(Fill fill){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Fill a WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (fill.getFill_id()!=null && fill.getFill_id()!=0) {
			sBuilder.append(" and fill_id = ? ");
			paramsList.add(fill.getFill_id());
		}
		if (fill.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + fill.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(fill.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + fill.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(fill.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + fill.getReal_name() +"%' ");
		}
		if (fill.getFill_date()!=null) {
			sBuilder.append(" and a.fill_date = ? ");
			paramsList.add(fill.getFill_date());
		}
		if (fill.getFill_lesson()!=null && fill.getFill_lesson()!=0) {
			sBuilder.append(" and a.fill_lesson = " + fill.getFill_lesson() +" ");
		}
		if (fill.getFill_flag()!=null && fill.getFill_flag()!=0) {
			sBuilder.append(" and a.fill_flag = " + fill.getFill_flag() +" ");
		}
		if (fill.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + fill.getDept_id() +" ");
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
