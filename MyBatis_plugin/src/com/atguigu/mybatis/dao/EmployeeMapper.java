package com.atguigu.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	//������¼��װ��һ��map map<Integer,Employee>:����������¼������,ֵ�������¼��װ��һ����
	//����mybatis��װmap��ʱ��ʹ���Ǹ�������Ϊ����
	@MapKey("lastName")
	public Map<String,Employee> getEmpByLastNameLikeReturnMap(String lastName);
	//������¼��һ��map
	// ����һ����¼��map;key��������,ֵ���Ƕ�Ӧֵ
	
	public Map<String, Object> getEmpByIdReturnMap(Integer id);

	public List<Employee> getEmpLikeER(String lastName);

	public Employee getEmpByMap(Map<String, Object> map);

	public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	public Employee getEmpById(Integer id);

	public void addEmp(Employee employee);

	public void updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
}
