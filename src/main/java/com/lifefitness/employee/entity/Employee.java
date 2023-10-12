package com.lifefitness.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
@Entity
public class Employee {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "Employee Id is mandatory")
	@Column(name = "employee_id", nullable = false)
	private long employeeId;

	@NotNull(message = "First Name is mandatory")
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotNull(message = "Last Name is mandatory")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Email(message = "Not a valid Emailid")
	private String email;
	@NotNull
	private String gender;

	@ManyToOne
	private Department department;

	public Employee() {
	}

	public Employee(long employeeId, String firstName, String lastName, String email, String gender) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee[employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", gender=" + gender + "]";
	}
}
