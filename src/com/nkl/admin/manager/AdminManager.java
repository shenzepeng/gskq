package com.nkl.admin.manager;

import java.util.Date;
import java.util.List;

import com.nkl.admin.dao.AttendDao;
import com.nkl.admin.dao.ConfigDao;
import com.nkl.admin.dao.DeptDao;
import com.nkl.admin.dao.FillDao;
import com.nkl.admin.dao.HolidayDao;
import com.nkl.admin.dao.LeaveDao;
import com.nkl.admin.dao.PostDao;
import com.nkl.admin.dao.UserDao;
import com.nkl.admin.domain.Attend;
import com.nkl.admin.domain.Config;
import com.nkl.admin.domain.Dept;
import com.nkl.admin.domain.Fill;
import com.nkl.admin.domain.Holiday;
import com.nkl.admin.domain.Leave;
import com.nkl.admin.domain.Post;
import com.nkl.admin.domain.User;
import com.nkl.common.util.DateUtil;
import com.nkl.common.util.Md5;
import com.nkl.common.util.Param;
import com.nkl.common.util.StringUtil;

public class AdminManager {

	DeptDao deptDao;
	UserDao userDao;
	AttendDao attendDao;
	FillDao fillDao;
	LeaveDao leaveDao;
	PostDao postDao;
	ConfigDao configDao;
	HolidayDao holidayDao;
	
	/**
	 * @Title: listConfigs
	 * @Description: 考勤时间查询
	 * @param config
	 * @return List<Config>
	 */
	public List<Config> listConfigs(Config config, int[] sum) {
		
		if (sum != null) {
			sum[0] = configDao.listConfigsCount(config);
		}
		List<Config> configs = configDao.listConfigs(config);

		
		return configs;
	}

	/**
	 * @Title: updateConfig
	 * @Description: 更新考勤时间信息
	 * @param config
	 * @return void
	 */
	public void updateConfig(Config config) {
		
		configDao.updateConfig(config);
		
	}
	
	/**
	 * @Title: listUsers
	 * @Description: 用户查询
	 * @param user
	 * @return List<User>
	 */
	public List<User> listUsers(User user, int[] sum) {
		
		if (sum != null) {
			sum[0] = userDao.listUsersCount(user);
		}
		List<User> users = userDao.listUsers(user);

		
		return users;
	}
	
	/**
	 * @Title: queryUser
	 * @Description: 用户查询
	 * @param user
	 * @return User
	 */
	public User queryUser(User user) {
		
		User _user = userDao.getUser(user);
		
		return _user;
	}

	/**
	 * @Title: addUser
	 * @Description: 添加用户
	 * @param user
	 * @return void
	 */
	public void addUser(User user) {
		
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		user.setUser_type(1);
		userDao.addUser(user);
		
	}

	/**
	 * @Title: updateUser
	 * @Description: 更新用户信息
	 * @param user
	 * @return void
	 */
	public void updateUser(User user) {
		
		if (!StringUtil.isEmptyString(user.getUser_pass())) {
			user.setUser_pass(Md5.makeMd5(user.getUser_pass()));
		}
		userDao.updateUser(user);
		
	}

	/**
	 * @Title: delUsers
	 * @Description: 删除用户信息
	 * @param user
	 * @return void
	 */
	public void delUsers(User user) {
		
		userDao.delUsers(user.getIds().split(","));
		
	}

	/**
	 * @Title: listDepts
	 * @Description: 部门查询
	 * @param dept
	 * @return List<Dept>
	 */
	public List<Dept> listDepts(Dept dept, int[] sum) {
		
		if (sum != null) {
			sum[0] = deptDao.listDeptsCount(dept);
		}
		List<Dept> depts = deptDao.listDepts(dept);

		
		return depts;
	}

	/**
	 * @Title: queryDept
	 * @Description: 部门查询
	 * @param dept
	 * @return Dept
	 */
	public Dept queryDept(Dept dept) {
		
		Dept _dept = deptDao.getDept(dept);
		
		return _dept;
	}

	/**
	 * @Title: addDept
	 * @Description: 添加部门
	 * @param dept
	 * @return void
	 */
	public void addDept(Dept dept) {
		
		deptDao.addDept(dept);
		
	}

