package com.SpringBoot.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.SpringBoot.dao.EmployeeDAO;
import com.SpringBoot.modal.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO 
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Override
	//@Transactional
	public void createEmployee(Employee employee) {
		TransactionDefinition txDef = new DefaultTransactionDefinition();
		TransactionStatus txStatus = transactionManager.getTransaction(txDef);
		try
		{
		String CREATE_EMPLOYEE_QUERY_TEMP = "insert into employee_table_temp(employee_name,email,salary) values(?,?,?)";
		int update = jdbcTemplate.update(CREATE_EMPLOYEE_QUERY_TEMP, employee.getEmployeeName(), employee.getEmail(),employee.getSalary());
		System.out.println("CREATE_EMPLOYEE_QUERY_TEMP is : "+CREATE_EMPLOYEE_QUERY_TEMP);
		if(update == 1)
		{
			System.out.println("EMPLOYEE CREATED in temp ................");
			String CREATE_EMPLOYEE_QUERY = "insert into employee_table(employee_name,email,salary) values(?,?,?)";
			int update_main_table = jdbcTemplate.update(CREATE_EMPLOYEE_QUERY, employee.getEmployeeName(), employee.getEmail(),employee.getSalary());
			System.out.println("CREATE_EMPLOYEE_QUERY is : "+CREATE_EMPLOYEE_QUERY);
			if(update_main_table == 1)
			{
				transactionManager.commit(txStatus);
				System.out.println("EMPLOYEE CREATED in MAIN TABLE.........");
			}
		}
		}
		catch(Exception e)
		{
			transactionManager.rollback(txStatus);
			System.out.println("SOME Exception : "+e.getClass());
			e.printStackTrace();
		}
	}

	@Override
	public Employee getEmployeeById(Integer employeeId) {
		String GET_EMPLOYEE_SQL = "SELECT * from employee_table where employee_id=?";
		Employee employee = jdbcTemplate.queryForObject(GET_EMPLOYEE_SQL, new EmployeerowMapper(), employeeId);
		return employee;
	}

	@Override
	public void updateEmployeeEmailById(Integer employeeId, String newEmail) {
		String UPDATE_EMPLOYEE_SQL = "UPDATE employee_table set email=? WHERE employee_id=?";
		int update = jdbcTemplate.update(UPDATE_EMPLOYEE_SQL, newEmail,employeeId);
		if(update == 1){
			System.out.println("Employee Email is Updated....");
		}
	}

	@Override
	public void deleteEmployeeById(Integer employeeId) {
		String DELETE_EMPLOYEE_SQL = "DELETE FROM employee_table WHERE employee_id=?";
		int update = jdbcTemplate.update(DELETE_EMPLOYEE_SQL,employeeId);
		if(update == 1){
			System.out.println("Employee is Deleted......");
		}
	}
}
