package com.javaenthu.employeeservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javaenthu.employeeservice.Exception.EmployeeException;
import com.javaenthu.employeeservice.dto.EmployeeDto;
import com.javaenthu.employeeservice.model.Employee;
import com.javaenthu.employeeservice.repository.EmployeeRepository;
import com.javaenthu.employeeservice.response.AddressResponse;
import com.javaenthu.employeeservice.response.EmployeeResponse;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${addressservice.base.url}")
	private String addressBaseURL;
	
//	manually initializing resttemplate using resttemplatebuilder
//	public EmployeeService(RestTemplateBuilder builder) {
//		this.restTemplate = builder.build();
//	}
	
	public Object getAllEmployee(int startIndex, int pageSize , String search, String sortBy) {
		Pageable pageable;
		Page<Employee> pageResult;
		if(pageSize==0) {
			if(sortBy.equals("name")) {
				pageable = (Pageable) PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			}else {
				pageable = (Pageable) PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
			}
		}else {
			int pageNo = (startIndex + (pageSize-1)) / pageSize;
			if(sortBy.equals("name")) {
				pageable = (Pageable) PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
			}else {
				pageable = (Pageable) PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
			}
		}
		if(search ==null) {
			pageResult = employeeRepo.findAll(pageable);
		}else {
			pageResult = employeeRepo.findByNameOrBloodGroupOrEmail(pageable, search);
		}
		Map<String, Object> response = new HashMap<String, Object>();
		if(pageResult ==null || pageResult.getContent().isEmpty()) {
			response.put("Employees", new ArrayList<>());
			response.put("totalElements", 0);
			response.put("totalPage", 0);
		}else {
			response.put("Employees", pageResult.getContent());
			response.put("totalElements", pageResult.getTotalElements());
			response.put("totalPage", pageResult.getTotalPages());
		}
		return response;
		
	}
	
	
	public EmployeeResponse getEmployee(int id) {
		Employee employee = employeeRepo.findById(id).orElseThrow(() ->
		    new  EmployeeException("employee does not exist", id));
		EmployeeResponse emplResponse = modelMapper.map(employee, EmployeeResponse.class);
		AddressResponse address=restTemplate.getForObject(addressBaseURL+ "/address/{id}", AddressResponse.class, id);
		emplResponse.setAddressResponse(address);
		return emplResponse;
		
	}
	
	public EmployeeResponse addNewEmployee(EmployeeDto dto) {
		Employee employee = modelMapper.map(dto, Employee.class);
		Employee addedEmployee =  employeeRepo.save(employee);
		EmployeeResponse empl = modelMapper.map(addedEmployee,EmployeeResponse.class);
		return  empl;
	}

}
