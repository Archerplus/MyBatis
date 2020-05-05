package com.atguigu.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapper {
	//多条记录封装的一个map map<Integer,Employee>:键是这条记录的主键,值是这个记录封装的一个类
	//告诉mybatis封装map的时候使用那个属性作为主键
	@MapKey("lastName")
	public Map<String,Employee> getEmpByLastNameLikeReturnMap(String lastName);
	//单条记录的一个map
	// 返回一条记录的map;key就是列名,值就是对应值
	
	public Map<String, Object> getEmpByIdReturnMap(Integer id);

	public List<Employee> getEmpLikeER(String lastName);

	public Employee getEmpByMap(Map<String, Object> map);

	public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

	public Employee getEmpById(Integer id);

	public void addEmp(Employee employee);

	public void updateEmp(Employee employee);

	public void deleteEmpById(Integer id);
}
