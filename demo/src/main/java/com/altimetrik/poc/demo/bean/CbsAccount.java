package com.altimetrik.poc.demo.bean;

public class CbsAccount {

	private int id;

	private String mobileNo;

	private String accountNo;

	private String bal;

	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

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

	@Override
	public String toString() {
		return "CbsAccount [id=" + id + ", mobileNo=" + mobileNo + ", accountNo=" + accountNo + ", bal=" + bal
				+ ", status=" + status + "]";
	}

}
