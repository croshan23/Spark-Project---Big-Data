package Streamming;

import java.io.Serializable;

public class Person implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5991108375546466101L;
	private String name;
	private int age;
	private String phone;
	private String profession;
	private Long Salary;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public Long getSalary() {
		return Salary;
	}
	public void setSalary(Long salary) {
		Salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Person(String name, int age, String phone, String profession,
			Long salary) {
		this.name = name;
		this.age = age;
		this.phone = phone;
		this.profession = profession;
		Salary = salary;
	}
	
}
