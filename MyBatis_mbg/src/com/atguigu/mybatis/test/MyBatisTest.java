package com.atguigu.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.bean.EmployeeExample;
import com.atguigu.mybatis.bean.EmployeeExample.Criteria;
import com.atguigu.mybatis.dao.EmployeeMapper;

/*
 * 1.获取sqlSessionFactory对象
 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession：
 * 		注意：MapperStatement：代表一个增删改查的详细信息
 * 		
 * 2.获取sqlSession对象
 * 3.获取接口的代理对象(MapperProxy)
 * 4.执行增删改查方法
 * 
 */


public class MyBatisTest {

//	selectByPrimaryKey
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
//	public static void main(String[] args) throws Exception{
//		List<String> warnings = new ArrayList<String>();
//		boolean overwrite = true;
//		// 指定配置文件
//		File configFile = new File("./conf/generatorConfig.xml");
//		ConfigurationParser cp = new ConfigurationParser(warnings);
//		Configuration config = cp.parseConfiguration(configFile);
//		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//		myBatisGenerator.generate(null);
//	}
	
	@Test
	public void test() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			//xxxExample就是封装查询条件的
			//根据特定条件查找
			//查询条件为null时，就是查询表中所有信息
			//1.查询所有
//			List<Employee> list = mapper.selectByExample(null);
//			for(Employee l:list) {
//				System.out.println(l.getId());
//			}
			
			//2.查询员工名字有e字母的，性别是f的
			//封装员工查询条件的example
			EmployeeExample example = new EmployeeExample();
			//创建一个Criteria,Criteria就是封装查询条件的
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%e%").andGenderEqualTo("f");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andEmailLike("%f%");
			example.or(criteria2);
			
			
			List<Employee> list2 = mapper.selectByExample(example);
			for(Employee ep:list2) {
				System.out.println(ep);
			}
//			Employee employee = mapper.selectByPrimaryKey(28);
//			System.out.println(employee.toString());

			//			EmployeeExample employeeExample = new EmployeeExample();
//			employeeExample.or().andIdEqualTo(26).andDIdGreaterThan(56);
//			Criteria criteria = employeeExample.createCriteria();
		}finally {
			openSession.close();
		}
	}
	public MyBatisTest() {
		
	}
//	public static void main(String[] args) {
//		System.out.println("hello");
//	}
}