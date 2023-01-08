package com.javaenthu.employeeservice.response;

public class EmployeeResponse {
	
	private int id;
	private String name;
	private String Email;
	private String bloodGroup;
	private AddressResponse addressResponse;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public AddressResponse getAddressResponse() {
		return addressResponse;
	}
	public void setAddressResponse(AddressResponse addressResponse) {
		this.addressResponse = addressResponse;
	}
	
	
	

}
