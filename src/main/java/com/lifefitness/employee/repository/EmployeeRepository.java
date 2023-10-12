package com.lifefitness.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lifefitness.employee.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public List<Employee> findByGender(String name);

	@Query(value = "select * from Employee where employee_id in (select  s.employee_id,\r\n"
			+ "FROM  SALARY s where s.basic_salary  in (SELECT min(s.basic_salary)  FROM   EMPLOYEE  e,  SALARY s \r\n"
			+ "where e.employee_id = s.employee_id\r\n"
			+ "GROUP BY  e.department_department_id))", nativeQuery = true)
	public List<Employee> getEmployeeWithMinSalaryByDept();

	public List<Employee> findByDepartmentDepartmentId(long deptId);
	
		
	
}
