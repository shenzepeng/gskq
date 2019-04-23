package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Config;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class ConfigDao extends BaseDao {

	public void addConfig(Config config){
		super.add(config);
	}

	public void delConfig(Integer config_id){
		super.del(Config.class, config_id);
	}

	public void delConfigs(String[] config_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <config_ids.length; i++) {
			sBuilder.append(config_ids[i]);
			if (i !=config_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Config WHERE config_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updateConfig(Config config){
		Config _config = (Config)super.get(Config.class, config.getConfig_id());
		if (!StringUtil.isEmptyString(config.getConfig_date1())) {
			_config.setConfig_date1(config.getConfig_date1());
		}
		if (!StringUtil.isEmptyString(config.getConfig_date2())) {
			_config.setConfig_date2(config.getConfig_date2());
		}
		super.update(_config);
	}

	@SuppressWarnings("rawtypes")
	public Config getConfig(Config config){
		Config _config=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Config WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (config.getConfig_id()!=null && config.getConfig_id()!=0) {
			sBuilder.append(" and config_id = ? ");
			paramsList.add(config.getConfig_id());
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
			_config = (Config)list.get(0);
		}

		return _config;
	}

	@SuppressWarnings("rawtypes")
	public List<Config>  listConfigs(Config config){
		List<Config> configs = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Config WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (config.getConfig_id()!=null && config.getConfig_id()!=0) {
			sBuilder.append(" and config_id = ? ");
			paramsList.add(config.getConfig_id());
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by config_id asc ");

		List list = null;
		if (config.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, config.getStart(), config.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			configs = new ArrayList<Config>();
			for (Object object : list) {
				configs.add((Config)object);
			}
		}

		return configs;
	}

	public int  listConfigsCount(Config config){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Config WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (config.getConfig_id()!=null && config.getConfig_id()!=0) {
			sBuilder.append(" and config_id = ? ");
			paramsList.add(config.getConfig_id());
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
