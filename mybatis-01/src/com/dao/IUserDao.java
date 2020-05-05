package com.dao;

import java.util.List;

import com.beans.UserInfo;

public interface IUserDao {
	
	//����û�
	public int addUser(UserInfo user);
	//ɾ���û�
	public int delUserById(int id);
	//�޸��û�
	public int updateUser(UserInfo user);
	//��ѯ�û�
	public UserInfo getUserById(int id);
	
	//��ѯ�����û�
	public List<UserInfo> getUserList();
}
