package com.altimetrik.poc.demo.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="UPI_TXN_MASTER")
@IdClass(UpiTxnMasterPK.class)
public class UpiTxnMaster {
	
	@Id
	@Column(name="TXN_ID")
	private String txnId;	

	@Column(name="PAYER_MOBILE")
	private String payerMobileNo;
	
	@Column(name="PAYER_ACCNUM")
	private String payerAccountNo;
	
	@Column(name="PAYER_ADDR")
	private String payerAddr;
	@Column(name="PAYEE_MOBILE")
	private String payeeMobileNo;
	
	@Column(name="PAYEE_ACCNUM")
	private String payeeAccountNo;
	
	@Column(name="PAYEE_ADDR")
	private String payeeAddr;	
	
	@Column(name="AMOUNT")
	private BigDecimal amount;

	@Column(name="TXN_TYPE")
	private String txnType;
	

	@Column(name="STATUS")
	private String status;


	public String getTxnId() {
		return txnId;
	}


	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}


	public String getPayerMobileNo() {
		return payerMobileNo;
	}


	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}


	public String getPayerAccountNo() {
		return payerAccountNo;
	}


	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}


	public String getPayerAddr() {
		return payerAddr;
	}


	public void setPayerAddr(String payerAddr) {
		this.payerAddr = payerAddr;
	}


	public String getPayeeMobileNo() {
		return payeeMobileNo;
	}


	public void setPayeeMobileNo(String payeeMobileNo) {
		this.payeeMobileNo = payeeMobileNo;
	}


	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}


	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}


	public String getPayeeAddr() {
		return payeeAddr;
	}


	public void setPayeeAddr(String payeeAddr) {
		this.payeeAddr = payeeAddr;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getTxnType() {
		return txnType;
	}


	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}	
	
}
