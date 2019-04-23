package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.User;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class UserDao extends BaseDao {

	public void addUser(User user){
		super.add(user);
	}

	public void delUser(Integer user_id){
		super.del(User.class, user_id);
	}

	public void delUsers(String[] user_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <user_ids.length; i++) {
			sBuilder.append(user_ids[i]);
			if (i !=user_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM User WHERE user_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateUser(User user){
		User _user = (User)super.get(User.class, user.getUser_id());
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			_user.setUser_name(user.getUser_name());
		}
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			_user.setUser_pass(user.getUser_pass());
		}
		if (!StringUtil.isEmptyString(user.getUser_no())) {
			_user.setUser_no(user.getUser_no());
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			_user.setReal_name(user.getReal_name());
		}
		if (user.getUser_sex()!=null && user.getUser_sex()!=0) {
			_user.setUser_sex(user.getUser_sex());
		}
		if (user.getDept()!=null && user.getDept().getDept_id()!=null) {
			_user.setDept(user.getDept());
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			_user.setUser_type(user.getUser_type());
		}
		super.update(_user);
	}

	@SuppressWarnings("rawtypes")
	public User getUser(User user){
		User _user=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM User u left join fetch u.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = ? ");
			paramsList.add(user.getUser_id());
		}
		if (!StringUtil.isEmptyString(user.getUser_no())) {
			sBuilder.append(" and user_no ='" + user.getUser_no() +"' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and user_name ='" + user.getUser_name() +"' ");
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			sBuilder.append(" and user_type =" + user.getUser_type() +" ");
		}
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			sBuilder.append(" and user_pass ='" + user.getUser_pass() +"' ");
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
			_user = (User)list.get(0);
		}

		return _user;
	}

	@SuppressWarnings("rawtypes")
	public List<User>  listUsers(User user){
		List<User> users = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM User u left join fetch u.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = ? ");
			paramsList.add(user.getUser_id());
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and u.user_name like '%" + user.getUser_name() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_no())) {
			sBuilder.append(" and user_no like '%" + user.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + user.getReal_name() +"%' ");
		}
		if (user.getDept_id()!=0) {
			sBuilder.append(" and u.dept.dept_id =" + user.getDept_id() +" ");
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			sBuilder.append(" and user_type =" + user.getUser_type() +" ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by user_id asc ");

		List list = null;
		if (user.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, user.getStart(), user.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			users = new ArrayList<User>();
			for (Object object : list) {
				users.add((User)object);
			}
		}

		return users;
	}

	public int  listUsersCount(User user){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM User u WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (user.getUser_id()!=null && user.getUser_id()!=0) {
			sBuilder.append(" and user_id = ? ");
			paramsList.add(user.getUser_id());
		}
		if (!StringUtil.isEmptyString(user.getUser_name())) {
			sBuilder.append(" and u.user_name like '%" + user.getUser_name() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getUser_no())) {
			sBuilder.append(" and user_no like '%" + user.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(user.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + user.getReal_name() +"%' ");
		}
		if (user.getDept_id()!=0) {
			sBuilder.append(" and u.dept.dept_id =" + user.getDept_id() +" ");
		}
		if (user.getUser_type()!=null && user.getUser_type()!=0) {
			sBuilder.append(" and user_type =" + user.getUser_type() +" ");
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
