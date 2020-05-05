package com.dao;

import java.util.List;

import com.beans.UserInfo;

public interface IUserDao {
	
	//添加用户
	public int addUser(UserInfo user);
	//删除用户
	public int delUserById(int id);
	//修改用户
	public int updateUser(UserInfo user);
	//查询用户
	public UserInfo getUserById(int id);
	
	//查询所有用户
	public List<UserInfo> getUserList();
}
