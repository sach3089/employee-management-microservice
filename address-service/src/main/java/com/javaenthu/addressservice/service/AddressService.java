package com.javaenthu.addressservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.javaenthu.addressservice.dto.AddressDto;
import com.javaenthu.addressservice.exception.AddressException;
import com.javaenthu.addressservice.model.Address;
import com.javaenthu.addressservice.repository.AddressRepository;
import com.javaenthu.addressservice.response.AddressResponse;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Object getAllAddress(int startIndex , int pageSize, String search, String sortBy) {
		
		Pageable pageable;
		Page<Address> pageResult;
		if(pageSize==0) {
			if(sortBy=="state") {
				pageable = (Pageable) PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.ASC, sortBy));
			}else {
				pageable = (Pageable) PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, sortBy));
			}
		}else {
			int pageNumber = (startIndex + (pageSize-1))/pageSize;
			if(sortBy=="state") {
				pageable = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
			}else {
				pageable = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
			}
		}
		pageResult = addressRepo.findAll(pageable);
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
	
	public AddressResponse createNewAddress(AddressDto dto) {
		Address address= modelMapper.map(dto, Address.class);
		AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
		return addressResponse;
		
	}
	
	public AddressResponse getAddressById(int id) {
		Address address = addressRepo.findById(id).orElseThrow(
				()-> new AddressException("address does not exist", id));
		AddressResponse response = modelMapper.map(address, AddressResponse.class);
		return response;
	}

	public AddressResponse getAddressByEmployeeId(int employeeId) {
		Address address = addressRepo.findAddressByEmployeeId(employeeId);
		AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
		return addressResponse;
	}

}
