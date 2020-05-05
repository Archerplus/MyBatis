package com.atguigu.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {
	// 携带了那个字段查询条件就带上这个字段的值
	public List<Employee> getEmpsByConditionIf(Employee employee);

	public List<Employee> getEmpsByConditionTrim(Employee employee);

	public List<Employee> getEmpByConditionChoose(Employee employee);
	
	public void updateEmp(Employee emp);

	public List<Employee> getEmpsByConditionForeach(List<Integer> ids);
	
	public void addEmps(@Param("emps")List<Employee> emps);
	
	public List<Employee> getEmpsTestInnerParameter(Employee employee);
}