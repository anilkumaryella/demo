package com.altimetrik.poc.demo.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Utils {

	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	@PersistenceContext
	private EntityManager entityManager;

	public String generateTxnId(String apiName) {
		UUID uuid = UUID.randomUUID();
		return StringUtils.join(apiName, uuid.toString());

	}

	private static final String PREFIXQUERY = "SELECT ";
	private static final String POSFIXQUERY = ".nextval from Dual";

	private <T> T typeCast(BigDecimal decimal, Class<T> clazz) {
		if (clazz.isAssignableFrom(BigInteger.class)) {
			return clazz.cast(decimal.toBigInteger());
		} else if (clazz.isAssignableFrom(String.class)) {
			return clazz.cast(decimal.toString());
		} else if (clazz.isAssignableFrom(Integer.class)) {
			return clazz.cast(decimal.intValueExact());
		} else if (clazz.isAssignableFrom(Long.class)) {
			return clazz.cast(decimal.longValueExact());
		} else if (clazz.isAssignableFrom(Double.class)) {
			return clazz.cast(decimal.doubleValue());
		} else {
			return clazz.cast(decimal);
		}

	}

	public <T> T getSecquence(String secquenceName, Class<T> clazz) {
		String sqlQuery = new StringBuilder().append(PREFIXQUERY).append(secquenceName.trim()).append(POSFIXQUERY)
				.toString();

		LOGGER.info("Secquence Querry :{} ReqClass :{}", sqlQuery, clazz.getTypeName());
		Query query = entityManager.createNativeQuery(sqlQuery);
		BigDecimal decimal = (BigDecimal) query.getSingleResult();
		T result = typeCast(decimal, clazz);
		LOGGER.info("Secquence Value :{} Class:{}", result, result.getClass());
		return result;
	}
}
