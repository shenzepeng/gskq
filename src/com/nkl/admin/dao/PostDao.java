package com.nkl.admin.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkl.admin.domain.Post;
import com.nkl.common.dao.BaseDao;
import com.nkl.common.util.StringUtil;

public class PostDao extends BaseDao {

	public void addPost(Post post){
		super.add(post);
	}

	public void delPost(Integer post_id){
		super.del(Post.class, post_id);
	}

	public void delPosts(String[] post_ids){
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i <post_ids.length; i++) {
			sBuilder.append(post_ids[i]);
			if (i !=post_ids.length-1) {
				sBuilder.append(",");
			}
		}
		String hql = "DELETE FROM Post WHERE post_id IN(" +sBuilder.toString()+")";

		Object[] params = null;

		super.executeUpdateHql(hql, params);
	}

	public void updatePost(Post post){
		Post _post = (Post)super.get(Post.class, post.getPost_id());
		if (post.getPost_date1()!=null) {
			_post.setPost_date1(post.getPost_date1());
		}
		if (post.getPost_lesson1()!=null && post.getPost_lesson1()!=0) {
			_post.setPost_lesson1(post.getPost_lesson1());
		}
		if (post.getPost_date2()!=null) {
			_post.setPost_date2(post.getPost_date2());
		}
		if (post.getPost_lesson2()!=null && post.getPost_lesson2()!=0) {
			_post.setPost_lesson2(post.getPost_lesson2());
		}
		if (!StringUtil.isEmptyString(post.getPost_reason())) {
			_post.setPost_reason(post.getPost_reason());
		}
		if (post.getPost_flag()!=null && post.getPost_flag()!=0) {
			_post.setPost_flag(post.getPost_flag());
		}
		super.update(_post);
	}

	@SuppressWarnings("rawtypes")
	public Post getPost(Post post){
		Post _post=null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Post a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (post.getPost_id()!=null && post.getPost_id()!=0) {
			sBuilder.append(" and post_id = ? ");
			paramsList.add(post.getPost_id());
		}
		if (post.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + post.getUser_id() +" ");
		}
		if (post.getPost_date1()!=null) {
			sBuilder.append(" and a.post_date1 = ? ");
			paramsList.add(post.getPost_date1());
		}
		if (post.getPost_lesson1()!=null && post.getPost_lesson1()!=0) {
			sBuilder.append(" and a.post_lesson1 = " + post.getPost_lesson1() +" ");
		}
		if (post.getPost_flag()!=null && post.getPost_flag()!=0) {
			sBuilder.append(" and a.post_flag = " + post.getPost_flag() +" ");
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
			_post = (Post)list.get(0);
		}

		return _post;
	}

	@SuppressWarnings("rawtypes")
	public List<Post>  listPosts(Post post){
		List<Post> posts = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("FROM Post a join fetch a.user join fetch a.user.dept WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (post.getPost_id()!=null && post.getPost_id()!=0) {
			sBuilder.append(" and post_id = ? ");
			paramsList.add(post.getPost_id());
		}
		if (post.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + post.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(post.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + post.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(post.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + post.getReal_name() +"%' ");
		}
		if (post.getPost_date1()!=null && post.getPost_date2()!=null) {
			sBuilder.append(" and ((a.post_date1 between ? and ?) or ");
			sBuilder.append("      (a.post_date2 between ? and ?)) ");
			paramsList.add(post.getPost_date1());
			paramsList.add(post.getPost_date2());
			paramsList.add(post.getPost_date1());
			paramsList.add(post.getPost_date2());
		}else {
			if (post.getPost_date1()!=null) {
				sBuilder.append(" and a.post_date1 >= ? ");
				paramsList.add(post.getPost_date1());
			}
			if (post.getPost_date2()!=null) {
				sBuilder.append(" and a.post_date1 <= ? ");
				paramsList.add(post.getPost_date2());
			}
		}
		
		if (post.getPost_flag()!=null && post.getPost_flag()!=0) {
			sBuilder.append(" and a.post_flag = " + post.getPost_flag() +" ");
		}
		if (post.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + post.getDept_id() +" ");
		}

		Object[] params = null;
		if (paramsList.size()>0) {
			params = new Object[paramsList.size()];
			for (int i = 0; i < paramsList.size(); i++) {
				params[i] = paramsList.get(i);
			}
		}

		sBuilder.append(" order by a.post_date desc,a.user.real_name,a.post_id asc");

		List list = null;
		if (post.getStart()!=-1) {
			list = super.findByPageHql(sBuilder.toString(), params, post.getStart(), post.getLimit());
		}else {
			list = super.executeQueryHql(sBuilder.toString(), params);
		}
		if (list != null && list.size() > 0) {
			posts = new ArrayList<Post>();
			for (Object object : list) {
				posts.add((Post)object);
			}
		}

		return posts;
	}

	public int  listPostsCount(Post post){
		int sum = 0;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("SELECT count(*) FROM Post a WHERE 1=1");
		List<Object> paramsList = new ArrayList<Object>();
		if (post.getPost_id()!=null && post.getPost_id()!=0) {
			sBuilder.append(" and post_id = ? ");
			paramsList.add(post.getPost_id());
		}
		if (post.getUser_id()!=0) {
			sBuilder.append(" and a.user.user_id = " + post.getUser_id() +" ");
		}
		if (!StringUtil.isEmptyString(post.getUser_no())) {
			sBuilder.append(" and a.user.user_no like '%" + post.getUser_no() +"%' ");
		}
		if (!StringUtil.isEmptyString(post.getReal_name())) {
			sBuilder.append(" and a.user.real_name like '%" + post.getReal_name() +"%' ");
		}
		if (post.getPost_date1()!=null && post.getPost_date2()!=null) {
			sBuilder.append(" and ((a.post_date1 between ? and ?) or ");
			sBuilder.append("      (a.post_date2 between ? and ?)) ");
			paramsList.add(post.getPost_date1());
			paramsList.add(post.getPost_date2());
			paramsList.add(post.getPost_date1());
			paramsList.add(post.getPost_date2());
		}else {
			if (post.getPost_date1()!=null) {
				sBuilder.append(" and a.post_date1 >= ? ");
				paramsList.add(post.getPost_date1());
			}
			if (post.getPost_date2()!=null) {
				sBuilder.append(" and a.post_date1 <= ? ");
				paramsList.add(post.getPost_date2());
			}
		}
		
		if (post.getPost_flag()!=null && post.getPost_flag()!=0) {
			sBuilder.append(" and a.post_flag = " + post.getPost_flag() +" ");
		}
		if (post.getDept_id()!=0) {
			sBuilder.append(" and a.user.dept.dept_id = " + post.getDept_id() +" ");
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
