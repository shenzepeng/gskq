package com.nkl.admin.manager;

import java.util.List;

import com.nkl.admin.dao.UserDao;
import com.nkl.admin.domain.User;
import com.nkl.common.util.Md5;
import com.nkl.common.util.StringUtil;

public class LoginManager {

	UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @Title: listManagers
	 * @Description: 查询用户集合
	 * @param manager
	 * @return List<Picnews>
	 */
	public List<User> listUsers(User manager){
		List<User> managers = userDao.listUsers(manager);
		return managers;
	}
	
	/**
	 * @Title: getManager
	 * @Description: 查询用户
	 * @param manager
	 * @return Manager
	 */
	public User getUser(User manager){
		if (!StringUtil.isEmptyString(manager.getUser_pass())) {
			manager.setUser_pass(Md5.makeMd5(manager.getUser_pass()));
		}
		User _manager = userDao.getUser(manager);
		return _manager;
	}
	
	/**
	 * @Title: addManager
	 * @Description: 用户注册
	 * @return void
	 */
	public void addUser(User manager) {
		if (!StringUtil.isEmptyString(manager.getUser_pass())) {
			manager.setUser_pass(Md5.makeMd5(manager.getUser_pass()));
		}
		userDao.addUser(manager);
		
	}  
	
}
