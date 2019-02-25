package com.altimetrik.poc.demo.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.altimetrik.poc.demo.bean.CbsAccount;
import com.altimetrik.poc.demo.bean.Response_VO;
import com.altimetrik.poc.demo.exception.DataException;

import generated.PayResponse;
import generated.PayTxn;

@Service
public class PayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PayService.class);
	@Inject
	private CbsClient cbsClient;

	/*
	 * @Autowired private GuavaCacheManager cacheManager;
	 */

	@Autowired
	private CacheManager cacheManager;

	@Value("${cbs.listacc.url:http://localhost:9080//app/cbs/listallacc}")
	private String cbsListAccUrl;

	@Value("${cbs.balenq.url:http://localhost:9080//app/cbs/balenq}")
	private String cbsBalEnqUrl;

	@Value("${cbs.pay.url:http://localhost:9080//app/cbs/reqpay}")
	private String cbsPayUrl;

	public Response_VO sendMoney(PayTxn vpa) {

		return sendAndReceiveCbs(cbsPayUrl, vpa);

	}

	private <T> Response_VO sendAndReceiveCbs(String url, T t) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_XML);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
			HttpEntity<T> entity = new HttpEntity<T>(t, headers);
			RestTemplate restTemplate = cbsClient.getRestTemplate();
			LOGGER.info("Request to CBS [{}]", t);
			HttpEntity<PayResponse> response = restTemplate.postForEntity(url, entity, PayResponse.class);
			PayResponse res = response.getBody();
			LOGGER.info("Response from CBS [{}]", res);
			return new Response_VO(res.getErrorCode(), res.getErrorMsg());

		} catch (RestClientException e) {

			if (e.getRootCause() instanceof HttpClientErrorException) {
				LOGGER.error(e.getMessage());
				return new Response_VO("01", "timeOut");
			} else if (e.getRootCause() instanceof SocketTimeoutException) {
				LOGGER.error(e.getMessage());
				return new Response_VO("02", "connectionTimeout");
			} else {
				LOGGER.error(e.getMessage());
				return new Response_VO("03", "CbsDown");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response_VO("11", "FAILURE");
		}
	}

	public List<CbsAccount> getAllAccounts() throws DataException {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

			RestTemplate restTemplate = cbsClient.getRestTemplate();

			HttpEntity entity = new HttpEntity(headers);

			ResponseEntity<Object> resEntity = restTemplate.exchange(cbsListAccUrl, HttpMethod.GET, entity,
					Object.class);
			Object response = resEntity.getBody();
			LOGGER.info("listacc Response from CBS [{}]", response);
			List<CbsAccount> list = new ArrayList<CbsAccount>();
			if (response instanceof List) {
				list = (List<CbsAccount>) response;
			} else {
				Response_VO res = (Response_VO) response;
				throw new DataException(res.getResponseCode(), res.getResponseMsg());
			}

			return list;

		} catch (RestClientException e) {

			if (e.getRootCause() instanceof HttpClientErrorException) {
				LOGGER.error(e.getMessage());
				throw new DataException("01", "timeOut");
			} else if (e.getRootCause() instanceof SocketTimeoutException) {
				LOGGER.error(e.getMessage());
				throw new DataException("02", "connectionTimeout");
			} else {
				LOGGER.error(e.getMessage());
				throw new DataException("03", "CbsDown");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			if (e instanceof DataException) {
				throw e;
			}
			throw new DataException("11", "FAILURE");
		}
	}

	@Async
	public void getAccountBalance(String txnId, String accNo) {

		Map<String, String> map = new HashMap<String, String>();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

			RestTemplate restTemplate = cbsClient.getRestTemplate();

			HttpEntity entity = new HttpEntity(headers);

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(cbsBalEnqUrl)

					.queryParam("accNo", accNo);

			ResponseEntity<Object> resEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					Object.class);
			Object response = resEntity.getBody();
			LOGGER.info("balenq Response from CBS [{}]", response);

			if (response instanceof Map) {
				map = (Map<String, String>) response;
			} else {
				Response_VO res = (Response_VO) response;
				throw new DataException(res.getResponseCode(), res.getResponseMsg());
			}

		} catch (RestClientException e) {

			if (e.getRootCause() instanceof HttpClientErrorException) {
				LOGGER.error(e.getMessage());
				map.put("errorCode", "01");
				map.put("errorMsg", "timeOut");
			} else if (e.getRootCause() instanceof SocketTimeoutException) {
				LOGGER.error(e.getMessage());
				map.put("errorCode", "02");
				map.put("errorMsg", "connectionTimeout");
			} else {
				LOGGER.error(e.getMessage());
				map.put("errorCode", "03");
				map.put("errorMsg", "CbsDown");
			}
		} catch (Exception e) {
			if (e instanceof DataException) {
				map.put("errorCode", ((DataException) e).getErrorCode());
				map.put("errorMsg", ((DataException) e).getErrorMsg());
			}
			LOGGER.error(e.getMessage());
			map.put("errorCode", "11");
			map.put("errorMsg", "FAILURE");
		} finally {
			// CacheManager cm= new GuavaCacheManager();
			cacheManager.getCache("mycache").put(txnId, map);
		}

	}

	public Map<String, String> getResponse(String txnId) {
		Map<String, String> map = new HashMap<String,String>();
		try {
			map = cacheManager.getCache("mycache").get(txnId, Map.class);
		} catch (Exception e) {
			map.put("errorCode", "11");
			map.put("errorMsg", "Please Try After SomeTime");
		}

		return map;
	}

	public <T> T getResp(String txnId) throws DataException {

		T response = (T) cacheManager.getCache("mycache").get(txnId, Object.class);

		if (response == null) {
			throw new DataException("11", "Please Try After SomeTime");
		}
		return response;
	}

}
