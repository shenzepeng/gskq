package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Dept;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class DeptDao extends BaseDao {

	public void addDept(Dept dept){
		super.add(dept);
	}

	public void delDept(Integer dept_id){
		super.del(Dept.class, dept_id);
	}

	public void delDepts(String[] dept_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <dept_ids.length; i++) {
			sBuilder.append(dept_ids[i]);
			if (i !=dept_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Dept WHERE dept_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateDept(Dept dept){
		Dept _dept = (Dept)super.get(Dept.class, dept.getDept_id());
		if (!StringUtil.isEmptyString(dept.getDept_name())) {
			_dept.setDept_name(dept.getDept_name());
		}
		if (!StringUtil.isEmptyString(dept.getDept_note())) {
			_dept.setDept_note(dept.getDept_note());
		}else {
			_dept.setDept_note("");
		}
		super.update(_dept);
	}

	@SuppressWarnings("rawtypes")
	public Dept getDept(Dept dept){
		Dept _dept=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (dept.getDept_id()!=null && dept.getDept_id()!=0) {
			sBuilder.append(" and dept_id = ? ");
			paramsList.add(dept.getDept_id());
		}
		if (!StringUtil.isEmptyString(dept.getDept_name())) {
			sBuilder.append(" and dept_name = '" + dept.getDept_name() +"' ");
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
			_dept = (Dept)list.get(0);
		}

		return _dept;
	}

	@SuppressWarnings("rawtypes")
	public List<Dept>  listDepts(Dept dept){
		List<Dept> depts = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (dept.getDept_id()!=null && dept.getDept_id()!=0) {
			sBuilder.append(" and dept_id = ? ");
			paramsList.add(dept.getDept_id());
		}
		if (!StringUtil.isEmptyString(dept.getDept_name())) {
			sBuilder.append(" and dept_name like '%" + dept.getDept_name() +"%' ");
		}
		
		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by dept_id asc ");

		List list = null;
		if (dept.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, dept.getStart(), dept.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			depts = new ArrayList<Dept>();
			for (Object object : list) {
				depts.add((Dept)object);
			}
		}

		return depts;
	}

	public int  listDeptsCount(Dept dept){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (dept.getDept_id()!=null && dept.getDept_id()!=0) {
			sBuilder.append(" and dept_id = ? ");
			paramsList.add(dept.getDept_id());
		}
		if (!StringUtil.isEmptyString(dept.getDept_name())) {
			sBuilder.append(" and dept_name like '%" + dept.getDept_name() +"%' ");
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
