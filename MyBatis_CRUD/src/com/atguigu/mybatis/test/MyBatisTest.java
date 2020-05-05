package com.atguigu.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.atguigu.mybatis.bean.Department;
import com.atguigu.mybatis.bean.Employee;
import com.atguigu.mybatis.dao.DepartmentMapper;
import com.atguigu.mybatis.dao.EmployeeMapper;
import com.atguigu.mybatis.dao.EmployeeMapperAnnotation;
import com.atguigu.mybatis.dao.EmployeeMapperPlus;

class MyBatisTest {

	/*
	 * 1.����xml�����ļ�(ȫ�������ļ�)����һ��SqlSessionFactory���� ������ԴһЩ���л�����Ϣ
	 * 2.SQLӳ���ļ�:������ÿһ��SQL,�Լ�SQL�ķ�װ����ȵ� SqlSessionFactory 3.��SQLӳ���ļ�ע����ȫ�������ļ��� 4.д����
	 * 1).����ȫ�������ļ��õ�SqlSessionFactory 2).ʹ��sqlSession����,��ȡ��sqlsession����ʹ������ִ����ɾ�Ĳ�
	 * һ��sqlSession���Ǵ����������ݿ��һ�λỰ,����ر�.
	 * 3).ʹ��sql��Ψһ��ʶ������MyBatisִ���Ǹ�SQL.SQL���Ǳ�����SQLӳ���ļ��е�
	 * 4).mapper�ӿ�û��ʵ����,����mybatis��Ϊ����ӿ�����һ���������. (���ӿں�xml�����ļ����ж�̬��) EmployeeMapper
	 * empMapper = sqlSession.getMapper(EmployeeMapper.getClass); 5.������Ҫ�������ļ�
	 * mybatis��ȫ�������ļ�: �������ݿ����ӳ���Ϣ,�����������Ϣ��...ϵͳ���л����� sqlӳ���ļ�: ������ÿһ��sql����ӳ����Ϣ.
	 * ��sql��ȡ����
	 */
//	@Test
	void test() throws IOException {
		// 2.��ȡsqlSessionʵ��,��ֱ��ִ���Ѿ�ӳ���SQL���
		// sqlSession��һ�����ڽ���������ݿ����������һ�����̶߳���
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

//	@Test
	public void test1() throws IOException {
		// 1.��ȡsqlSessionFactory����
//		2.
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		// 3.��ȡ�ӿڵ�ʵ�������
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
//			Employee employee = mapper.getEmpById(1);
			Employee employee = mapper.getEmpByIdAndLastName(2, "jerry");
			// ��Ϊ�ӿ��Զ��Ĵ���һ���������
			// �������ȥִ����ɾ�Ĳ�
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}

// 	@Test
	public void test2() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperAnnotation employeeMapperAnnotation = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = employeeMapperAnnotation.getEmpById(1);
			System.out.println(employee);
		} finally {
			openSession.close();
		}
	}

	/*
	 * ������ɾ�� 1.mybatis������ɾ��ֱ�Ӷ����������ͷ���ֵ: Integer,long,boolean 2.��Ҫ�����ֶ��ύ
	 * sqlSessionFactory.openSession();=>>�ֶ��ύ
	 * sqlSessionFactory.openSession(true);=>>�Զ��ύ
	 */
//	@Test
	public void test3() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 1.��ȡ����sqlsession�����Զ��ύ����
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// �������
			EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
//			List<Employee> list = employeeMapper.getEmpLikeER("%er");
//			for(Employee l:list) {
//				System.out.println(l);
//			}
//			Map<String, Object> map = employeeMapper.getEmpByIdReturnMap(2);
//			System.out.println(map);
			Map<String, Employee> lastNameLikeReturnMap = employeeMapper.getEmpByLastNameLikeReturnMap("%r%");
			System.out.println(lastNameLikeReturnMap);
//			Employee employee = new Employee(null,"taiwan","China@qq.com","c");
//			employeeMapper.addEmp(employee);
//			System.out.println(employee);
//			System.out.println(employee.getId());
			// �����޸�
//			Employee employee = new Employee(1,"jerry","jerry@qq.com","f");
//			employeeMapper.updateEmp(employee);

			// ����ɾ��
//			employeeMapper.deleteEmpById(1);
			// 2.�ֶ��ύ����
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("id", "4");
//			map.put("lastName", "Archer");
//			map.put("tableName", "employee");
//			Employee empByMap = employeeMapper.getEmpByMap(map);
//			System.out.println("map: " + empByMap);
			openSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			openSession.close();
		}
	}

//	@Test
	public void test4() throws Throwable {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
//			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//			Employee employee = mapper.getEmpById(3);
			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
//			Employee employee = mapper.getEmpAndDept(5);
//			System.out.println(employee);
//			System.out.println(employee.getDept().getDepartmentName());
//			System.out.println(employee.getDept().getId());
			Employee employee = mapper.getEmpByIdStep(5);
			System.out.println(employee);
			System.out.println(employee.getDept().getId());
			System.out.println(employee.getDept().getDepartmentName());
		} finally {
			openSession.close();
		}
	}

	@Test
	public void test5() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
//			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//			Department department = mapper.getDeptByIdPlus(1);
//			List<Employee> emps = department.getEmps();
//			System.out.println(emps);
//			Department deptByIdStep = mapper.getDeptByIdStep(2);
//			System.out.println(deptByIdStep);
//			System.out.println(deptByIdStep.getEmps());
			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = mapper.getEmpByIdStep(5);
			System.out.println(employee);
			System.out.println(employee.getDept());
		} finally {
			openSession.close();
		}
		System.out.println("hello wolrd");
	}
}
