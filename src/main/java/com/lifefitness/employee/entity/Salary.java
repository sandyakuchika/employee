package com.lifefitness.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Salary {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false)
	private long employeeId;
	
	@Column(name = "basic_salary", nullable = false)
	private long basicSalary;
	
	@Column(name = "allowence", nullable = false)
	private long allowence;
	
	@Transient
	private double maxTax;
	
	public Salary() {
	}
	public Salary(long employeeId, long basicSalary, long allowence) {
		super();
		this.employeeId = employeeId;
		this.basicSalary = basicSalary;
		this.allowence = allowence;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public long getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(long basic_salary) {
		this.basicSalary = basic_salary;
	}
	public long getAllowence() {
		return allowence;
	}
	public void setAllowence(long allowence) {
		this.allowence = allowence;
	}
	
	public double getMaxTax() {
		return maxTax;
	}
	public void setMaxTax(double maxTax) {
		this.maxTax = maxTax;
	}
	@Override
	public String toString() {
		return "Salary [employeeId=" + employeeId + ", basicSalary=" + basicSalary + ", allowence=" + allowence
				+ ", maxTax=" + maxTax + "]";
	}
	
	

}
