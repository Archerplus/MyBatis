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
	 * 1.根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象 有数据源一些运行环境信息
	 * 2.SQL映射文件:配置了每一个SQL,以及SQL的封装规则等等 SqlSessionFactory 3.将SQL映射文件注册在全局配置文件中 4.写代码
	 * 1).根据全局配置文件得到SqlSessionFactory 2).使用sqlSession工厂,获取到sqlsession对象使用它来执行增删改查
	 * 一个sqlSession就是代表h和数据库的一次会话,用完关闭.
	 * 3).使用sql的唯一标识来告诉MyBatis执行那个SQL.SQL都是保存在SQL映射文件中的
	 * 4).mapper接口没有实现类,但是mybatis会为这个接口生成一个代理对象.
	 * 	(将接口和xml配置文件进行动态绑定)
	 * EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.getClass);
	 * 5.两个重要的配置文件
	 * 		mybatis的全局配置文件: 包含数据库连接池信息,事务管理器信息等...系统运行环境等
	 * 		sql映射文件: 保存了每一个sql语句的映射信息.
	 * 			将sql抽取出来
	 */
	@Test
	void test() throws IOException {
		// 2.获取sqlSession实例,能直接执行已经映射的SQL语句
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		// SQL语句的唯一标识符:statement Unique identifier matching the statement to use.
		// 执行SQL语句需要使用的参数:parameter A parameter object to pass to the statement.
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
		// 1.获取sqlSessionFactory对象
//		 2.
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3.获取接口的实现类对象
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1);
			//会为接口自动的创建一个代理对象
			//代理对象去执行增删改查
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
