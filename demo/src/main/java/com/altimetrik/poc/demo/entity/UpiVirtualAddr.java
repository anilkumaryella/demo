package com.altimetrik.poc.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="UPI_VPA")
@IdClass(UpiVirtualAddrPK.class)
public class UpiVirtualAddr {
	
	@Id
	@Column(name="ID")
	private int id;

	@Column(name="MOBILE_NO")
	private String mobileNo;
	
	@Column(name="VPA")
	private String vpa;
	
	@Column(name="STATUS")
	private String status;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVpa() {
		return vpa;
	}

	public void setVpa(String vpa) {
		this.vpa = vpa;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
