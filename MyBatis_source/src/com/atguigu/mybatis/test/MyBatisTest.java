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
	 * 1. 获取sqlSessionFactory对象：
	 * 		解析文件的每一个信息都保存在Configuration中，返回包含Configuration的DefaultSqlSession
	 * 		注意：MapperStatement：代表一个增删改查的详细信息
	 * 		
	 * 2. 获取sqlSession对象
	 * 		返回一个DefaultSqlSession对象，包含Executor和Configutation;
	 * 		这一步会创建Executor对象
	 * 3. 获取接口的代理对象(Mapper.class)
	 * 4.执行增删改查方法
	 * 
	 * 
	 * 总结：
	 * 	1、根据配置文件（全局，sql映射）初始化Configuration对象
	 *  2、创建一个DefaultSqlSession对象，它里面包含Configuration以及我们的
	 *  	Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
	 *  3、DefaultSqlSession.getMapper()，拿到Mapper接口对应的MapperProxy
	 *  4、MapperProxy里面有（DefaultSqlSession）；
	 *  5、执行增删改查方法；
	 *  	1.调用DefaultSqlSession的增删改查（Executor）
	 *  	2.会创建一个StatementHandler对象,(同时也会创建出ParameterHandler和ResultSetHandler)
	 *  	3.调用StatementHandler的预编译参数以及设置参数值
	 *  		使用ParameterHandler来给SQL设置参数
	 *  	4.调用StatementHandler的增删改查方法
	 *  	5.ResultSetHandler来封装结果 
	 *  注意：
	 *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(paramenterHandler)
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
