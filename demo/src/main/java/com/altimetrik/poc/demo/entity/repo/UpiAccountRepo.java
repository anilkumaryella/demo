package com.altimetrik.poc.demo.entity.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.poc.demo.entity.UpiAccount;
import com.altimetrik.poc.demo.entity.UpiAccountPK;

public interface UpiAccountRepo extends JpaRepository<UpiAccount, UpiAccountPK>{
	}
