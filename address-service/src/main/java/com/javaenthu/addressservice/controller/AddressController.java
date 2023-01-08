package com.javaenthu.addressservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaenthu.addressservice.dto.AddressDto;
import com.javaenthu.addressservice.response.AddressResponse;
import com.javaenthu.addressservice.service.AddressService;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/address")
	public ResponseEntity<Object> getAllAddress(@RequestParam(defaultValue = "0") int startIndex, @RequestParam(defaultValue = "0") int pageSize,
			@RequestParam(required = false) String search, @RequestParam(defaultValue = "id") String sortBy){
		 Object response= addressService.getAllAddress(startIndex, pageSize, search, sortBy);
		 return ResponseEntity.status(HttpStatus.OK)
				 .body(response);
	}
	
	@PostMapping("/address")
	public ResponseEntity<AddressResponse> createNewAddress(@RequestBody AddressDto dto){
		AddressResponse response =addressService.createNewAddress(dto);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
	@GetMapping("/address/{employeeId}")
	public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int employeeId){
		AddressResponse response = addressService.getAddressByEmployeeId(employeeId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}

}
