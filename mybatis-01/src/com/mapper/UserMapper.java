package com.mapper;

import java.util.List;

import com.beans.UserInfo;

public interface UserMapper {
	void addUser(UserInfo user);

	int delUserById(int id);

	int updateUser(UserInfo user);

	UserInfo getUserById(int id);

	List<UserInfo> getAllUserInfo();
}
