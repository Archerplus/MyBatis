package com.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.beans.UserInfo;

public class UserDaoImple implements IUserDao{
	private SqlSessionFactory sqlSessionFactory;
	
	public UserDaoImple(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public int addUser(UserInfo user) {
		SqlSession openSession = sqlSessionFactory.openSession();
		openSession.insert("test.addUser",user);
		openSession.commit();
		openSession.close();
		return 0;
	}

	@Override
	public int delUserById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(UserInfo user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserInfo getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserInfo> getUserList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static SqlSessionFactory getSqlSessionFactory() throws IOException {
		// 这样的加载方式能保证只要在类路径下就能加载到
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
}
