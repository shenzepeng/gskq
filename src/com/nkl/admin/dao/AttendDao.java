package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;

import com.nkl.admin.domain.Attend;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.DateUtil;
import com.nkl.common.util.StringUtil;

public class AttendDao extends BaseDao {

	public void addAttend(Attend attend){
		super.add(attend);
	}

	public int addAttendBatch(){
		String sql = "INSERT INTO attend(user_id,attend_date,attend_lesson,attend_type) ";
		sql += " select user_id,sysdate(),1,1 from `user` union select user_id,sysdate(),2,1 from `user` ";
		Object[] params = null;
		return super.executeUpdateSql(sql, params);
	}
	
	public int addAttendBatch(String attend_date,int attend_type){
		String sql = "INSERT INTO attend(user_id,attend_date,attend_lesson,attend_type) ";
		sql += " select user_id,'"+attend_date+"',1,"+attend_type+" from `user` union select user_id,'"+attend_date+"',2,"+attend_type+" from `user` ";
		Object[] params = null;
		return super.executeUpdateSql(sql, params);
	}
	
	public int addAttendBatch(String attend_date,int user_id, int attend_type){
		String sql = "INSERT INTO attend(user_id,attend_date,attend_lesson,attend_type) ";
		sql += " select "+user_id+",'"+attend_date+"',1,"+attend_type+" union select "+user_id+",'"+attend_date+"',2,"+attend_type+" ";
		Object[] params = null;
		return super.executeUpdateSql(sql, params);
	}
	
	public void delAttend(Integer attend_id){
		super.del(Attend.class, attend_id);
	}

