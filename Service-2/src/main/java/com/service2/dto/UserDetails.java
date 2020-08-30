package com.service2.dto;

public class UserDetails {
	private String name;
	private String dob;
	private String salary;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "UserDetails [name=" + name + ", dob=" + dob + ", salary=" + salary + ", age=" + age + "]";
	}
	
	
	

}
