package com.SpringBoot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.dao.EmployeeDAO;
import com.SpringBoot.modal.Employee;

@RestController
public class SpringBootRestController {
	
	@Autowired
	private EmployeeDAO dao;

	@Cacheable(value="employees",key="#root.methodName")
	@RequestMapping("/getAllEmployees")
	public List<Employee> getAllEmployees()
	{
		System.out.println("getAllEmployees called");
		List<Employee> employee_Data = dao.getAllEmployees();
		return employee_Data;
	}
	
	
	@CacheEvict(value="employees",allEntries=true)
	@RequestMapping("/clearcache")
	@ResponseBody
	public String clearcache()
	{
		System.out.println("cache cleared");
		return "cache cleared";
	}
	
	@RequestMapping("/")
	public ModelAndView getAllEmployeesdata()
	{
		System.out.println("ModelAndView");
		return new ModelAndView( "redirect:/getAllEmployees");
	}
	
}
