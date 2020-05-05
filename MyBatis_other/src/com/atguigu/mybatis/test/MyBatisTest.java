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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	 */
	
	@Test
	void test() throws IOException {

		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			/*
			 * Employee employee = mapper.getEmpById(28); System.out.println(employee);
			 */
			Page<Object> page = PageHelper.startPage(5,1); 
			List<Employee> list = mapper.getEmps();
			//传入要连续显示多少页
			PageInfo<Employee> pageInfo = new PageInfo<Employee>(list,5);
//			PageInfo<Employee> pageInfo = new PageInfo<Employee>(list);
			for(Employee emp:list) {
				System.out.println(emp);
			}
//			System.out.println("当前页码：" + page.getPageNum());
//			System.out.println("总记录数：" + page.getTotal());
//			System.out.println("每页的记录数：" + page.getPageSize());
//			System.out.println("总页码：" + page.getPages());
			System.out.println("当前页码：" + pageInfo.getPageNum());
			System.out.println("总记录数：" + pageInfo.getTotal());
			System.out.println("每页的记录数：" + pageInfo.getPageSize());
			System.out.println("总页码：" + pageInfo.getPages());
			System.out.println("是否第一页：" + pageInfo.isIsFirstPage());
			System.out.println("是否最后一页：" + pageInfo.isIsLastPage());
			System.out.println("连续显示的页码：");
			int[] nums = pageInfo.getNavigatepageNums();
			int len = nums.length;
			for(int i = 0;i < len;i++) {
				System.out.println(nums[i]);
			}
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
	 * 插件原理
	 * 在四大对象创建的时候
	 * 1.每个创建出来的对象不是直接返回的，而是
	 * 		interceptorChain.pluginAll(paramenterHandler);
	 * 2.获取到所有的Interceptor（拦截器）（插件需要实现的接口）
	 * 		调用interceptor.plugin(target);返回target包装后的对象
	 * 3.插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面的编程方式）
	 * 		我们的插件可以为四大对象创建出代理对象
	 * 		代理对象就可以拦截到四大对象的每一个执行方法
	 * 		
	 * public Object pluginAll(Object object){
	 * 	for(Interceptor interceptor : interceptors){
	 * 		target = interceptor.plugin(target);
	 *  }
	 *  return target;
	 * }
	 */
	
	/*
	 * 插件的编写
	 * 1.编写Interceptor的实现类
	 * 2.使用@Intercepts注解完成插件签名
	 * 3.将写好的插件注册在全局配置文件中
	 */

}
