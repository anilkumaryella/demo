package com.altimetrik.poc.demo.fin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.poc.demo.bean.CbsAccount;
import com.altimetrik.poc.demo.bean.Response_VO;
import com.altimetrik.poc.demo.common.Utils;
import com.altimetrik.poc.demo.common.Versions;
import com.altimetrik.poc.demo.exception.DataException;
import com.altimetrik.poc.demo.service.PayService;

import generated.PayTxn;

@RestController
@RequestMapping(value = "/app/demo/", consumes = Versions.V1_0, produces = Versions.V1_0)
public class PayController {

	@Inject
	private PayService payService;

	@Inject
	private Utils utils;

	private static final Logger LOGGER = LoggerFactory.getLogger(PayController.class);

	
	@Cacheable(value="listacccache")
	@RequestMapping(value = "/listallacc", method = RequestMethod.GET)
	public List<CbsAccount> listacc() throws DataException {
		LOGGER.info("list all accounts with status A");
		return payService.getAllAccounts();
	}

	// Asynchronous Service
	
	@RequestMapping(value = "/balenq", method = RequestMethod.GET)
	public Map<String, String> balenq(@RequestParam String accNo) {
		LOGGER.info("Balence Enquery accNo [{}] ", accNo);

		String txnId = utils.generateTxnId("BAL");
		payService.getAccountBalance(txnId, accNo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("txnId", txnId);
		return map;

	}

	@RequestMapping(value = "/getresponse", method = RequestMethod.GET)
	public <T> ResponseEntity<T> getRes(@RequestParam String txnId) throws DataException {
		LOGGER.info("getResponse txnId [{}] ", txnId);
		return new ResponseEntity<T>((T) payService.getResponse(txnId), HttpStatus.OK);

	}

	@RequestMapping(value = "/sendmoney", method = RequestMethod.POST)
	public Response_VO sendmoney(@RequestBody PayTxn txn) {
		LOGGER.info("sendmoney Request {}", txn);
		Response_VO response = payService.sendMoney(txn);
		LOGGER.info("sendmoney Response {}", response);
		return response;
	}

}
