package com.altimetrik.poc.demo.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
@EnableAutoConfiguration

public class CacheConfiguration {

	@Value("${cache.expiry:60}")
	private int cacheExpireTime;

	@Value("${cache.size:100}")
	private int cacheSize;
	
	@Bean
	public CacheManager cacheManager() throws IOException {

		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

		GuavaCache mycache = new GuavaCache("mycache",
				CacheBuilder.newBuilder().expireAfterWrite(cacheExpireTime, TimeUnit.SECONDS).maximumSize(cacheSize).build());

		GuavaCache listacccache = new GuavaCache("listacccache",
				CacheBuilder.newBuilder().expireAfterWrite(cacheExpireTime, TimeUnit.SECONDS).maximumSize(cacheSize).build());

		
		simpleCacheManager.setCaches(Arrays.asList(mycache,listacccache));
		return simpleCacheManager;
	}

}
