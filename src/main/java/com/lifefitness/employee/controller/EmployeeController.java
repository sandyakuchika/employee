package com.lifefitness.employee.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lifefitness.employee.entity.Employee;
import com.lifefitness.employee.entity.Salary;
import com.lifefitness.employee.service.EmployeeService;

@RestController
@Validated
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/*
	 * public EmployeeController(EmployeeService employeeService) { super();
	 * this.employeeService = employeeService; }
	 */

	@RequestMapping("/emp-all")
	ResponseEntity<List<Employee>> getAllEmployeeList() {
		List<Employee> employeeList = employeeService.getAllEmployeeList();
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@RequestMapping(path = "/add-emp", method = RequestMethod.POST)
	ResponseEntity<?> addEmployee(@RequestBody @Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		Employee employeeEntity = employeeService.addEmployee(employee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(employeeEntity.getEmployeeId()).toUri();
		return ResponseEntity.created(location).body(location);

//		return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
	}

	@RequestMapping(path = "/delete-emp/{empId}", method = RequestMethod.DELETE)
	ResponseEntity<String> deleteEmployee(@PathVariable int empId) {
		employeeService.deleteEmployee(empId);
		return new ResponseEntity<>("Employee Deleted sucessfully", HttpStatus.OK);
	}

	@RequestMapping(path = "/update-emp/{empId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateEmployee(@PathVariable long empId, @RequestBody @Valid Employee employee,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getAllErrors());
		}
		Employee employeeEntity = employeeService.updateEmployee(empId, employee);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(employeeEntity.getEmployeeId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@RequestMapping(path = "/emp-by-id/{empId}", method = RequestMethod.GET)
	ResponseEntity<Employee> getEmployee(@PathVariable long empId) {
		Employee entity = employeeService.findEmployeeById(empId);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@RequestMapping("/emp-by-deptid/{deptId}")
	ResponseEntity<List<Employee>> getEmployeeByDepartmentId(@PathVariable long deptId) {
		List<Employee> employeeList = employeeService.getEmployeeByDepartmentId(deptId);
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@RequestMapping(path = "/emp-by-gender/{gender}", method = RequestMethod.GET)
	ResponseEntity<List<Employee>> getEmployeeByGender(@PathVariable String gender) {
		List<Employee> employeeList = employeeService.findEmployeeByGender(gender);
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@RequestMapping(path = "/emp-with-min-sal-by-dept", method = RequestMethod.GET)
	ResponseEntity<List<Employee>> getEmployeeWithMinSalaryByDept() {
		List<Employee> employeeList = employeeService.getEmployeeWithMinSalaryByDept();
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@RequestMapping(path = "/emp-max-tax/{empId}", method = RequestMethod.GET)
	ResponseEntity<Salary> getEmployeeMaxTax(@PathVariable long empId) {
		Salary salary = employeeService.getEmployeeMaxTax(empId);
		return new ResponseEntity<>(salary, HttpStatus.OK);

	}
	@PostMapping(path = "/upload-emp-excel" )
	ResponseEntity<?> uploadEmpExcel(@RequestParam("file") MultipartFile excelFile) {
		return employeeService.uploadEmpExcel(excelFile);
	}

}
