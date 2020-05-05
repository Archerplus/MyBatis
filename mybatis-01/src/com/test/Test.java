package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.beans.UserInfo;

public class Test {
	public static void main(String[] args) throws IOException {
		show();
	}

	public static void test1() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		UserInfo one = (UserInfo) openSession.selectOne("test.getUserById", 2);
		System.out.println(one);
		openSession.close();
	}
	public static void test2() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		List<Object> list = openSession.selectList("test.getUserByUsername","%e%");
		Iterator<Object> iterator = list.iterator();
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
	public static SqlSessionFactory getSqlSessionFactory() throws IOException {
		// �����ļ��ط�ʽ�ܱ�ֻ֤Ҫ����·���¾��ܼ��ص�
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	static void testAdd() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		UserInfo user = new UserInfo();
		user.setNote("2019_10_11_1");
		user.setUserName("chang");
		user.setPassword("ren");
		user.setId(19);
		openSession.insert("test.addUser",user);
		openSession.commit();
		openSession.close();
	}
	static void testDel() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		openSession.delete("test.delUserById", 19);
		openSession.commit();
		openSession.close();
	}
	static void testUpdate() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		UserInfo one = (UserInfo)openSession.selectOne("test.getUserById", 2);
		one.setNote("iphone");
		one.setUserName("xs");
		one.setPassword("8");
		openSession.update("test.updateUser", one);
		openSession.commit();
		openSession.close();
	}
	public static void show() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		System.out.println("�������û���������");
		Scanner in = new Scanner(System.in);
		UserInfo user = new UserInfo();
		String userName = in.next();
		String password = in.next();
		user.setUserName(userName);
		user.setPassword(password);
		UserInfo userInfo = (UserInfo)openSession.selectOne("test.login", user);
		if(userInfo!=null) {
			System.out.println("��½�ɹ�");
		}else {
			System.out.println("��½ʧ��");
		}
	}
}
