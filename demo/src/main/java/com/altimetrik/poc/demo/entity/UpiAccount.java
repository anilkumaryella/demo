package com.altimetrik.poc.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="UPI_ACCOUNT")
@IdClass(UpiAccountPK.class)
public class UpiAccount {
	
	@Id
	@Column(name="ID")
	private int id;

	@Column(name="MOBILE_NO")
	private String mobileNo;
	
	@Column(name="ACCOUNT_NO")
	private String accountNo;
	
	@Column(name="STATUS")
	private String status;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
