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
 * 1.��ȡsqlSessionFactory����
 * 		�����ļ���ÿһ����Ϣ������Configuration�У����ذ���Configuration��DefaultSqlSession��
 * 		ע�⣺MapperStatement������һ����ɾ�Ĳ����ϸ��Ϣ
 * 		
 * 2.��ȡsqlSession����
 * 3.��ȡ�ӿڵĴ������(MapperProxy)
 * 4.ִ����ɾ�Ĳ鷽��
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
//		// ָ�������ļ�
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
			//xxxExample���Ƿ�װ��ѯ������
			//�����ض���������
			//��ѯ����Ϊnullʱ�����ǲ�ѯ����������Ϣ
			//1.��ѯ����
//			List<Employee> list = mapper.selectByExample(null);
//			for(Employee l:list) {
//				System.out.println(l.getId());
//			}
			
			//2.��ѯԱ��������e��ĸ�ģ��Ա���f��
			//��װԱ����ѯ������example
			EmployeeExample example = new EmployeeExample();
			//����һ��Criteria,Criteria���Ƿ�װ��ѯ������
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