package com.altimetrik.poc.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.poc.demo.common.JaxbConverter;
import com.altimetrik.poc.demo.common.SignatureGenUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CbsClient {

	private RestTemplate template;
	private String userName;
	private String password;
	private final HttpClientConnectionManager httpClientConnectionManager;
	private int timeOut;
	private int connTimeOut;

	private SignatureGenUtils genUtils;

	public CbsClient(String userName, String password, HttpClientConnectionManager httpClientConnectionManager,
			int timeOut, int connTimeOut, SignatureGenUtils genUtils) {
		super();

		this.userName = userName;
		this.password = password;
		this.httpClientConnectionManager = httpClientConnectionManager;
		this.timeOut = timeOut;
		this.connTimeOut = connTimeOut;
		this.genUtils = genUtils;
	}

	@PostConstruct
	public void init() {
		template = new RestTemplate(getClientHttpRequestFactory());

		List<HttpMessageConverter<?>> l = new ArrayList<HttpMessageConverter<?>>();

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		converter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

		l.add(converter);

		JaxbConverter signConverter = new JaxbConverter(genUtils);
		l.add(signConverter);
		template.setMessageConverters(l);

	}

	public RestTemplate getRestTemplate() {
		return template;
	}

	public ClientHttpRequestFactory getClientHttpRequestFactory() {

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(timeOut);
		factory.setConnectTimeout(connTimeOut);
		try {
			HttpClient httpClient = null;
			if (StringUtils.isEmpty(userName)) {
				httpClient = HttpClients.custom().setConnectionManager(this.httpClientConnectionManager).build();
			} else {
				httpClient = HttpClientBuilder.create().setConnectionManager(this.httpClientConnectionManager)
						.setDefaultCredentialsProvider(credentialsProvider(userName, password)).build();
			}
			factory.setHttpClient(httpClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return factory;
	}

	private BasicCredentialsProvider credentialsProvider(String userName2, String password2) {

		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(userName2, password2);
		AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
		BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();

		basicCredentialsProvider.setCredentials(scope, creds);
		return null;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getConnTimeOut() {
		return connTimeOut;
	}

	public void setConnTimeOut(int connTimeOut) {
		this.connTimeOut = connTimeOut;
	}

}
