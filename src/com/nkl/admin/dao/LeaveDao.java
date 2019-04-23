package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Leave;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class LeaveDao extends BaseDao {

	public void addLeave(Leave leave){
		super.add(leave);
	}

	public void delLeave(Integer leave_id){
		super.del(Leave.class, leave_id);
	}

	public void delLeaves(String[] leave_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <leave_ids.length; i++) {
			sBuilder.append(leave_ids[i]);
			if (i !=leave_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Leave WHERE leave_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateLeave(Leave leave){
		Leave _leave = (Leave)super.get(Leave.class, leave.getLeave_id());
		if (leave.getLeave_date1()!=null) {
			_leave.setLeave_date1(leave.getLeave_date1());
		}
		if (leave.getLeave_lesson1()!=null && leave.getLeave_lesson1()!=0) {
			_leave.setLeave_lesson1(leave.getLeave_lesson1());
		}
		if (leave.getLeave_date2()!=null) {
			_leave.setLeave_date2(leave.getLeave_date2());
		}
		if (leave.getLeave_lesson2()!=null && leave.getLeave_lesson2()!=0) {
			_leave.setLeave_lesson2(leave.getLeave_lesson2());
		}
		if (leave.getLeave_type()!=null && leave.getLeave_type()!=0) {
			_leave.setLeave_type(leave.getLeave_type());
		}
		if (!StringUtil.isEmptyString(leave.getLeave_reason())) {
			_leave.setLeave_reason(leave.getLeave_reason());
		}
		if (leave.getLeave_flag()!=null && leave.getLeave_flag()!=0) {
			_leave.setLeave_flag(leave.getLeave_flag());
		}
		super.update(_leave);
	}

	@SuppressWarnings("rawtypes")
	public Leave getLeave(Leave leave){
		Leave _leave=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Leave a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (leave.getLeave_id()!=null && leave.getLeave_id()!=0) {
			sBuilder.append(" and leave_id = ? ");
			paramsList.add(leave.getLeave_id());
		}
		if (leave.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + leave.getUser_id() +" ");
		}
		if (leave.getLeave_date1()!=null) {
			sBuilder.append(" and a.leave_date1 = ? ");
			paramsList.add(leave.getLeave_date1());
		}
		if (leave.getLeave_lesson1()!=null && leave.getLeave_lesson1()!=0) {
			sBuilder.append(" and a.leave_lesson1 = " + leave.getLeave_lesson1() +" ");
		}
		if (leave.getLeave_flag()!=null && leave.getLeave_flag()!=0) {
			sBuilder.append(" and a.leave_flag = " + leave.getLeave_flag() +" ");
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
			_leave = (Leave)list.get(0);
		}

		return _leave;
	}

	@SuppressWarnings("rawtypes")
	public List<Leave>  listLeaves(Leave leave){
		List<Leave> leaves = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Leave a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (leave.getLeave_id()!=null && leave.getLeave_id()!=0) {
			sBuilder.append(" and leave_id = ? ");
			paramsList.add(leave.getLeave_id());
		}
		if (leave.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + leave.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(leave.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + leave.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(leave.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + leave.getReal_name() +"%' ");
		}
		if (leave.getLeave_date1()!=null && leave.getLeave_date2()!=null) {
			sBuilder.append(" and ((a.leave_date1 between ? and ?) or ");
			sBuilder.append("      (a.leave_date2 between ? and ?)) ");
			paramsList.add(leave.getLeave_date1());
			paramsList.add(leave.getLeave_date2());
			paramsList.add(leave.getLeave_date1());
			paramsList.add(leave.getLeave_date2());
		}else {
			if (leave.getLeave_date1()!=null) {
				sBuilder.append(" and a.leave_date1 >= ? ");
				paramsList.add(leave.getLeave_date1());
			}
			if (leave.getLeave_date2()!=null) {
				sBuilder.append(" and a.leave_date1 <= ? ");
				paramsList.add(leave.getLeave_date2());
			}
		}
		
		if (leave.getLeave_type()!=null && leave.getLeave_type()!=0) {
			sBuilder.append(" and a.leave_type = " + leave.getLeave_type() +" ");
		}
		if (leave.getLeave_flag()!=null && leave.getLeave_flag()!=0) {
			sBuilder.append(" and a.leave_flag = " + leave.getLeave_flag() +" ");
		}
		if (leave.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + leave.getDept_id() +" ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by a.leave_date desc,a.user.real_name,a.leave_id asc");

		List list = null;
		if (leave.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, leave.getStart(), leave.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			leaves = new ArrayList<Leave>();
			for (Object object : list) {
				leaves.add((Leave)object);
			}
		}

		return leaves;
	}

	public int  listLeavesCount(Leave leave){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Leave a WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (leave.getLeave_id()!=null && leave.getLeave_id()!=0) {
			sBuilder.append(" and leave_id = ? ");
			paramsList.add(leave.getLeave_id());
		}
		if (leave.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + leave.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(leave.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + leave.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(leave.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + leave.getReal_name() +"%' ");
		}
		if (leave.getLeave_date1()!=null && leave.getLeave_date2()!=null) {
			sBuilder.append(" and ((a.leave_date1 between ? and ?) or ");
			sBuilder.append("      (a.leave_date2 between ? and ?)) ");
			paramsList.add(leave.getLeave_date1());
			paramsList.add(leave.getLeave_date2());
			paramsList.add(leave.getLeave_date1());
			paramsList.add(leave.getLeave_date2());
		}else {
			if (leave.getLeave_date1()!=null) {
				sBuilder.append(" and a.leave_date1 >= ? ");
				paramsList.add(leave.getLeave_date1());
			}
			if (leave.getLeave_date2()!=null) {
				sBuilder.append(" and a.leave_date1 <= ? ");
				paramsList.add(leave.getLeave_date2());
			}
		}
		
		if (leave.getLeave_type()!=null && leave.getLeave_type()!=0) {
			sBuilder.append(" and a.leave_type = " + leave.getLeave_type() +" ");
		}
		if (leave.getLeave_flag()!=null && leave.getLeave_flag()!=0) {
			sBuilder.append(" and a.leave_flag = " + leave.getLeave_flag() +" ");
		}
		if (leave.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + leave.getDept_id() +" ");
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