	/**
	 * @Title: updateDept
	 * @Description: 更新部门信息
	 * @param dept
	 * @return void
	 */
	public void updateDept(Dept dept) {
		
		deptDao.updateDept(dept);
		
	}

	/**
	 * @Title: delDept
	 * @Description: 删除部门信息
	 * @param dept
	 * @return void
	 */
	public void delDepts(Dept dept) {
		
		deptDao.delDepts(dept.getIds().split(","));
		
	}
	
	/**
	 * @Title: listAttends
	 * @Description: 考勤查询
	 * @param attend
	 * @return List<Attend>
	 */
	public List<Attend> listAttends(Attend attend, int[] sum) {
		
		if (sum != null) {
			sum[0] = attendDao.listAttendsCount(attend);
		}
		List<Attend> attends = attendDao.listAttends(attend);

		
		return attends;
	}
	
	/**
	 * @Title: listAttendTemps
	 * @Description: 考勤统计
	 * @param attend
	 * @return List<Attend>
	 */
	public List<Attend> listAttendTemps(Attend attend) {
		
		
		List<Attend> attends = attendDao.listAttendTemps(attend);

		
		return attends;
	}


	/**
	 * @Title: queryAttend
	 * @Description: 考勤查询
	 * @param attend
	 * @return Attend
	 */
	public Attend queryAttend(Attend attend) {
		
		Attend _attend = attendDao.getAttend(attend);
		
		return _attend;
	}

	/**
	 * @Title: checkAttendToday
	 * @Description: 检查本日考勤是否存在
	 * @param attend
	 * @return void
	 */
	public String checkAttendToday() {
		String holiday_note = null;
		
		//检查本日是否为节假日
		Holiday _holiday = new Holiday();
		_holiday.setHoliday_date(DateUtil.getCurDate());
		_holiday = holidayDao.getHoliday(_holiday);
		if (_holiday==null) {
			//查询本日考勤是否存在
			Attend _attend = new Attend();
			_attend.setAttend_date(DateUtil.getDate(DateUtil.getCurDate()));
			_attend = attendDao.getAttend(_attend);
			if (_attend==null) {
				//初始化本日考勤
				addAttendBatch();
			}else{
				User admin = (User)Param.getSession("admin");
				_attend = new Attend();
				_attend.setUser_id(admin.getUser_id());
				_attend.setAttend_date(DateUtil.getDate(DateUtil.getCurDate()));
				_attend = attendDao.getAttend(_attend);
				if (_attend==null) {
					//初始化本日考勤
					addAttendBatchUser(DateUtil.getCurDate(),admin.getUser_id());
				}
			}
		}else {
			holiday_note = _holiday.getHoliday_note();
		}
		
		return holiday_note;
	}
	
	/**
	 * @Title: checkAttendEveryDay
	 * @Description: 检查指定日期考勤是否存在
	 * @param attend
	 * @return void
	 */
	public String checkAttendEveryDay(String date) {
		String holiday_note = null;
		
		//检查本日是否为节假日
		Holiday _holiday = new Holiday();
		_holiday.setHoliday_date(date);
		_holiday = holidayDao.getHoliday(_holiday);
		if (_holiday==null) {
			//查询本日考勤是否存在
			Attend _attend = new Attend();
			_attend.setAttend_date(DateUtil.getDate(DateUtil.getCurDate()));
			_attend = attendDao.getAttend(_attend);
			if (_attend==null) {
				//初始化本日考勤
				addAttendBatch(date);
			}
		}else {
			holiday_note = _holiday.getHoliday_note();
		}
		
		return holiday_note;
	}
	
	/**
	 * @Title: checkAttends
	 * @Description: 检查日期段考勤
	 * @param date1
	 * @param date2
	 * @return void
	 */
	public void checkAttends(String date1,String date2) {
		if (!StringUtil.isEmptyString(date1)) {
			Date curDate = DateUtil.getDate(DateUtil.getCurDate());
			Date startDate = DateUtil.getDate(date1);
			Date endDate = DateUtil.getDate(date2);
			if (DateUtil.compareDateStr(curDate,endDate)>0) {
				endDate = curDate;
			}
			for (Date _date = startDate; DateUtil.compareDateStr(_date, endDate)>=0; _date=DateUtil.getDateAfter(_date, 1)) {
				checkAttendEveryDay(DateUtil.dateToDateString(_date));
			}
		}
	}
	
