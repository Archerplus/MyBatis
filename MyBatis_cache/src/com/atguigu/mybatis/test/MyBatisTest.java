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

	// ��������
	// һ�����棺�����ػ��棩sqlSession����Ļ��棬һ��������һֱ�����ģ������޷�ȥ�ر�;������ʵ����sqlSession�����һ��map�����ǻὫ��ѯ�����������ݶ��ŵ����map��
	//		Ҳ����˵һ��sqlSession����ӵ��һ�������Լ��Ļ��棬�µ�sqlSession�����ӵ���µ�һ������
	//			�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ�����
	//			�Ժ������Ҫ��ȡ����ͬ�����ݣ�ֱ�Ӵӻ�����ȥ�ã�û������ȥ��ѯ���ݿ�
	//
	//			һ������ʧЧ�����(û��ʹ�õ���ǰһ������������Ч������û�л���Ҫ�����ݿⷢ����ѯ����)
	//			1��sqlSession��ͬ	
	//			2��sqlSession��ͬ����ѯ������ͬ��		
	//			3��sqlSession��ͬ���������β�ѯ�ڼ�ִ������ɾ�Ĳ���(��β������ܾͻ�Ե�ǰ������Ӱ��)
	//			4��sqlSession��ͬ���ֶ������һ�����棨������գ� session.clearCache();��ջ���
	// �������棺��ȫ�ֻ��棩������namespace����Ļ��棬һ��namespace��Ӧһ����������
	//			��������
	//			1��һ���Ự����ѯһ�����ݣ�������ݾͻᱻ���ڵ�ǰ�Ự��һ�������У�
	//			2������Ự�رգ�һ����������ݻᱻ���浽���������У��µĻỰ��ѯ��Ϣ�Ϳ��Բ��ն���������ȥѰ������
	//			3��sqlSession===EmployeerMapper==>>Employee
	//							DepartmentMapper==>>Department
	//							��ͬnamespace��������ݻ�����Լ���Ӧ�Ļ��棨map����
	//							Ч�������ݻ�Ӷ��������л�ȡ
	//								��������ݶ��ᱻĬ���ȷ���һ��������
	//								ֻ�лỰ�ύ���߹ر��Ժ�һ�������е����ݲŻ�ת�Ƶ�����������
	//			ʹ�ã�
	//				1��������ȫ�ֶ����������ã�<setting name="cacheEnable" value="true" />
	//				2����ȥmapper.xml������ʹ�ö�������
	//					<cache></cache>
	//				3�������ǵ�pojo��Ҫʵ�����л��ӿ�
	//				
//					�ͻ����йص�һЩ�����Լ�һЩ����
//					1����cacheEnabled=true	false�رջ��棨�رն������棩��һ������һֱ���ڣ�
//					2����ÿ��select��ǩ����useCache="true".
//							false����ʹ�û��棨һ��������Ȼʹ�ã��������治ʹ�ã�
//					3����ÿ����ɾ�ı�ǩ��flushCache="true"  һ�������������
//						��˼����ÿ��ִ�������ɾ��֮��ͻ��������
//						���ԣ�flushCache="true"�����һ�����棻��������Ҳ�ᱻ���
//						��ѯ��ǩ��flushCache="false";
//							������Ǹ�Ϊtrue��ÿ�β�ѯ֮�󶼻���ջ��棬������û�б�ʹ�õ�
//					4����sqlSession.clearCache();ֻ�������ǰsession��һ������
//					5����localCacheScope�����ػ��������򣻣�һ������session������ǰ�Ự���������ݱ����ڻỰ�Ļ�����
//						STATEMENT:���Խ���һ�����棻
//				�������������ϣ�
//					1�������������jar������
//					2����������������������ϵ���������ٷ��У�
//					3����mapper.xml�ļ���ʹ���Զ��建��
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
			//������Ϊtrue,��Ϊ�ڵ�һ�β�ѯidΪ5��Ա����ʱ�򣬾ͽ�������ݴ�ŵ�������ȥ��
			//�������ٴν��в��ҵ�ʱ��,���Ƿ��ֻ������Ѿ�����idΪ5��Ա��,��ֱ�Ӵӻ�����ȥ��
			//�����ش����ݿ��л�ȡ,���Դ�ӡ����Ľ��Ϊtrue
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
			
			//�ڶ��β�ѯ�ǴӶ����������õ������ݣ���û�з����µ�SQL
			Employee employee2 = mapper2.getEmpById(5);
			System.out.println(employee2);
			session2.close();
		}finally {
			
		}
	}
}


//�����˳���Ȳ鿴����������������ݣ�Ȼ���ٲ鿴һ����������ݣ���������û���ҵ��ʹ����ݿ���ȥ����