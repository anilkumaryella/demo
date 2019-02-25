package com.altimetrik.poc.demo.entity;

import java.io.Serializable;

public class UpiTxnMasterPK implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String txnId;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	
	
}