	/**
	 * @Title: addAttend
	 * @Description: 添加考勤
	 * @param attend
	 * @return void
	 */
	public void addAttend(Attend attend) {
		
		//查询本日考勤是否存在
		checkAttendToday();
		
		//打卡人
		User admin = (User)Param.getSession("admin");
		attend.setUser_id(admin.getUser_id());
		//打卡时间
		attend.setAttend_time(DateUtil.getDate(DateUtil.getCurDateTime()));
		//打卡日期
		attend.setAttend_date(DateUtil.getDate(DateUtil.getCurDate()));
		//上午or下午
		String attend_hour_second = attend.getAttend_timeDesc().substring(11, 16).replaceAll(":", "");
		attend.setAttend_lesson(Integer.parseInt(attend_hour_second)>1200?2:1);
		//签到状态
		Config config = configDao.getConfig(new Config());
		String config_date1 = config.getConfig_date1().replaceAll(":", "");
		String config_date2 = config.getConfig_date2().replaceAll(":", "");
		String config_date = config_date1;
		//上班签到判断
		if (attend.getAttend_lesson()==1) {
			attend.setAttend_type(2);
			if (Integer.parseInt(attend_hour_second) > Integer.parseInt(config_date)) {
				attend.setAttend_type(3);
			}
		}
		//下班签到判断
		else if (attend.getAttend_lesson()==2) {
			config_date = config_date2;
			attend.setAttend_type(2);
			if (Integer.parseInt(attend_hour_second) < Integer.parseInt(config_date)) {
				attend.setAttend_type(6);
			}
		}
		
		
		attendDao.updateAttendType(attend);
		
	}
	
	/**
	 * @Title: addAttendBatch
	 * @Description: 初始化本日考勤
	 * @param attend
	 * @return void
	 */
	public void addAttendBatch() {
		
		//初始化本日考勤
		attendDao.addAttendBatch();
		//根据请假记录更新本日考勤
		attendDao.updateAttendTypeBatchLeave();
		//根据离岗记录更新本日考勤
		attendDao.updateAttendTypeBatchPost();
		
	}
	
	public void addAttendBatch(String date) {
		
		//初始化本日考勤
		attendDao.addAttendBatch(date,1);
		//根据请假记录更新本日考勤
		attendDao.updateAttendTypeBatchLeave(date);
		//根据离岗记录更新本日考勤
		attendDao.updateAttendTypeBatchPost(date);
		
	}
	
	public void addAttendBatchUser(String date, int user_id) {
		
		//初始化本日考勤
		attendDao.addAttendBatch(date,user_id,1);
		//根据请假记录更新本日考勤
		attendDao.updateAttendTypeBatchLeave(date,user_id);
		//根据离岗记录更新本日考勤
		attendDao.updateAttendTypeBatchPost(date,user_id);
		
	}
	
	/**
	 * @Title: updateAttendType
	 * @Description: 更新考勤
	 * @param attend
	 * @return void
	 */
	public void updateAttendType(Attend attend) {
		
		attendDao.updateAttendType(attend);
		
	}
	
	/**
	 * @Title: updateAttendTypeBatchUser
	 * @Description: 更新考勤
	 * @param attend
	 * @return void
	 */
	public void updateAttendTypeBatchUser(Attend attend) {
		
		attendDao.updateAttendTypeBatchUser(attend);
		
	}

	/**
	 * @Title: delAttend
	 * @Description: 删除考勤信息
	 * @param attend
	 * @return void
	 */
	public void delAttends(Attend attend) {
		
		attendDao.delAttends(attend.getIds().split(","));
		
	}
	
	/**
	 * @Title: listFills
	 * @Description: 补签记录查询
	 * @param fill
	 * @return List<Fill>
	 */
	public List<Fill> listFills(Fill fill, int[] sum) {
		
		if (sum != null) {
			sum[0] = fillDao.listFillsCount(fill);
		}
		List<Fill> fills = fillDao.listFills(fill);

		
		return fills;
	}

