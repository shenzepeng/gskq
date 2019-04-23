package com.nkl.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nkl.admin.domain.Attend;
import com.nkl.admin.domain.Config;
import com.nkl.admin.domain.Dept;
import com.nkl.admin.domain.Fill;
import com.nkl.admin.domain.Holiday;
import com.nkl.admin.domain.Leave;
import com.nkl.admin.domain.Post;
import com.nkl.admin.domain.User;
import com.nkl.admin.manager.AdminManager;
import com.nkl.common.action.BaseAction;
import com.nkl.common.util.DateUtil;
import com.nkl.common.util.Param;
import com.nkl.common.util.BeanLocator;

public class AdminAction  extends BaseAction {

	private static final long serialVersionUID = 1L;
	AdminManager adminManager = (AdminManager)BeanLocator.getInstance().getBean("adminManager");
	
	//抓取页面参数
	User paramsUser;
	Attend paramsAttend;
	Dept paramsDept;
	Config paramsConfig;
	Fill paramsFill;
	Leave paramsLeave;
	Post paramsPost;
	Holiday paramsHoliday;
	String tip;
	
	/**
	 * @Title: saveAdmin
	 * @Description: 保存修改个人信息
	 * @return String
	 */
	public String saveAdmin(){
		try {
			//验证用户会话是否失效
			if (!validateAdmin()) {
				return "loginTip";
			}
			 //保存修改个人信息
			adminManager.updateUser(paramsUser);
			//更新session
			User admin = new User();
			admin.setUser_id(paramsUser.getUser_id());
			admin = adminManager.queryUser(admin);
			Param.setSession("admin", admin);
			
			setSuccessTip("编辑成功", "modifyInfo.jsp");
			
		} catch (Exception e) {
			setErrorTip("编辑异常", "modifyInfo.jsp");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: saveAdminPass
	 * @Description: 保存修改个人密码
	 * @return String
	 */
	public String saveAdminPass(){
		try {
			//验证用户会话是否失效
			if (!validateAdmin()) {
				return "loginTip";
			}
			 //保存修改个人密码
			adminManager.updateUser(paramsUser);
			//更新session
			User admin = (User)Param.getSession("admin");
			if (admin!=null) {
				admin.setUser_pass(paramsUser.getUser_pass());
				Param.setSession("admin", admin);
			}
			
			setSuccessTip("修改成功", "modifyPwd.jsp");
		} catch (Exception e) {
			setErrorTip("修改异常", "modifyPwd.jsp");
		}
		
		return "infoTip";
	}
	
	
	/**
	 * @Title: listUsers
	 * @Description: 查询用户
	 * @return String
	 */
	public String listUsers(){
		try {
			if (paramsUser==null) {
				paramsUser = new User();
			}
			//设置分页信息
			setPagination(paramsUser);
			//总的条数
			int[] sum={0};
			//查询用户列表
			paramsUser.setUser_type(1);
			List<User> users = adminManager.listUsers(paramsUser,sum); 
			
			Param.setAttribute("users", users);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
		} catch (Exception e) {
			setErrorTip("查询用户异常", "main.jsp");
			return "infoTip";
		}
		
		return "userShow";
	}
	
	/**
	 * @Title: addUserShow
	 * @Description: 显示添加用户页面
	 * @return String
	 */
	public String addUserShow(){
		//查询部门
		Dept dept = new Dept();
		dept.setStart(-1);
		List<Dept> depts = adminManager.listDepts(dept, null);
		Param.setAttribute("depts", depts);
		
		return "userEdit";
	}
	
	/**
	 * @Title: addUser
	 * @Description: 添加用户
	 * @return String
	 */
	public String addUser(){
		try {
			//检查员工编号是否存在
			User user = new User();
			user.setUser_no(paramsUser.getUser_no());
			user = adminManager.queryUser(user);
			if (user!=null) {
				tip="失败，该员工编号已经存在！";
				Param.setAttribute("user", paramsUser);
				
				//查询部门
				Dept dept = new Dept();
				dept.setStart(-1);
				List<Dept> depts = adminManager.listDepts(dept, null);
				Param.setAttribute("depts", depts);
				
				return "userEdit";
			}
			//检查用户名是否存在
			user = new User();
			user.setUser_name(paramsUser.getUser_name());
			user = adminManager.queryUser(user);
			if (user!=null) {
				tip="失败，该用户名已经存在！";
				Param.setAttribute("user", paramsUser);
				
				//查询部门
				Dept dept = new Dept();
				dept.setStart(-1);
				List<Dept> depts = adminManager.listDepts(dept, null);
				Param.setAttribute("depts", depts);
				
				return "userEdit";
			}
			
			 //添加用户
			adminManager.addUser(paramsUser);
			
			setSuccessTip("添加成功", "Admin_listUsers.action");
		} catch (Exception e) {
			setErrorTip("添加用户异常", "Admin_listUsers.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editUser
	 * @Description: 编辑用户
	 * @return String
	 */
	public String editUser(){
		try {
			 //得到用户
			User user = adminManager.queryUser(paramsUser);
			Param.setAttribute("user", user);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
		} catch (Exception e) {
			setErrorTip("查询用户异常", "Admin_listUsers.action");
			return "infoTip";
		}
		
		return "userEdit";
	}
	
	/**
	 * @Title: saveUser
	 * @Description: 保存编辑用户
	 * @return String
	 */
	public String saveUser(){
		try {
			 //保存编辑用户
			adminManager.updateUser(paramsUser);
			
			setSuccessTip("编辑成功", "Admin_listUsers.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("user", paramsUser);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
			return "userEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delUsers
	 * @Description: 删除用户
	 * @return String
	 */
	public String delUsers(){
		try {
			 //删除用户
			adminManager.delUsers(paramsUser);
			
			setSuccessTip("删除用户成功", "Admin_listUsers.action");
		} catch (Exception e) {
			setErrorTip("删除用户异常", "Admin_listUsers.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listDepts
	 * @Description: 查询部门
	 * @return String
	 */
	public String listDepts(){
		try {
			if (paramsDept==null) {
				paramsDept = new Dept();
			}
			
			//设置分页信息
			setPagination(paramsDept);
			//总的条数
			int[] sum={0};
			//查询部门列表
			List<Dept> depts = adminManager.listDepts(paramsDept,sum); 
			
			Param.setAttribute("depts", depts);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询部门异常", "main.jsp");
			return "infoTip";
		}
		
		return "deptShow";
	}
	
	/**
	 * @Title: addDeptShow
	 * @Description: 显示添加部门页面
	 * @return String
	 */
	public String addDeptShow(){
		return "deptEdit";
	}
	
	/**
	 * @Title: addDept
	 * @Description: 添加部门
	 * @return String
	 */
	public String addDept(){
		try {
			//检查部门是否存在
			Dept dept = new Dept();
			dept.setDept_name(paramsDept.getDept_name());
			dept = adminManager.queryDept(dept);
			if (dept!=null) {
				tip="失败，该部门已经存在！";
				Param.setAttribute("dept", paramsDept);
				return "deptEdit";
			}
			
			 //添加部门
			adminManager.addDept(paramsDept);
			
			setSuccessTip("添加成功", "Admin_listDepts.action");
		} catch (Exception e) {
			setErrorTip("添加部门异常", "Admin_listDepts.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editDept
	 * @Description: 编辑部门
	 * @return String
	 */
	public String editDept(){
		try {
			 //得到部门
			Dept dept = adminManager.queryDept(paramsDept);
			Param.setAttribute("dept", dept);
			
		} catch (Exception e) {
			setErrorTip("查询部门异常", "Admin_listDepts.action");
			return "infoTip";
		}
		
		return "deptEdit";
	}
	
	/**
	 * @Title: saveDept
	 * @Description: 保存编辑部门
	 * @return String
	 */
	public String saveDept(){
		try {
			//检查部门是否存在
			Dept dept = new Dept();
			dept.setDept_name(paramsDept.getDept_name());
			dept = adminManager.queryDept(dept);
			if (dept!=null&&dept.getDept_id().intValue()!=paramsDept.getDept_id().intValue()) {
				tip="失败，该部门已经存在！";
				Param.setAttribute("dept", paramsDept);
				return "deptEdit";
			}
			
			 //保存编辑部门
			adminManager.updateDept(paramsDept);
			
			setSuccessTip("编辑成功", "Admin_listDepts.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("dept", paramsDept);
			return "deptEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delDepts
	 * @Description: 删除部门
	 * @return String
	 */
	public String delDepts(){
		try {
			 //删除部门
			adminManager.delDepts(paramsDept);
			
			setSuccessTip("删除部门成功", "Admin_listDepts.action");
		} catch (Exception e) {
			setErrorTip("删除部门异常", "Admin_listDepts.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listConfigs
	 * @Description: 查询考勤时间
	 * @return String
	 */
	public String listConfigs(){
		try {
			if (paramsConfig==null) {
				paramsConfig = new Config();
			}
			
			//设置分页信息
			setPagination(paramsConfig);
			//总的条数
			int[] sum={0};
			//查询考勤时间列表
			List<Config> configs = adminManager.listConfigs(paramsConfig,sum); 
			
			Param.setAttribute("configs", configs);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询考勤时间异常", "main.jsp");
			return "infoTip";
		}
		
		return "configShow";
	}
	
	/**
	 * @Title: saveConfig
	 * @Description: 保存编辑考勤时间
	 * @return String
	 */
	public String saveConfig(){
		try {
			 //保存编辑考勤时间
			adminManager.updateConfig(paramsConfig);
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorReason("后台服务器繁忙，请稍后再试");
			return "error2";
		}
		
		return "success";
	}
	
	/**
	 * @Title: listAttends
	 * @Description: 查询本日考勤
	 * @return String
	 */
	public String listAttendTodays(){
		try {
			if (paramsAttend==null) {
				paramsAttend = new Attend();
			}
			//设置分页信息
			setPagination(paramsAttend);
			//总的条数
			int[] sum={0};
			//设置日期为本日
			paramsAttend.setAttend_date(DateUtil.getDate(DateUtil.getCurDate()));
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsAttend.setUser_id(admin.getUser_id());
			}
			//查询本日考勤是否存在
			String holiday_note = adminManager.checkAttendToday();
			Param.setAttribute("holiday_note", holiday_note);
			//查询考勤列表
			List<Attend> attends = adminManager.listAttends(paramsAttend,sum); 
			Param.setAttribute("attends", attends);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
			//查询当前时间为上午or下午
			int hour = DateUtil.getHour(new Date());
			Param.setAttribute("ap", hour>12?2:1);
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("查询考勤异常", "main.jsp");
			return "infoTip";
		}
		
		return "attendTodayShow";
	}
	
	/**
	 * @Title: addAttend
	 * @Description: 本日考勤打卡
	 * @return String
	 */
	public String addAttend(){
		try {
			 //本日考勤打卡
			adminManager.addAttend(paramsAttend);
			
			setSuccessTip("签到成功", "Admin_listAttendTodays.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("签到异常，后台服务器繁忙", "Admin_listAttendTodays.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listAttends
	 * @Description: 查询考勤记录
	 * @return String
	 */
	public String listAttends(){
		try {
			if (paramsAttend==null) {
				paramsAttend = new Attend();
			}
			//设置分页信息
			setPagination(paramsAttend);
			//总的条数
			int[] sum={0};
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsAttend.setUser_id(admin.getUser_id());
			}
			//考勤检测
			//adminManager.checkAttends(paramsAttend.getAttend_date1(),paramsAttend.getAttend_date2());
			//查询考勤列表
			List<Attend> attends = adminManager.listAttends(paramsAttend,sum); 
			
			Param.setAttribute("attends", attends);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
		} catch (Exception e) {
			setErrorTip("查询考勤记录异常", "main.jsp");
			return "infoTip";
		}
		
		return "attendShow";
	}
	
	/**
	 * @Title: listAttendTempsShow
	 * @Description: 显示考勤信息统计页面
	 * @return String
	 */
	public String listAttendTempsShow(){
		//查询部门
		Dept dept = new Dept();
		dept.setStart(-1);
		List<Dept> depts = adminManager.listDepts(dept, null);
		Param.setAttribute("depts", depts);
		
		return "attendTempShow";
	}
	
	/**
	 * @Title: listAttendTemps
	 * @Description: 考勤信息统计
	 * @return String
	 */
	public String listAttendTemps(){
		try {
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsAttend.setUser_id(admin.getUser_id());
			}
			//考勤检测
			String _attend_date = paramsAttend.getAttend_month();
			String _lastDay = "02,04,06,09,11";
			String start_date = _attend_date+"-01";
			String end_date = _attend_date+"-31";
			if (_attend_date.equals(DateUtil.getCurDateMonth())) {
				end_date = DateUtil.getCurDate();
			}else if (_lastDay.indexOf(_attend_date.substring(5))!=-1) {
				end_date = _attend_date+"-30";
			}
			//adminManager.checkAttends(start_date,end_date);
			//查询 考勤信息统计
			List<Attend> attendTemps = adminManager.listAttendTemps(paramsAttend); 
			if (attendTemps==null || attendTemps.size()==0) {
				setErrorReason("暂无考勤数据");
				return "error2";
			}
			setResult("attendTemps", attendTemps);
			
			//查询单个用户月视图考勤统计
			if (attendTemps.size()==1) {
				Map<String, String> attendsMap = new HashMap<String,String>();
				Map<String, String> holidaysMap = new HashMap<String,String>();
				//查询本月用户考勤统计
				String attend_date = paramsAttend.getAttend_month();
				paramsAttend.setAttend_date(null);
				paramsAttend.setAttend_date1(attend_date+"-01");
				paramsAttend.setAttend_date2(attend_date+"-31");
				String lastDay = "02,04,06,09,11";
				int week_middle = 31;
				if (lastDay.indexOf(attend_date.substring(5))!=-1) {
					paramsAttend.setAttend_date2(attend_date+"-30");
					week_middle = 30;
				}
				paramsAttend.setStart(-1);
				List<Attend> attends = adminManager.listAttends(paramsAttend,null);
				for (Attend attend : attends) {
					attendsMap.put(attend.getAttend_dateDesc().substring(8)+"_1", attend.getAttend_type1Desc());
					attendsMap.put(attend.getAttend_dateDesc().substring(8)+"_2", attend.getAttend_type2Desc());
				}
				setResult("attendsMap", attendsMap);
				
				//处理月视图前后空白的天数
				int week_first = DateUtil.getWeek(DateUtil.getDate(paramsAttend.getAttend_date1()));
				week_first=week_first==1?7:week_first-1;
				int week_last = DateUtil.getWeek(DateUtil.getDate(paramsAttend.getAttend_date2()));
				week_last=week_last==1?7:week_last-1;
				setResult("week_before", week_first-1);
				setResult("week_middle", week_middle);
				setResult("week_after", 7-week_last);
				
				//节假日信息查询
				Holiday holiday = new Holiday();
				holiday.setStart(-1);
				holiday.setHoliday_date1(paramsAttend.getAttend_date1());
				holiday.setHoliday_date2(paramsAttend.getAttend_date2());
				List<Holiday> holidays = adminManager.listHolidays(holiday, null);
				if (holidays!=null) {
					for (Holiday holiday2 : holidays) {
						holidaysMap.put(holiday2.getHoliday_date().substring(8), holiday2.getHoliday_note());
						attendsMap.put(holiday2.getHoliday_date().substring(8), "节假日");
					}
				}
				setResult("holidaysMap", holidaysMap);
			}
			
			//计算考勤天数统计图数据
			String[] x = {"未签到天数","已签到天数","迟签到天数","早退天数","请假天数","离岗天数"};
			double[] y = new double[6];
			for (Attend attendTemp : attendTemps) {
				y[0]+=attendTemp.getAttend_type1_days();
				y[1]+=attendTemp.getAttend_type2_days();
				y[2]+=attendTemp.getAttend_type3_days();
				y[3]+=attendTemp.getAttend_type6_days();
				y[4]+=attendTemp.getAttend_type4_days();
				y[5]+=attendTemp.getAttend_type5_days();
			}
			setResult("x", x);
			setResult("y", y);
			
		} catch (Exception e) {
			e.printStackTrace();
			setErrorReason("后台服务器繁忙，请稍后重试");
			return "error2";
		}
		
		return "success";
	}
	
	/**
	 * @Title: delAttends
	 * @Description: 删除考勤
	 * @return String
	 */
	public String delAttends(){
		try {
			 //删除考勤
			adminManager.delAttends(paramsAttend);
			
			setSuccessTip("删除考勤成功", "Admin_listAttends.action");
		} catch (Exception e) {
			setErrorTip("删除考勤异常", "Admin_listAttends.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listFills
	 * @Description: 查询补签记录
	 * @return String
	 */
	public String listFills(){
		try {
			if (paramsFill==null) {
				paramsFill = new Fill();
			}
			//设置分页信息
			setPagination(paramsFill);
			//总的条数
			int[] sum={0};
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsFill.setUser_id(admin.getUser_id());
			}
			//查询补签记录列表
			List<Fill> fills = adminManager.listFills(paramsFill,sum); 
			Param.setAttribute("fills", fills);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
		} catch (Exception e) {
			setErrorTip("查询补签记录异常", "main.jsp");
			return "infoTip";
		}
		
		return "fillShow";
	}
	
	/**
	 * @Title: assessFill
	 * @Description: 审核补签记录
	 * @return String
	 */
	public String assessFill(){
		try {
			 //审核补签记录
			adminManager.updateFill(paramsFill);
			
			setSuccessTip("审核成功", "Admin_listFills.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("审核异常", "Admin_listFills.action");
			return "infoTip";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: addFillShow
	 * @Description: 显示添加补签记录页面
	 * @return String
	 */
	public String addFillShow(){
		return "fillEdit";
	}
	
	/**
	 * @Title: addFill
	 * @Description: 添加补签记录
	 * @return String
	 */
	public String addFill(){
		try {
			 //添加补签记录
			adminManager.addFill(paramsFill);
			
			setSuccessTip("添加补签申请成功，请等待审核", "Admin_listFills.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("添加补签记录异常", "Admin_listFills.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editFill
	 * @Description: 编辑补签记录
	 * @return String
	 */
	public String editFill(){
		try {
			 //得到补签记录
			Fill fill = adminManager.queryFill(paramsFill);
			Param.setAttribute("fill", fill);
			
		} catch (Exception e) {
			setErrorTip("查询补签记录异常", "Admin_listFills.action");
			return "infoTip";
		}
		
		return "fillEdit";
	}
	
	/**
	 * @Title: saveFill
	 * @Description: 保存编辑补签记录
	 * @return String
	 */
	public String saveFill(){
		try {
			 //保存编辑补签记录
			adminManager.updateFill(paramsFill);
			
			setSuccessTip("编辑成功", "Admin_listFills.action");
		} catch (Exception e) {
			e.printStackTrace();
			tip="编辑失败";
			Param.setAttribute("fill", paramsFill);
			return "fillEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delFills
	 * @Description: 删除补签记录
	 * @return String
	 */
	public String delFills(){
		try {
			 //删除补签记录
			adminManager.delFills(paramsFill);
			
			setSuccessTip("删除补签记录成功", "Admin_listFills.action");
		} catch (Exception e) {
			setErrorTip("删除补签记录异常", "Admin_listFills.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listLeaves
	 * @Description: 查询请假申请
	 * @return String
	 */
	public String listLeaves(){
		try {
			if (paramsLeave==null) {
				paramsLeave = new Leave();
			}
			//设置分页信息
			setPagination(paramsLeave);
			//总的条数
			int[] sum={0};
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsLeave.setUser_id(admin.getUser_id());
			}
			//查询请假申请列表
			List<Leave> leaves = adminManager.listLeaves(paramsLeave,sum); 
			Param.setAttribute("leaves", leaves);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("查询请假申请异常", "main.jsp");
			return "infoTip";
		}
		
		return "leaveShow";
	}
	
	/**
	 * @Title: assessLeave
	 * @Description: 审核请假申请
	 * @return String
	 */
	public String assessLeave(){
		try {
			 //审核请假申请
			adminManager.updateLeave(paramsLeave);
			
			setSuccessTip("审核成功", "Admin_listLeaves.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("审核异常", "Admin_listLeaves.action");
			return "infoTip";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: addLeaveShow
	 * @Description: 显示添加请假申请页面
	 * @return String
	 */
	public String addLeaveShow(){
		return "leaveEdit";
	}
	
	/**
	 * @Title: addLeave
	 * @Description: 添加请假申请
	 * @return String
	 */
	public String addLeave(){
		try {
			 //添加请假申请
			adminManager.addLeave(paramsLeave);
			
			setSuccessTip("添加请假申请成功，请等待审核", "Admin_listLeaves.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("添加请假申请异常", "Admin_listLeaves.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editLeave
	 * @Description: 编辑请假申请
	 * @return String
	 */
	public String editLeave(){
		try {
			 //得到请假申请
			Leave leave = adminManager.queryLeave(paramsLeave);
			Param.setAttribute("leave", leave);
			
		} catch (Exception e) {
			setErrorTip("查询请假申请异常", "Admin_listLeaves.action");
			return "infoTip";
		}
		
		return "leaveEdit";
	}
	
	/**
	 * @Title: saveLeave
	 * @Description: 保存编辑请假申请
	 * @return String
	 */
	public String saveLeave(){
		try {
			 //保存编辑请假申请
			adminManager.updateLeave(paramsLeave);
			
			setSuccessTip("编辑成功", "Admin_listLeaves.action");
		} catch (Exception e) {
			e.printStackTrace();
			tip="编辑失败";
			Param.setAttribute("leave", paramsLeave);
			return "leaveEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delLeaves
	 * @Description: 删除请假申请
	 * @return String
	 */
	public String delLeaves(){
		try {
			 //删除请假申请
			adminManager.delLeaves(paramsLeave);
			
			setSuccessTip("删除请假申请成功", "Admin_listLeaves.action");
		} catch (Exception e) {
			setErrorTip("删除请假申请异常", "Admin_listLeaves.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listPosts
	 * @Description: 查询离岗申请
	 * @return String
	 */
	public String listPosts(){
		try {
			if (paramsPost==null) {
				paramsPost = new Post();
			}
			//设置分页信息
			setPagination(paramsPost);
			//总的条数
			int[] sum={0};
			//用户身份限制
			User admin = (User)Param.getSession("admin");
			if (admin.getUser_type()==1) {
				paramsPost.setUser_id(admin.getUser_id());
			}
			//查询离岗申请列表
			List<Post> posts = adminManager.listPosts(paramsPost,sum); 
			Param.setAttribute("posts", posts);
			setTotalCount(sum[0]);
			
			//查询部门
			Dept dept = new Dept();
			dept.setStart(-1);
			List<Dept> depts = adminManager.listDepts(dept, null);
			Param.setAttribute("depts", depts);
			
		} catch (Exception e) {
			setErrorTip("查询离岗申请异常", "main.jsp");
			return "infoTip";
		}
		
		return "postShow";
	}
	
	/**
	 * @Title: assessPost
	 * @Description: 审核离岗申请
	 * @return String
	 */
	public String assessPost(){
		try {
			 //审核离岗申请
			adminManager.updatePost(paramsPost);
			
			setSuccessTip("审核成功", "Admin_listPosts.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("审核异常", "Admin_listPosts.action");
			return "infoTip";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: addPostShow
	 * @Description: 显示添加离岗申请页面
	 * @return String
	 */
	public String addPostShow(){
		return "postEdit";
	}
	
	/**
	 * @Title: addPost
	 * @Description: 添加离岗申请
	 * @return String
	 */
	public String addPost(){
		try {
			 //添加离岗申请
			adminManager.addPost(paramsPost);
			
			setSuccessTip("添加离岗申请成功，请等待审核", "Admin_listPosts.action");
		} catch (Exception e) {
			e.printStackTrace();
			setErrorTip("添加离岗申请异常", "Admin_listPosts.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editPost
	 * @Description: 编辑离岗申请
	 * @return String
	 */
	public String editPost(){
		try {
			 //得到离岗申请
			Post post = adminManager.queryPost(paramsPost);
			Param.setAttribute("post", post);
			
		} catch (Exception e) {
			setErrorTip("查询离岗申请异常", "Admin_listPosts.action");
			return "infoTip";
		}
		
		return "postEdit";
	}
	
	/**
	 * @Title: savePost
	 * @Description: 保存编辑离岗申请
	 * @return String
	 */
	public String savePost(){
		try {
			 //保存编辑离岗申请
			adminManager.updatePost(paramsPost);
			
			setSuccessTip("编辑成功", "Admin_listPosts.action");
		} catch (Exception e) {
			e.printStackTrace();
			tip="编辑失败";
			Param.setAttribute("post", paramsPost);
			return "postEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delPosts
	 * @Description: 删除离岗申请
	 * @return String
	 */
	public String delPosts(){
		try {
			 //删除离岗申请
			adminManager.delPosts(paramsPost);
			
			setSuccessTip("删除离岗申请成功", "Admin_listPosts.action");
		} catch (Exception e) {
			setErrorTip("删除离岗申请异常", "Admin_listPosts.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: listHolidays
	 * @Description: 查询节假日
	 * @return String
	 */
	public String listHolidays(){
		try {
			if (paramsHoliday==null) {
				paramsHoliday = new Holiday();
			}
			//设置分页信息
			setPagination(paramsHoliday);
			//总的条数
			int[] sum={0};
			//查询节假日列表
			List<Holiday> holidays = adminManager.listHolidays(paramsHoliday,sum); 
			Param.setAttribute("holidays", holidays);
			setTotalCount(sum[0]);
			
		} catch (Exception e) {
			setErrorTip("查询节假日异常", "main.jsp");
			return "infoTip";
		}
		
		return "holidayShow";
	}
	
	/**
	 * @Title: addHolidayShow
	 * @Description: 显示添加节假日页面
	 * @return String
	 */
	public String addHolidayShow(){
		return "holidayEdit";
	}
	
	/**
	 * @Title: addHoliday
	 * @Description: 添加节假日
	 * @return String
	 */
	public String addHoliday(){
		try {
			//检查节假日是否存在
			Holiday holiday = new Holiday();
			holiday.setHoliday_date1(paramsHoliday.getHoliday_date1());
			holiday.setHoliday_date2(paramsHoliday.getHoliday_date2());
			List<Holiday> holidays = adminManager.listHolidays(holiday, null);
			if (holidays!=null && holidays.size()>0) {
				tip="失败，该日期段内的节假日已经存在！";
				Param.setAttribute("holiday", paramsHoliday);
				return "holidayEdit";
			}
			
			 //添加节假日
			adminManager.addHoliday(paramsHoliday);
			
			setSuccessTip("添加节假日成功", "Admin_listHolidays.action");
		} catch (Exception e) {
			setErrorTip("添加节假日异常", "Admin_listHolidays.action");
		}
		
		return "infoTip";
	}
	
	 
	/**
	 * @Title: editHoliday
	 * @Description: 编辑节假日
	 * @return String
	 */
	public String editHoliday(){
		try {
			 //得到节假日
			Holiday holiday = adminManager.queryHoliday(paramsHoliday);
			Param.setAttribute("holiday", holiday);
			
		} catch (Exception e) {
			setErrorTip("查询节假日异常", "Admin_listHolidays.action");
			return "infoTip";
		}
		
		return "holidayEdit";
	}
	
	/**
	 * @Title: saveHoliday
	 * @Description: 保存编辑节假日
	 * @return String
	 */
	public String saveHoliday(){
		try {
			 //保存编辑节假日
			adminManager.updateHoliday(paramsHoliday);
			
			setSuccessTip("编辑成功", "Admin_listHolidays.action");
		} catch (Exception e) {
			tip="编辑失败";
			Param.setAttribute("holiday", paramsHoliday);
			return "holidayEdit";
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: delHolidays
	 * @Description: 删除节假日
	 * @return String
	 */
	public String delHolidays(){
		try {
			 //删除节假日
			adminManager.delHolidays(paramsHoliday);
			
			setSuccessTip("删除节假日成功", "Admin_listHolidays.action");
		} catch (Exception e) {
			setErrorTip("删除节假日异常", "Admin_listHolidays.action");
		}
		
		return "infoTip";
	}
	
	/**
	 * @Title: validateAdmin
	 * @Description: admin登录验证
	 * @return boolean
	 */
	private boolean validateAdmin(){
		User admin = (User)Param.getSession("admin");
		if (admin!=null) {
			return true;
		}else {
			return false;
		}
	}
	
	private void setErrorTip(String tip,String url){
		Param.setAttribute("tipType", "error");
		Param.setAttribute("tip", tip);
		Param.setAttribute("url1", url);
		Param.setAttribute("value1", "确 定");
	}
	
	private void setSuccessTip(String tip,String url){
		Param.setAttribute("tipType", "success");
		Param.setAttribute("tip", tip);
		Param.setAttribute("url1", url);
		Param.setAttribute("value1", "确 定");
	}

	public User getParamsUser() {
		return paramsUser;
	}

	public void setParamsUser(User paramsUser) {
		this.paramsUser = paramsUser;
	}

	public Attend getParamsAttend() {
		return paramsAttend;
	}

	public void setParamsAttend(Attend paramsAttend) {
		this.paramsAttend = paramsAttend;
	}

	public Dept getParamsDept() {
		return paramsDept;
	}

	public void setParamsDept(Dept paramsDept) {
		this.paramsDept = paramsDept;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Config getParamsConfig() {
		return paramsConfig;
	}

	public Fill getParamsFill() {
		return paramsFill;
	}

	public Leave getParamsLeave() {
		return paramsLeave;
	}

	public Post getParamsPost() {
		return paramsPost;
	}

	public void setParamsConfig(Config paramsConfig) {
		this.paramsConfig = paramsConfig;
	}

	public void setParamsFill(Fill paramsFill) {
		this.paramsFill = paramsFill;
	}

	public void setParamsLeave(Leave paramsLeave) {
		this.paramsLeave = paramsLeave;
	}

	public void setParamsPost(Post paramsPost) {
		this.paramsPost = paramsPost;
	}

	public Holiday getParamsHoliday() {
		return paramsHoliday;
	}

	public void setParamsHoliday(Holiday paramsHoliday) {
		this.paramsHoliday = paramsHoliday;
	}

	 
}
