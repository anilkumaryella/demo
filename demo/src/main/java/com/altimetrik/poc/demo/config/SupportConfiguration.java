package com.altimetrik.poc.demo.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.security.cert.CertificateException;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.altimetrik.poc.demo.common.JaxbConverter;
import com.altimetrik.poc.demo.common.SignatureGenUtils;
import com.altimetrik.poc.demo.service.CbsClient;

@Configuration
public class SupportConfiguration {

	@Value("${sign.cert.path:C:\\Workspace\\POC\\demo\\demo\\keys\\mydomain.cer}")
	private String signCrt;

	@Value("${sign.pvtfile.path:C:\\Workspace\\POC\\demo\\demo\\keys\\private.p12}")
	private String pvtFile;

	@Value("${sign.jksfile.path:C:\\Workspace\\POC\\demo\\demo\\keys\\keystore.jks}")
	private String jksFile;

	@Value("${sign.pvtfile.type:PKCS12}")
	private String pvtType;

	@Value("${sign.pvtfile.password:anilanilanil}")
	private String pvtPass;

	@Value("${sign.pvtfile.allias:mydomain}")
	private String pvtAllias;

	@Autowired
	Environment env;

	@Value("${cbs.timeout:10000}")
	private int cbsTimeOut;

	@Value("${cbs.contimeout:10000}")
	private int cbsConnectionTimeOut;

	@Value("${cbs.cbsClientPoolSize:40}")
	private int cbsClientPoolSize;

	@Bean
	public CbsClient restClientBeans() throws Exception {

		CbsClient cbsClient = new CbsClient("", "", getHttpClientConnectionManager(cbsClientPoolSize), cbsTimeOut,
				cbsConnectionTimeOut, getSignatureUtils());
		return cbsClient;
	}

	@Bean(destroyMethod = "shutdown")
	public HttpClientConnectionManager getHttpClientConnectionManager(int cbsClientPoolSize2) {
		PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
		pool.setMaxTotal(cbsClientPoolSize2);
		pool.setDefaultMaxPerRoute(cbsClientPoolSize2);
		return pool;
	}

	@Bean
	public PublicKey getPublicKey()
			throws java.security.cert.CertificateException, NoSuchProviderException, CertificateException, IOException {
		return getCertificate();

	}

	@Bean
	public PrivateKey getPrivate() throws Exception {

		/*
		 * KeyStore keyStore=KeyStore.getInstance(pvtType);
		 * keyStore.load(((InputStream)ClassLoader.getSystemResourceAsStream(pvtFile)),
		 * pvtPass.toCharArray()); PrivateKey key=(PrivateKey)keyStore.getKey(pvtAllias,
		 * pvtPass.toCharArray()); return key;
		 */

		return getPrivateKey();

	}

	@Bean
	public SignatureGenUtils getSignatureUtils() throws Exception {

		SignatureGenUtils genUtils = new SignatureGenUtils();
		genUtils.setPrivateKey(getPrivate());
		genUtils.setPublicKey(getPublicKey());
		return genUtils;
	}

	@Bean
	JaxbConverter getJaxbConverterBean() throws Exception {
		return new JaxbConverter<>(getSignatureUtils());
	}

	private PublicKey getCertificate()
			throws CertificateException, IOException, java.security.cert.CertificateException, NoSuchProviderException {

		FileInputStream fis = new FileInputStream(signCrt);
		BufferedInputStream bis = new BufferedInputStream(fis);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		Certificate cert = null;
		while (bis.available() > 0) {
			cert = cf.generateCertificate(bis);
			LOGGER.info("Signer Certificate {}", cert.toString());
		}
		PublicKey key = cert.getPublicKey();
		LOGGER.info("Public Key [{}]", key);
		return key;
	}

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SupportConfiguration.class);

	private PrivateKey getPrivateKey() throws Exception {
		FileInputStream is = new FileInputStream(jksFile);

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, "anilanilanil".toCharArray());

		String alias = "mydomain";
		PrivateKey k = null;
		;
		Key key = keystore.getKey(alias, "anilanilanil".toCharArray());
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			// Certificate cert = keystore.getCertificate(alias);

			k = (PrivateKey) key;

			System.out.println("PrivateKey key[{}]" + k);

		}

		return k;

		/*
		 * KeyStore keyStore = KeyStore.getInstance(pvtType);
		 * keyStore.load(ClassLoader.getSystemResourceAsStream(pvtFile),
		 * pvtPass.toCharArray()); PrivateKey key = (PrivateKey)
		 * keyStore.getKey(pvtAllias, pvtPass.toCharArray());
		 * LOGGER.info("private key object [{}]", key); return key;
		 */

	}

}
