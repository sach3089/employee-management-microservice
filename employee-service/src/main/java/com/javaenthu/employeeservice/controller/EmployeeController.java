package com.javaenthu.employeeservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.employeeservice.dto.EmployeeDto;
import com.javaenthu.employeeservice.model.Employee;
import com.javaenthu.employeeservice.repository.EmployeeRepository;
import com.javaenthu.employeeservice.response.EmployeeResponse;
import com.javaenthu.employeeservice.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public ResponseEntity<Object> getAllEmployee(@RequestParam(defaultValue = "0") int startIndex,
			@RequestParam(defaultValue = "0") int pageSize, @RequestParam(required = false) String search,
			@RequestParam(defaultValue = "id") String sortBy){
		Object response= employeeService.getAllEmployee(startIndex, pageSize, search, sortBy);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeDetails(@PathVariable("id") int id) {
		EmployeeResponse employeeResponse =  employeeService.getEmployee(id);
	    return ResponseEntity.status(HttpStatus.OK)
	    		.body(employeeResponse);
	}
	
	@PostMapping("/employees")
	public ResponseEntity<EmployeeResponse> addNewEmployee(@RequestBody EmployeeDto edto){
		EmployeeResponse employeeResponse = employeeService.addNewEmployee(edto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(employeeResponse);
	}

}
