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

	/*
	 * ���ԭ��
	 * ���Ĵ���󴴽���ʱ��
	 * 1.ÿ�����������Ķ�����ֱ�ӷ��صģ�����
	 * 		interceptorChain.pluginAll(paramenterHandler);
	 * 2.��ȡ�����е�Interceptor�����������������Ҫʵ�ֵĽӿڣ�
	 * 		����interceptor.plugin(target);����target��װ��Ķ���
	 * 3.������ƣ����ǿ���ʹ�ò��ΪĿ����󴴽�һ���������AOP����������ı�̷�ʽ��
	 * 		���ǵĲ������Ϊ�Ĵ���󴴽����������
	 * 		�������Ϳ������ص��Ĵ�����ÿһ��ִ�з���
	 * 		
	 * public Object pluginAll(Object object){
	 * 	for(Interceptor interceptor : interceptors){
	 * 		target = interceptor.plugin(target);
	 *  }
	 *  return target;
	 * }
	 */
	
	/*
	 * ����ı�д
	 * 1.��дInterceptor��ʵ����
	 * 2.ʹ��@Interceptsע����ɲ��ǩ��
	 * 3.��д�õĲ��ע����ȫ�������ļ���
	 */
	@Test
	public void testPlugin() {
		
	}
}
