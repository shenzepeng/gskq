package com.nkl.admin.action;

import com.nkl.admin.domain.User;
import com.nkl.admin.manager.LoginManager;
import com.nkl.common.action.BaseAction;
import com.nkl.common.util.Param;
import com.nkl.common.util.BeanLocator;

public class LoginAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	LoginManager loginManager = (LoginManager)BeanLocator.getInstance().getBean("loginManager");
	
	User params;
	String tip;

	/**
	 * @Title: InSystem
	 * @Description: 用户登录
	 * @return String
	 */
	public String InSystem(){
		try {
			//验证码验证
			String random = (String)Param.getSession("random");
			if (!random.equals(params.getRandom())) {
				tip="验证码错误";
				return "error";
			}
			
			//用户登录查询
			User admin = loginManager.getUser(params);
			if (admin!=null) {
				Param.setSession("admin", admin);
			}else {
				tip="用户名或密码错误";
				return "error";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tip="登录异常，请稍后重试";
			return "error";
		}
		
		return "success";
	}
	
	/**
	 * @Title: OutSystem
	 * @Description: 退出登录
	 * @return String
	 */
	public String OutSystem(){
		try {
			//用户查询
			User user = (User)Param.getSession("admin");
			if (user!=null) {
				//退出登录
				Param.removeSession("admin");
			}
			
		} catch (Exception e) {
			return "logout";
		}
		
		return "logout";
	}
	
	/**
	 * @Title: RegSystem
	 * @Description: 用户注册
	 * @return String
	 */
	public String RegSystem(){
		try {
			//查询验证码
			String random = (String)Param.getSession("random");
			if (!random.equals(params.getRandom())) {
				setErrorReason("验证码不正确");
				return "regerror";
			}
			
			//查询用户名是否被占用
			User user = new User();
			user.setUser_name(params.getUser_name());
			User user_temp = loginManager.getUser(user);
			if (user_temp!=null) {
				setErrorReason("注册失败，用户名已被注册："+params.getUser_name());
				return "regerror";
			}
			
			//添加用户入库
			loginManager.addUser(params);
			
			//用户登录查询
			User reguser = new User();//
			reguser.setUser_name(params.getUser_name());
			reguser = loginManager.getUser(reguser);
			if (reguser!=null) {
				Param.setSession("admin", reguser);
			}
			
		} catch (Exception e) {
			setErrorReason("注册异常，请稍后重试");
			return "regerror";
		}
		
		return "regsuccess";
	}
	
	public User getParams() {
		return params;
	}

	public void setParams(User params) {
		this.params = params;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

}
