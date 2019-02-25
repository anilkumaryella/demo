package com.altimetrik.poc.demo.entity.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.poc.demo.entity.UpiVirtualAddr;
import com.altimetrik.poc.demo.entity.UpiVirtualAddrPK;

public interface UpiVirtualAddrRepo extends JpaRepository<UpiVirtualAddr, UpiVirtualAddrPK>{

	List<UpiVirtualAddr> findAllByVpa(String vpa);

	List<UpiVirtualAddr> findAllByVpaAndStatus(String vpa, String status);

}
