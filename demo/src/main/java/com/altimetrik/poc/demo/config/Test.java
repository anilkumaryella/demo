package com.altimetrik.poc.demo.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.slf4j.LoggerFactory;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// private static final org.slf4j.Logger LOGGER =
		// LoggerFactory.getLogger(Test.class);
		try {

			FileInputStream is = new FileInputStream("C:\\Workspace\\POC\\demo\\demo\\keys\\keystore.jks");

			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(is, "anilanilanil".toCharArray());

			String alias = "mydomain";

			Key key = keystore.getKey(alias, "anilanilanil".toCharArray());
			if (key instanceof PrivateKey) {
				// Get certificate of public key
				Certificate cert = keystore.getCertificate(alias);

				// Get public key
				PublicKey publicKey = cert.getPublicKey();
				PrivateKey k = (PrivateKey) key;

				System.out.println("publicKey key[{}]" + publicKey);

				System.out.println("PrivateKey key[{}]" + k);

				System.out.println("certificate [{}]" + cert.toString());
			}
			/*
			 * FileInputStream fis = new FileInputStream(
			 * "C:\\\\Workspace\\\\POC\\\\demo\\\\demo\\\\keys\\\\mydomain.cer");
			 * BufferedInputStream bis = new BufferedInputStream(fis);
			 * 
			 * CertificateFactory cf = CertificateFactory.getInstance("X.509"); Certificate
			 * cert = null; while (bis.available() > 0) { cert =
			 * cf.generateCertificate(bis); //System.out.println("certificate" +
			 * cert.toString()); } PublicKey key = cert.getPublicKey();
			 * System.out.println("PUBLIC KEY [{}]" + key);
			 * 
			 * 
			 * byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
			 * 
			 * PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes); KeyFactory kf =
			 * KeyFactory.getInstance("RSA"); return kf.generatePrivate(spec);
			 * 
			 * 
			 * String passwd = "anilanilanil"; KeyStore keyStore =
			 * KeyStore.getInstance("PKCS12"); keyStore.load(
			 * ClassLoader.getSystemResourceAsStream(
			 * "C:\\\\Workspace\\\\POC\\\\demo\\\\demo\\\\keys\\\\wso2.key"), null);
			 * PrivateKey pvtkey = (PrivateKey) keyStore.getKey("mydomain",null);
			 * 
			 * System.out.println("private KEY [{}]" + pvtkey);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
