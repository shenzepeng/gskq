package com.nkl.common.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class BaseDao extends HibernateDaoSupport {

	/**
	 * 添加对象
	 * @param o
	 */
	public void add(Object o) {
		super.getHibernateTemplate().save(o);
	}
	/**
	 * 删除对象
	 * @param z
	 */
	public void del(Object o) {
		super.getHibernateTemplate().delete(o);
	}
	/**
	 * 根据ID删除对象
	 * @param z
	 * @param id
	 */
	@SuppressWarnings({ "rawtypes" })
	public void del(Class c,Integer id) {
		del(get(c, id));
	}

	/**
	 * 根据ID获取对象
	 * @param z
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object get(Class c,Integer id)
	{
		return super.getHibernateTemplate().get(c, id);
	}
	/**
	 * 更新对象
	 * @param o
	 */
	public void update(Object o) {
		super.getHibernateTemplate().update(o);
	}
	public void merge(Object o) {
		super.getHibernateTemplate().merge(o);
	}
	public void attachDirty(Object o) {
		super.getHibernateTemplate().saveOrUpdate(o);
	}
	
	/**
	 * 查询对象集合
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List executeQueryHql(String hql,Object[] params) {
		return super.getHibernateTemplate().find(hql,params);
	}
	/**
	 * 查询对象集合数量
	 * @param hql
	 * @param params
	 * @return
	 */
	public long executeQueryCountHql(String hql,Object[] params) {
		return (Long)super.getHibernateTemplate().find(hql,params).get(0);
	}
	/**
	 * 根据对象按条件查询
	 * @param z
	 * @param o
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findbyCra(final Class z,final Object o) {
		return super.getHibernateTemplate().executeFind(
			new HibernateCallback(){
				public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
					Criteria criteria=arg0.createCriteria(z);
					criteria.add(Example.create(o));
					// TODO 自动生成方法存根
					return criteria.list();
				}	
			}
		);
	}
	
	/**
	 * 设置参数
	 * @param query
	 * @param params
	 */
	public void setParam(Query query,Object[] params)
	{
		if(params!=null)
		{
			for (int i = 0 ; i < params.length ; i++)
			{
				query.setParameter( i, params[i]);
			}
		}
	}
	
	/**
	 * DML风格批量更新/删除操作
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int executeUpdateHql(final String hql, final Object[] params){
		//通过一个HibernateCallback对象来执行查询
		Object o = getHibernateTemplate().execute(new HibernateCallback(){
						//实现HibernateCallback接口必须实现的方法
						public Object doInHibernate(Session session)
							throws HibernateException, SQLException
						{
							//清理缓存，否则可能再次查询的数据还是之前缓存的数据
							session.flush();
							session.clear();
							//执行Hibernate查询
							Query query = session.createQuery(hql);
							//为hql语句传入参数
							setParam(query, params);
							return query.executeUpdate()+"";
						}
					});
		return Integer.parseInt(o+"");
	}
	
	/**
	 * 使用hql语句进行分页查询
	 * @param hql 需要查询的hql语句
	 * @param params 如果hql有多个个参数需要传入，params就是传入hql的参数数组
	 * @param offset 第一条记录索引
	 * @param pageSize 每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("rawtypes")
	public List findByPageHql(final String hql, final Object[] params,
		final int offset, final int pageSize){
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate()
			.executeFind(new HibernateCallback()
			{
				//实现HibernateCallback接口必须实现的方法
				public Object doInHibernate(Session session)
					throws HibernateException, SQLException
				{
					//执行Hibernate分页查询
					Query query = session.createQuery(hql);
					//为hql语句传入参数
					setParam(query, params);
					List result = query.setFirstResult(offset)
						.setMaxResults(pageSize)
						.list();
					return result;
				}
			});
		return list;
	}
	

	@SuppressWarnings("rawtypes")
	public List executeQueryJavaBeanSql(final String sql,final Class c,final Object[] params,final String[] scalars,final Type[] types)
	{
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate()
			.executeFind(new HibernateCallback()
			{
				//实现HibernateCallback接口必须实现的方法
				public Object doInHibernate(Session session)
					throws HibernateException, SQLException
				{
					//执行Hibernate分页查询
					SQLQuery query = session.createSQLQuery(sql);
					//为hql语句传入参数
					setParam(query, params);
					//添加标量
					if(scalars!=null)
					{
						for(int i=0;i<scalars.length;i++)
						{
							query.addScalar(scalars[i], types[i]);
						}
					}
					
					query.setResultTransformer(Transformers.aliasToBean(c));
					
					return query.list();
				}
			});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int executeUpdateSql(final String sql,final Object[] params)
	{
		//通过一个HibernateCallback对象来执行查询
		Object o = getHibernateTemplate()
			.execute(new HibernateCallback()
			{
				//实现HibernateCallback接口必须实现的方法
				public Object doInHibernate(Session session)
					throws HibernateException, SQLException
				{
					//执行Hibernate分页查询
					SQLQuery query = session.createSQLQuery(sql);
					//为hql语句传入参数
					setParam(query, params);
					 
					return query.executeUpdate()+"";
				}
			});
		return Integer.parseInt(o+"");
	}

	@SuppressWarnings("rawtypes")
	public List executeQueryJavaBeanSql(final String sql,final Class c,final Object[] params,final String[] scalars,final Type[] types,final int offset, final int pageSize)
	{
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate()
			.executeFind(new HibernateCallback()
			{
				//实现HibernateCallback接口必须实现的方法
				public Object doInHibernate(Session session)
					throws HibernateException, SQLException
				{
					//执行Hibernate分页查询
					SQLQuery query = session.createSQLQuery(sql);
					//为hql语句传入参数
					setParam(query, params);
					//添加标量
					if(scalars!=null)
					{
						for(int i=0;i<scalars.length;i++)
						{
							query.addScalar(scalars[i], types[i]);
						}
					}
					
					query.setResultTransformer(Transformers.aliasToBean(c));
					
					return query.setFirstResult(offset)
							.setMaxResults(pageSize)
							.list();
				}
			});
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List executeQueryMapSql(final String sql,final Object[] params)
	{
		//通过一个HibernateCallback对象来执行查询
		List list = getHibernateTemplate()
			.executeFind(new HibernateCallback()
			{
				//实现HibernateCallback接口必须实现的方法
				public Object doInHibernate(Session session)
					throws HibernateException, SQLException
				{
					//执行Hibernate分页查询
					SQLQuery query = session.createSQLQuery(sql);
					//为hql语句传入参数
					setParam(query, params);
					
					query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
					
					return query.list();
				}
			});
		return list;
	}
}
