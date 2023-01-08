package com.javaenthu.addressservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaenthu.addressservice.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

	Page<Address> findAll(Pageable pageable);
	@Query(value = "select ea.id,ea.lane1,ea.lane2,ea.state,ea.zip from employeeservicedb.address ea join employeeservicedb.employee e on "
			+ "e.id = ea.employee_id where ea.employee_id= :employeeId", nativeQuery=true)
	Address findAddressByEmployeeId(int employeeId);
}
