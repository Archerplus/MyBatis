package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;
import com.atguigu.mybatis.dao.EmployeeMapperDynamicSQL;
import com.atguigu.mybatis.dao.EmployeeMapperPlus;

class MyBatisTest {

	/*
	 * 1. ��ȡsqlSessionFactory����
	 * 		�����ļ���ÿһ����Ϣ��������Configuration�У����ذ���Configuration��DefaultSqlSession
	 * 		ע�⣺MapperStatement������һ����ɾ�Ĳ����ϸ��Ϣ
	 * 		
	 * 2. ��ȡsqlSession����
	 * 		����һ��DefaultSqlSession���󣬰���Executor��Configutation;
	 * 		��һ���ᴴ��Executor����
	 * 3. ��ȡ�ӿڵĴ������(Mapper.class)
	 * 4.ִ����ɾ�Ĳ鷽��
	 * 
	 * 
	 * �ܽ᣺
	 * 	1�����������ļ���ȫ�֣�sqlӳ�䣩��ʼ��Configuration����
	 *  2������һ��DefaultSqlSession�������������Configuration�Լ����ǵ�
	 *  	Executor������ȫ�������ļ��е�defaultExecutorType��������Ӧ��Executor��
	 *  3��DefaultSqlSession.getMapper()���õ�Mapper�ӿڶ�Ӧ��MapperProxy
	 *  4��MapperProxy�����У�DefaultSqlSession����
	 *  5��ִ����ɾ�Ĳ鷽����
	 *  	1.����DefaultSqlSession����ɾ�Ĳ飨Executor��
	 *  	2.�ᴴ��һ��StatementHandler����,(ͬʱҲ�ᴴ����ParameterHandler��ResultSetHandler)
	 *  	3.����StatementHandler��Ԥ��������Լ����ò���ֵ
	 *  		ʹ��ParameterHandler����SQL���ò���
	 *  	4.����StatementHandler����ɾ�Ĳ鷽��
	 *  	5.ResultSetHandler����װ��� 
	 *  ע�⣺
	 *  	�Ĵ����ÿ��������ʱ����һ��interceptorChain.pluginAll(paramenterHandler)
	 */
	
	@Test
	void test() throws IOException {

		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(28);
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

}
