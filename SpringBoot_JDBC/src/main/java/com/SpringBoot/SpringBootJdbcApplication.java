package com.SpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.SpringBoot.dao.EmployeeDAO;
import com.SpringBoot.modal.Employee;

@SpringBootApplication
public class SpringBootJdbcApplication implements CommandLineRunner {

	@Autowired
	private EmployeeDAO employeeDAO;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createEmployee();
		//getEmployeeById(1);
		//employeeDAO.updateEmployeeEmailById(2,"ravi123@gmail.com");
		//employeeDAO.deleteEmployeeById(2);
	}


	private void getEmployeeById(int id) {
		Employee employee = employeeDAO.getEmployeeById(id);
		System.out.println("employee : "+employee);
	}

	private void createEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeName("test2");
		employee.setEmail("navin@gmail.com");
		employee.setSalary(9999.09);
		
		employeeDAO.createEmployee(employee);
	}
}
