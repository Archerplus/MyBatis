package com.atguigu.mybatis.bean;

public class Employee {
	private Integer idd;
	private String lastName;
	private String email;
	private String gender;
	public Integer getId() {
		return idd;
	}
	public void setId(Integer id) {
		this.idd = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Employee [id=" + idd + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + "]";
	}
	
}
