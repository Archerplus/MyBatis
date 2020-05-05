package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;

class MyBatisTest {

	/*
	 * 1.����xml�����ļ�(ȫ�������ļ�)����һ��SqlSessionFactory���� ������ԴһЩ���л�����Ϣ
	 * 2.SQLӳ���ļ�:������ÿһ��SQL,�Լ�SQL�ķ�װ����ȵ� SqlSessionFactory 3.��SQLӳ���ļ�ע����ȫ�������ļ��� 4.д����
	 * 1).����ȫ�������ļ��õ�SqlSessionFactory 2).ʹ��sqlSession����,��ȡ��sqlsession����ʹ������ִ����ɾ�Ĳ�
	 * һ��sqlSession���Ǵ���h�����ݿ��һ�λỰ,����ر�.
	 * 3).ʹ��sql��Ψһ��ʶ������MyBatisִ���Ǹ�SQL.SQL���Ǳ�����SQLӳ���ļ��е�
	 * 4).mapper�ӿ�û��ʵ����,����mybatis��Ϊ����ӿ�����һ���������.
	 * 	(���ӿں�xml�����ļ����ж�̬��)
	 * EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.getClass);
	 * 5.������Ҫ�������ļ�
	 * 		mybatis��ȫ�������ļ�: �������ݿ����ӳ���Ϣ,�����������Ϣ��...ϵͳ���л�����
	 * 		sqlӳ���ļ�: ������ÿһ��sql����ӳ����Ϣ.
	 * 			��sql��ȡ����
	 */
	@Test
	void test() throws IOException {
		// 2.��ȡsqlSessionʵ��,��ֱ��ִ���Ѿ�ӳ���SQL���
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		// SQL����Ψһ��ʶ��:statement Unique identifier matching the statement to use.
		// ִ��SQL�����Ҫʹ�õĲ���:parameter A parameter object to pass to the statement.
		try {
			Employee employee = (Employee) openSession.selectOne("com.atguigu.mybatis.dao.EmployeeMapper.getEmpById",
					1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	@Test
	public void test1() throws IOException {
		// 1.��ȡsqlSessionFactory����
//		 2.
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3.��ȡ�ӿڵ�ʵ�������
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			//��Ϊ�ӿ��Զ��Ĵ���һ���������
			//�������ȥִ����ɾ�Ĳ�
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}
	@Test
	public void test2() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperAnnotation employeeMapperAnnotation = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = employeeMapperAnnotation.getEmpById(1);
			System.out.println(employee);
		}finally {
			openSession.close();
		}
	}
}
