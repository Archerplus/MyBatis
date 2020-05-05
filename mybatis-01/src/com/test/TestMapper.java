package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.beans.UserInfo;
import com.mapper.UserMapper;

public class TestMapper {
	private static SqlSessionFactory sqlsessionFactory;
	
	static {
		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlsessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	public static void main(String[] args) {
//		testAdd();
//		testDel();
//		testUpdate();
//		testGetUser();
//		testUpdate();
		testGetAllUser();
	}
	
	public static void testAdd() {
		SqlSession session = sqlsessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		UserInfo user = new UserInfo();
		user.setId(21);
		user.setNote("cause");
		user.setUserName("org");
		user.setPassword("junit");
		userMapper.addUser(user);
		session.commit();
		session.close();
	}
	public static void testDel() {
		SqlSession session = sqlsessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		userMapper.delUserById(5);
		session.commit();
		session.close();
	}
	public static void testUpdate() {
		SqlSession session = sqlsessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		UserInfo user = new UserInfo();
		user = userMapper.getUserById(20);
		System.out.println(user);
		user.setUserName("org");
		user.setPassword("com");
		user.setNote("entity");
		userMapper.updateUser(user);
	}
	public static void testGetUser() {
		SqlSession session = sqlsessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		UserInfo userInfo = userMapper.getUserById(1);
		System.out.println(userInfo);
		session.close();
	}
	public static void testGetAllUser() {
		SqlSession session = sqlsessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		List<UserInfo> list = userMapper.getAllUserInfo();
		Iterator<UserInfo> iterator = list.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}