	/**
	 * @Title: queryFill
	 * @Description: 补签记录查询
	 * @param fill
	 * @return Fill
	 */
	public Fill queryFill(Fill fill) {
		
		Fill _fill = fillDao.getFill(fill);
		
		return _fill;
	}

	/**
	 * @Title: addFill
	 * @Description: 添加补签记录
	 * @param fill
	 * @return void
	 */
	public void addFill(Fill fill) {
		
		//补签人
		User admin = (User)Param.getSession("admin");
		fill.setUser_id(admin.getUser_id());
		fill.setUser(admin);
		fill.setFill_flag(1);
		fillDao.addFill(fill);
		
	}

	/**
	 * @Title: updateFill
	 * @Description: 更新补签记录信息
	 * @param fill
	 * @return void
	 */
	public void updateFill(Fill fill) {
		
		//更新补签记录信息
		fillDao.updateFill(fill);
		if (fill.getFill_flag()!=null && fill.getFill_flag()==2) {
			//审核通过
			fill = fillDao.getFill(fill);
			Attend attend = new Attend();
			attend.setUser_id(fill.getUser().getUser_id());
			attend.setAttend_date(fill.getFill_date());
			attend.setAttend_lesson(fill.getFill_lesson());
			attend.setAttend_type(2);
			attendDao.updateAttendType(attend);
		}
		
	}

	/**
	 * @Title: delFill
	 * @Description: 删除补签记录信息
	 * @param fill
	 * @return void
	 */
	public void delFills(Fill fill) {
		
		fillDao.delFills(fill.getIds().split(","));
		
	}
	
	/**
	 * @Title: listLeaves
	 * @Description: 请假记录查询
	 * @param leave
	 * @return List<Leave>
	 */
	public List<Leave> listLeaves(Leave leave, int[] sum) {
		
		if (sum != null) {
			sum[0] = leaveDao.listLeavesCount(leave);
		}
		List<Leave> leaves = leaveDao.listLeaves(leave);

		
		return leaves;
	}

	/**
	 * @Title: queryLeave
	 * @Description: 请假记录查询
	 * @param leave
	 * @return Leave
	 */
	public Leave queryLeave(Leave leave) {
		
		Leave _leave = leaveDao.getLeave(leave);
		
		return _leave;
	}

	/**
	 * @Title: addLeave
	 * @Description: 添加请假记录
	 * @param leave
	 * @return void
	 */
	public void addLeave(Leave leave) {
		
		//请假人
		User admin = (User)Param.getSession("admin");
		leave.setUser_id(admin.getUser_id());
		leave.setUser(admin);
		leave.setLeave_flag(1);
		//添加请假记录
		leaveDao.addLeave(leave);
		
	}

	/**
	 * @Title: updateLeave
	 * @Description: 更新请假记录信息
	 * @param leave
	 * @return void
	 */
	public void updateLeave(Leave leave) {
		
		//更新请假记录
		leaveDao.updateLeave(leave);
		if (leave.getLeave_flag()!=null && leave.getLeave_flag()==2) {
			leave = leaveDao.getLeave(leave);
			//审核通过
			Attend attend = new Attend();
			attend.setUser_id(leave.getUser().getUser_id());
			attend.setAttend_date1(leave.getLeave_date1Desc().replaceAll("-", "")+leave.getLeave_lesson1());
			attend.setAttend_date2(leave.getLeave_date2Desc().replaceAll("-", "")+leave.getLeave_lesson2());
			attend.setAttend_type(4);
			attendDao.updateAttendTypeBatchUser(attend);
		}
		
	}

	/**
	 * @Title: delLeave
	 * @Description: 删除请假记录信息
	 * @param leave
	 * @return void
	 */
	public void delLeaves(Leave leave) {
		
		leaveDao.delLeaves(leave.getIds().split(","));
		
	}
	
	/**
	 * @Title: listPosts
	 * @Description: 离岗记录查询
	 * @param post
	 * @return List<Post>
	 */
	public List<Post> listPosts(Post post, int[] sum) {
		
		if (sum != null) {
			sum[0] = postDao.listPostsCount(post);
		}
		List<Post> posts = postDao.listPosts(post);

		
		return posts;
	}

	/**
	 * @Title: queryPost
	 * @Description: 离岗记录查询
	 * @param post
	 * @return Post
	 */
	public Post queryPost(Post post) {
		
		Post _post = postDao.getPost(post);
		
		return _post;
	}

