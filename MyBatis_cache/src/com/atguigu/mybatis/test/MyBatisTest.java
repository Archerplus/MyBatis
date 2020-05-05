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

	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}

	// 两级缓存
	// 一级缓存：（本地缓存）sqlSession级别的缓存，一级缓存是一直开启的；我们无法去关闭;缓存其实就是sqlSession级别的一个map，我们会将查询到的所有数据都放到这个map中
	//		也就是说一个sqlSession对象拥有一个属于自己的缓存，新的sqlSession对象就拥有新的一级缓存
	//			与数据库同一次会话期间查询到的数据会放在本地缓存中
	//			以后如果需要获取到相同的数据，直接从缓存中去拿，没必须再去查询数据库
	//
	//			一级缓存失效的情况(没有使用到当前一级缓存的情况，效果就是没有还需要向数据库发出查询请求)
	//			1、sqlSession不同	
	//			2、sqlSession相同、查询条件不同。		
	//			3、sqlSession相同，但是两次查询期间执行了增删改操作(这次操作可能就会对当前数据有影响)
	//			4、sqlSession相同，手动清楚了一级缓存（缓存清空） session.clearCache();清空缓存
	// 二级缓存：（全局缓存）：基于namespace级别的缓存，一个namespace对应一个二级缓存
	//			工作机制
	//			1、一个会话，查询一条数据，这个数据就会被放在当前会话的一级缓存中；
	//			2、如果会话关闭；一级缓存的数据会被保存到二级缓存中；新的会话查询信息就可以参照二级缓存中去寻找内容
	//			3、sqlSession===EmployeerMapper==>>Employee
	//							DepartmentMapper==>>Department
	//							不同namespace查出的数据会放在自己对应的缓存（map）中
	//							效果：数据会从二级缓存中获取
	//								查出的数据都会被默认先放在一级缓存中
	//								只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	//			使用；
	//				1）、开启全局二级缓存配置：<setting name="cacheEnable" value="true" />
	//				2）、去mapper.xml中配置使用二级缓存
	//					<cache></cache>
	//				3）、我们的pojo需要实现序列化接口
	//				
//					和缓存有关的一些设置以及一些属性
//					1）、cacheEnabled=true	false关闭缓存（关闭二级缓存）（一级缓存一直存在）
//					2）、每个select标签都有useCache="true".
//							false：不使用缓存（一级缓存依然使用，二级缓存不使用）
//					3）、每个增删改标签的flushCache="true"  一级二级都会清除
//						意思就是每次执行完毕增删改之后就会清除缓存
//						测试：flushCache="true"；清空一级缓存；二级缓存也会被清除
//						查询标签：flushCache="false";
//							如果我们改为true，每次查询之后都会清空缓存，缓存是没有被使用的
//					4）、sqlSession.clearCache();只是清除当前session的一级缓存
//					5）、localCacheScope：本地缓存作用域；（一级缓存session）；当前会话的所有数据保存在会话的缓存中
//						STATEMENT:可以禁用一级缓存；
//				第三方缓存整合：
//					1）、导入第三方jar包即可
//					2）、导入与第三方缓存整合的适配包，官方有；
//					3）、mapper.xml文件中使用自定义缓存
//					<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
//	
	//@Test
	public void testFirstLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(5);
			Employee employee2 = mapper.getEmpById(5);
			System.out.println(employee == employee2);
			//输出结果为true,因为在第一次查询id为5的员工的时候，就将这个数据存放到缓存中去了
			//当我们再次进行查找的时候,我们发现缓存中已经存在id为5的员工,就直接从缓存中去拿
			//而不必从数据库中获取,所以打印输出的结果为true
		}finally {
			session.close();
		}
	}
	
	@Test
	public void testSecondLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		SqlSession session2 = sqlSessionFactory.openSession();
		try {
			//1.
			EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
			
			Employee employee = mapper.getEmpById(5);
			System.out.println(employee);
			session.close();
			
			//第二次查询是从二级缓存中拿到的数据，并没有发起新的SQL
			Employee employee2 = mapper2.getEmpById(5);
			System.out.println(employee2);
			session2.close();
		}finally {
			
		}
	}
}


//缓存的顺序：先查看二级缓存里面的数据，然后再查看一级缓存的数据，最后如果还没有找到就从数据库中去查找