	public void delAttends(String[] attend_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <attend_ids.length; i++) {
			sBuilder.append(attend_ids[i]);
			if (i !=attend_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Attend WHERE attend_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateAttend(Attend attend){
		Attend _attend = (Attend)super.get(Attend.class, attend.getAttend_id());
		super.update(_attend);
	}
	
	public int updateAttendType(Attend attend){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend SET attend_type = " + attend.getAttend_type() +" ");
		if (attend.getAttend_time()!=null) {
			sBuilder.append("  , attend_time = '" + attend.getAttend_timeDesc() +"' ");
		}
		sBuilder.append("where user_id = " + attend.getUser_id() +" ");
		sBuilder.append("  and date_format(attend_date,'%Y-%m-%d') = '" + attend.getAttend_dateDesc() +"' ");
		sBuilder.append("  and attend_lesson = " + attend.getAttend_lesson() +" ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchUser(Attend attend){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend SET attend_type = " + attend.getAttend_type() +" ");
		sBuilder.append("where user_id = " + attend.getUser_id() +" ");
		sBuilder.append("  and CONCAT(date_format(attend_date,'%Y%m%d'),attend_lesson) between '" + attend.getAttend_date1() +"' and '" + attend.getAttend_date2() +"' ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchLeave(){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,`leave` l SET a.attend_type = 4 ");
		sBuilder.append("where a.user_id=l.user_id ");
		sBuilder.append("  and l.leave_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.leave_date1,'%Y%m%d'),l.leave_lesson1) and CONCAT(date_format(l.leave_date2,'%Y%m%d'),l.leave_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and TO_DAYS(a.attend_date)= to_days(SYSDATE()) ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchLeave(String date){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,`leave` l SET a.attend_type = 4 ");
		sBuilder.append("where a.user_id=l.user_id ");
		sBuilder.append("  and l.leave_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.leave_date1,'%Y%m%d'),l.leave_lesson1) and CONCAT(date_format(l.leave_date2,'%Y%m%d'),l.leave_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and date_format(a.attend_date,'%Y-%m-%d') = '"+date+"' ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchLeave(String date,int user_id){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,`leave` l SET a.attend_type = 4 ");
		sBuilder.append("where a.user_id=l.user_id and a.user_id="+user_id);
		sBuilder.append("  and l.leave_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.leave_date1,'%Y%m%d'),l.leave_lesson1) and CONCAT(date_format(l.leave_date2,'%Y%m%d'),l.leave_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and date_format(a.attend_date,'%Y-%m-%d') = '"+date+"' ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchPost(){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,post l SET a.attend_type = 5 ");
		sBuilder.append("where a.user_id=l.user_id ");
		sBuilder.append("  and l.post_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.post_date1,'%Y%m%d'),l.post_lesson1) and CONCAT(date_format(l.post_date2,'%Y%m%d'),l.post_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and TO_DAYS(a.attend_date)= to_days(SYSDATE()) ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}

	public int updateAttendTypeBatchPost(String date,int user_id){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,post l SET a.attend_type = 5 ");
		sBuilder.append("where a.user_id=l.user_id and a.user_id="+user_id);
		sBuilder.append("  and l.post_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.post_date1,'%Y%m%d'),l.post_lesson1) and CONCAT(date_format(l.post_date2,'%Y%m%d'),l.post_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and date_format(a.attend_date,'%Y-%m-%d') = '"+date+"' ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}
	
	public int updateAttendTypeBatchPost(String date){
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("UPDATE attend a ,post l SET a.attend_type = 5 ");
		sBuilder.append("where a.user_id=l.user_id ");
		sBuilder.append("  and l.post_flag=2 ");
		sBuilder.append("  and CONCAT(date_format(a.attend_date,'%Y%m%d'),a.attend_lesson) between CONCAT(date_format(l.post_date1,'%Y%m%d'),l.post_lesson1) and CONCAT(date_format(l.post_date2,'%Y%m%d'),l.post_lesson2) ");
		sBuilder.append("  and a.attend_type=1 and date_format(a.attend_date,'%Y-%m-%d') = '"+date+"' ");

		Object[] params = null;
		return super.executeUpdateSql(sBuilder.toString(), params);
	}

	@SuppressWarnings("rawtypes")
	public Attend getAttend(Attend attend){
		Attend _attend=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Attend a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (attend.getAttend_id()!=null && attend.getAttend_id()!=0) {
			sBuilder.append(" and attend_id = ? ");
			paramsList.add(attend.getAttend_id());
		}
		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = ? ");
			paramsList.add(attend.getUser_id());
		}
		if (attend.getAttend_date()!=null) {
			sBuilder.append(" and a.attend_date = ? ");
			paramsList.add(attend.getAttend_date());
		}
		if (attend.getAttend_lesson()!=null && attend.getAttend_lesson()!=0) {
			sBuilder.append(" and a.attend_lesson = ? ");
			paramsList.add(attend.getAttend_lesson());
		}
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" and a.attend_type = ? ");
			paramsList.add(attend.getAttend_type());
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
			_attend = (Attend)list.get(0);
		}

		return _attend;
	}

	@SuppressWarnings("rawtypes")
	public List<Attend>  listAttends2(Attend attend){
		List<Attend> attends = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Attend a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (attend.getAttend_id()!=null && attend.getAttend_id()!=0) {
			sBuilder.append(" and attend_id = ? ");
			paramsList.add(attend.getAttend_id());
		}
		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = ? ");
			paramsList.add(attend.getUser_id());
		}
		if (!StringUtil.isEmptyString(attend.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + attend.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + attend.getReal_name() +"%' ");
		}
		if (attend.getAttend_date()!=null) {
			sBuilder.append(" and attend_date = ? ");
			paramsList.add(attend.getAttend_date());
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date1())) {
			sBuilder.append(" and attend_date >= ? ");
			paramsList.add(DateUtil.getDate(attend.getAttend_date1()));
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date2())) {
			sBuilder.append(" and attend_date <= ? ");
			paramsList.add(DateUtil.getDate(attend.getAttend_date2()));
		}
		if (attend.getAttend_lesson()!=null && attend.getAttend_lesson()!=0) {
			sBuilder.append(" and a.attend_lesson = ? ");
			paramsList.add(attend.getAttend_lesson());
		}
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" and a.attend_type = ? ");
			paramsList.add(attend.getAttend_type());
		}
		if (attend.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + attend.getDept_id() +" ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by a.attend_date desc,a.user.real_name,a.attend_id asc ");

		List list = null;
		if (attend.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, attend.getStart(), attend.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			attends = new ArrayList<Attend>();
			for (Object object : list) {
				attends.add((Attend)object);
			}
		}

		return attends;
	}

	public int  listAttendsCount2(Attend attend){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Attend a WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (attend.getAttend_id()!=null && attend.getAttend_id()!=0) {
			sBuilder.append(" and attend_id = ? ");
			paramsList.add(attend.getAttend_id());
		}
		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = ? ");
			paramsList.add(attend.getUser_id());
		}
		if (!StringUtil.isEmptyString(attend.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + attend.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + attend.getReal_name() +"%' ");
		}
		if (attend.getAttend_date()!=null) {
			sBuilder.append(" and attend_date = ? ");
			paramsList.add(attend.getAttend_date());
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date1())) {
			sBuilder.append(" and attend_date >= ? ");
			paramsList.add(DateUtil.getDate(attend.getAttend_date1()));
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date2())) {
			sBuilder.append(" and attend_date <= ? ");
			paramsList.add(DateUtil.getDate(attend.getAttend_date2()));
		}
		if (attend.getAttend_lesson()!=null && attend.getAttend_lesson()!=0) {
			sBuilder.append(" and a.attend_lesson = ? ");
			paramsList.add(attend.getAttend_lesson());
		}
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" and a.attend_type = ? ");
			paramsList.add(attend.getAttend_type());
		}
		if (attend.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + attend.getDept_id() +" ");
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

	
	@SuppressWarnings("rawtypes")
	public List<Attend>  listAttends(Attend attend){
		List<Attend> attends = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT * FROM (");
		sBuilder.append("SELECT a.attend_date,u.user_no,u.real_name,u.user_sex,c.dept_name, ");
		sBuilder.append("       sum(case when a.attend_lesson=1 then a.attend_type else 0 end) attend_type1, ");
		sBuilder.append("       sum(case when a.attend_lesson=2 then a.attend_type else 0 end) attend_type2 from attend a ");
		sBuilder.append("  join user u on u.user_id=a.user_id ");
		sBuilder.append("  join dept c on c.dept_id=u.dept_id WHERE 1=1");

		if (attend.getAttend_id()!=null && attend.getAttend_id()!=0) {
			sBuilder.append(" and a.attend_id = " + attend.getAttend_id() +" ");
		}
		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user_id = " + attend.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(attend.getUser_no())) {
			sBuilder.append(" and u.user_no like '%" + attend.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + attend.getReal_name() +"%' ");
		}
		if (attend.getAttend_date()!=null) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') ='" + attend.getAttend_dateDesc() +"' ");
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date1())) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') >='" + attend.getAttend_date1() +"' ");
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date2())) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') <='" + attend.getAttend_date2() +"' ");
		}
		if (attend.getAttend_lesson()!=null && attend.getAttend_lesson()!=0) {
			sBuilder.append(" and a.attend_lesson = " + attend.getAttend_lesson() +" ");
		}
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" and a.attend_type = " + attend.getAttend_type() +" ");
		}
		if (attend.getDept_id()!=0) {
			sBuilder.append(" and c.dept_id = " + attend.getDept_id() +" ");
		}
		
		sBuilder.append(" group by a.attend_date,u.user_no,u.real_name,u.user_sex,c.dept_name ");
		sBuilder.append(" order by a.attend_date desc,u.real_name asc) t");
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" WHERE 1=1");
			sBuilder.append(" and (t.attend_type1 = " + attend.getAttend_type() +" or ");
			sBuilder.append("      t.attend_type2 = " + attend.getAttend_type() +") ");
		}


		String[] scalars = {"attend_date","user_no","real_name","user_sex","dept_name","attend_type1","attend_type2"};
		Type[] types = {Hibernate.DATE,Hibernate.STRING,Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING,Hibernate.INTEGER,Hibernate.INTEGER};

		List list = null;
		if (attend.getStart()!=-1) {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), attend.getClass(), null, scalars, types, attend.getStart(), attend.getLimit());
		}else {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), attend.getClass(), null, scalars, types);
		}
		
		if (list != null && list.size() > 0) {
			attends = new ArrayList<Attend>();
			for (Object object : list) {
				attends.add((Attend)object);
			}
		}
		return attends;
	}

	@SuppressWarnings("rawtypes")
	public int  listAttendsCount(Attend attend){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) s_count from ( ");
		sBuilder.append("SELECT a.attend_date,u.user_no,u.real_name,u.user_sex,c.dept_name, ");
		sBuilder.append("       sum(case when a.attend_lesson=1 then a.attend_type else 0 end) attend_type1, ");
		sBuilder.append("       sum(case when a.attend_lesson=2 then a.attend_type else 0 end) attend_type2 from attend a ");
		sBuilder.append("  join user u on u.user_id=a.user_id ");
		sBuilder.append("  join dept c on c.dept_id=u.dept_id WHERE 1=1");

		if (attend.getAttend_id()!=null && attend.getAttend_id()!=0) {
			sBuilder.append(" and a.attend_id = " + attend.getAttend_id() +" ");
		}
		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user_id = " + attend.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(attend.getUser_no())) {
			sBuilder.append(" and u.user_no like '%" + attend.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + attend.getReal_name() +"%' ");
		}
		if (attend.getAttend_date()!=null) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') ='" + attend.getAttend_dateDesc() +"' ");
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date1())) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') >='" + attend.getAttend_date1() +"' ");
		}
		if (!StringUtil.isEmptyString(attend.getAttend_date2())) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m-%d') <='" + attend.getAttend_date2() +"' ");
		}
		if (attend.getAttend_lesson()!=null && attend.getAttend_lesson()!=0) {
			sBuilder.append(" and a.attend_lesson = " + attend.getAttend_lesson() +" ");
		}
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" and a.attend_type = " + attend.getAttend_type() +" ");
		}
		if (attend.getDept_id()!=0) {
			sBuilder.append(" and c.dept_id = " + attend.getDept_id() +" ");
		}
		sBuilder.append(" group by a.attend_date,u.user_no,u.real_name,u.user_sex,c.dept_name ) t");
		if (attend.getAttend_type()!=null && attend.getAttend_type()!=0) {
			sBuilder.append(" WHERE 1=1");
			sBuilder.append(" and (t.attend_type1 = " + attend.getAttend_type() +" or ");
			sBuilder.append("      t.attend_type2 = " + attend.getAttend_type() +") ");
		}
		
		List list = super.executeQueryMapSql(sBuilder.toString(), null);
		if (list!=null) {
			Map map = (Map) list.get(0);
			sum = Integer.parseInt(map.get("s_count").toString());
		}
		return sum;
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<Attend>  listAttendTemps(Attend attend){
		List<Attend> attends = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT * FROM (");
		sBuilder.append("SELECT u.user_no,u.real_name,u.user_sex,c.dept_name, ");
		sBuilder.append("       sum(case when a.attend_type=1 then 0.5 else 0 end) attend_type1_days, ");
		sBuilder.append("       sum(case when a.attend_type=2 then 0.5 else 0 end) attend_type2_days, ");
		sBuilder.append("       sum(case when a.attend_type=3 then 0.5 else 0 end) attend_type3_days, ");
		sBuilder.append("       sum(case when a.attend_type=4 then 0.5 else 0 end) attend_type4_days, ");
		sBuilder.append("       sum(case when a.attend_type=5 then 0.5 else 0 end) attend_type5_days, ");
		sBuilder.append("       sum(case when a.attend_type=6 then 0.5 else 0 end) attend_type6_days from attend a ");
		sBuilder.append("  join user u on u.user_id=a.user_id ");
		sBuilder.append("  join dept c on c.dept_id=u.dept_id WHERE 1=1");

		if (attend.getUser_id()!=0) {
			sBuilder.append(" and a.user_id = " + attend.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(attend.getUser_no())) {
			sBuilder.append(" and u.user_no like '%" + attend.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getReal_name())) {
			sBuilder.append(" and u.real_name like '%" + attend.getReal_name() +"%' ");
		}
		if (!StringUtil.isEmptyString(attend.getAttend_month())) {
			sBuilder.append(" and date_format(a.attend_date,'%Y-%m') ='" + attend.getAttend_month() +"' ");
		}
		if (attend.getDept_id()!=0) {
			sBuilder.append(" and c.dept_id = " + attend.getDept_id() +" ");
		}
		
		sBuilder.append(" group by u.user_no,u.real_name,u.user_sex,c.dept_name ");
		sBuilder.append(" order by u.real_name asc) t");

		String[] scalars = {"user_no","real_name","user_sex","dept_name","attend_type1_days","attend_type2_days","attend_type3_days","attend_type4_days","attend_type5_days","attend_type6_days"};
		Type[] types = {Hibernate.STRING,Hibernate.STRING,Hibernate.INTEGER,Hibernate.STRING,Hibernate.DOUBLE,Hibernate.DOUBLE,Hibernate.DOUBLE,Hibernate.DOUBLE,Hibernate.DOUBLE,Hibernate.DOUBLE};

		List list = null;
		if (attend.getStart()!=-1) {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), attend.getClass(), null, scalars, types, attend.getStart(), attend.getLimit());
		}else {
			list = super.executeQueryJavaBeanSql(sBuilder.toString(), attend.getClass(), null, scalars, types);
		}
		
		if (list != null && list.size() > 0) {
			attends = new ArrayList<Attend>();
			for (Object object : list) {
				attends.add((Attend)object);
			}
		}
		return attends;
	}
}
