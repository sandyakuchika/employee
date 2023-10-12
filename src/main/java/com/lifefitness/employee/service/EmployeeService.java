package com.lifefitness.employee.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lifefitness.employee.entity.Employee;
import com.lifefitness.employee.entity.Salary;
import com.lifefitness.employee.exception.ResourceNotFoundException;
import com.lifefitness.employee.repository.EmployeeRepository;
import com.lifefitness.employee.repository.SalaryRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	private SalaryRepository salaryRepository;

	public EmployeeService(EmployeeRepository employeeRepository, SalaryRepository salaryRepository) {
		super();
		this.employeeRepository = employeeRepository;
		this.salaryRepository = salaryRepository;
	}

	public List<Employee> getAllEmployeeList() {
		List<Employee> empAll = employeeRepository.findAll();
		return empAll;
	}

	public Employee addEmployee(Employee employee) {
		Employee entity = employeeRepository.save(employee);
		if (entity == null)
			throw new ResourceNotFoundException("Employee not Created");
		return entity;

	}

	public Employee findEmployeeById(long empId) {
		Employee entity = employeeRepository.findById(empId).get();
//						.orElseThrow(()->new EmployeeCustomException("Employee with ID" + empId+" Not Found"));
		return entity;
	}

	public Employee updateEmployee(long empId, Employee employee) {
		Employee entity = findEmployeeById(empId);
		entity.setFirstName(employee.getFirstName());
		entity.setEmail(employee.getEmail());
		entity.setLastName(employee.getLastName());
		entity.setGender(employee.getGender());
		entity.setDepartment(employee.getDepartment());
		Employee updatedEntity = employeeRepository.save(entity);
		return updatedEntity;
	}

	public void deleteEmployee(int empId) {
		Employee entity = findEmployeeById(empId);
		employeeRepository.delete(entity);
	}

	public List<Employee> findEmployeeByGender(String gender) {
		List<Employee> employeeList = employeeRepository.findByGender(gender);
		if (employeeList.isEmpty())
			throw new ResourceNotFoundException("Employees not found with the given gender");
		return employeeList;
	}

	public List<Employee> getEmployeeWithMinSalaryByDept() {
		return employeeRepository.getEmployeeWithMinSalaryByDept();

	}

	public Salary getEmployeeMaxTax(long empId) {
		if (salaryRepository.findById(empId).isEmpty())
			throw new ResourceNotFoundException("Employee Salary not found");
		Salary taxSalary = salaryRepository.findById(empId).isEmpty() ? null : salaryRepository.findById(empId).get();
		double tax = 0;
		long income = (taxSalary.getBasicSalary() + taxSalary.getAllowence()) * 12;
		if (income <= 15000) {
			tax = 0;
		} else if (income > 15000 && income <= 30000) {
			tax = 200;
		} else if (income > 30000 && income <= 75000) {
			tax = 0 + 200 + (income - 30000) * 0.0475;
		} else if (income > 75000) {
			tax = 0 + 200 + (45000 * 0.0475) + ((income - 75000) * 0.07);
		}
		taxSalary.setMaxTax(tax);
		return taxSalary;
	}

	public List<Employee> getEmployeeByDepartmentId(long deptId) {
		List<Employee> employeeList = employeeRepository.findByDepartmentDepartmentId(deptId);
		if (employeeList.isEmpty())
			throw new ResourceNotFoundException("Employees not found with the given Department");
		return employeeList;
	}

	public ResponseEntity<?> uploadEmpExcel(MultipartFile excelFile) {
		List<Employee> employeeList= new  ArrayList<Employee>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excelFile.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			Employee empEntity = new Employee();
			Row headerRow = sheet.getRow(0);
			// System.out.println(headerRow.getHeight());
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Employee savedEmployee = new Employee();
				XSSFRow row = sheet.getRow(i);
				// for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
				DataFormatter dataformatter = new DataFormatter();
				// System.out.print(dataformatter.formatCellValue(row.getCell(j)) +" ");
				empEntity.setEmployeeId((long) row.getCell(0).getNumericCellValue());
				empEntity.setFirstName(dataformatter.formatCellValue(row.getCell(1)));
				empEntity.setLastName(dataformatter.formatCellValue(row.getCell(2)));
				empEntity.setEmail(dataformatter.formatCellValue(row.getCell(3)));
				empEntity.setGender(dataformatter.formatCellValue(row.getCell(4)));
				empEntity.getDepartment().setDepartmentId((long) row.getCell(5).getNumericCellValue());;

				Employee existingEntity = this.findEmployeeById(empEntity.getEmployeeId());
				if (existingEntity != null)
					savedEmployee=this.updateEmployee(empEntity.getEmployeeId(), empEntity);
				else
					savedEmployee=this.addEmployee(empEntity);
				employeeList.add(savedEmployee);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(employeeList);

		//return empResponseList;
	}

}