	/**
	 * @Title: addPost
	 * @Description: 添加离岗记录
	 * @param post
	 * @return void
	 */
	public void addPost(Post post) {
		
		//离岗人
		User admin = (User)Param.getSession("admin");
		post.setUser_id(admin.getUser_id());
		post.setUser(admin);
		post.setPost_flag(1);
		//添加离岗记录
		postDao.addPost(post);
		
	}

	/**
	 * @Title: updatePost
	 * @Description: 更新离岗记录信息
	 * @param post
	 * @return void
	 */
	public void updatePost(Post post) {
		
		//更新离岗记录
		postDao.updatePost(post);
		if (post.getPost_flag()!=null && post.getPost_flag()==2) {
			//审核通过
			post = postDao.getPost(post);
			Attend attend = new Attend();
			attend.setUser_id(post.getUser().getUser_id());
			attend.setAttend_date1(post.getPost_date1Desc().replaceAll("-", "")+post.getPost_lesson1());
			attend.setAttend_date2(post.getPost_date2Desc().replaceAll("-", "")+post.getPost_lesson2());
			attend.setAttend_type(5);
			attendDao.updateAttendTypeBatchUser(attend);
		}
		
	}

	/**
	 * @Title: delPost
	 * @Description: 删除离岗记录信息
	 * @param post
	 * @return void
	 */
	public void delPosts(Post post) {
		
		postDao.delPosts(post.getIds().split(","));
		
	}
	
	/**
	 * @Title: listHolidays
	 * @Description: 节假日查询
	 * @param holiday
	 * @return List<Holiday>
	 */
	public List<Holiday> listHolidays(Holiday holiday, int[] sum) {
		
		if (sum != null) {
			sum[0] = holidayDao.listHolidaysCount(holiday);
		}
		List<Holiday> holidays = holidayDao.listHolidays(holiday);

		
		return holidays;
	}
 
	/**
	 * @Title: queryHoliday
	 * @Description: 节假日查询
	 * @param holiday
	 * @return Holiday
	 */
	public Holiday queryHoliday(Holiday holiday) {
		
		Holiday _holiday = holidayDao.getHoliday(holiday);
		
		return _holiday;
	}
	
	/**
	 * @Title: addHoliday
	 * @Description: 添加节假日
	 * @param holiday
	 * @return void
	 */
	public void addHoliday(Holiday holiday) {
		
		//循环将节假日时间段插入数据库
		Date holiday_date1 = DateUtil.getDate(holiday.getHoliday_date1());
		Date holiday_date2 = DateUtil.getDate(holiday.getHoliday_date2());
		for (Date i = holiday_date1; DateUtil.compareDateStr(i, holiday_date2)>=0; i=DateUtil.getDateAfter(i, 1)) {
			Holiday _holiday = new Holiday();
			_holiday.setHoliday_note(holiday.getHoliday_note());
			_holiday.setHoliday_date(DateUtil.dateToDateString(i));
			holidayDao.addHoliday(_holiday);
		}
		
	}

	/**
	 * @Title: updateHoliday
	 * @Description: 更新节假日信息
	 * @param holiday
	 * @return void
	 */
	public void updateHoliday(Holiday holiday) {
		
		holidayDao.updateHoliday(holiday);
		
	}

	/**
	 * @Title: delHoliday
	 * @Description: 删除节假日信息
	 * @param holiday
	 * @return void
	 */
	public void delHolidays(Holiday holiday) {
		
		holidayDao.delHolidays(holiday.getIds().split(","));
		
	}

	public DeptDao getDeptDao() {
		return deptDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public AttendDao getAttendDao() {
		return attendDao;
	}

	public FillDao getFillDao() {
		return fillDao;
	}

	public LeaveDao getLeaveDao() {
		return leaveDao;
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public ConfigDao getConfigDao() {
		return configDao;
	}

	public HolidayDao getHolidayDao() {
		return holidayDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAttendDao(AttendDao attendDao) {
		this.attendDao = attendDao;
	}

	public void setFillDao(FillDao fillDao) {
		this.fillDao = fillDao;
	}

	public void setLeaveDao(LeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

	public void setHolidayDao(HolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}
}
