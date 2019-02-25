package com.altimetrik.poc.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync

public class AsyncConfiguration {

	@Value("${async.thread.pool.name:Async -}")
	private String threadPoolName;
	
	
	@Value("${async.thread.maxpool.size:40}")
	private int maxPoolSize;
	
	@Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(maxPoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(threadPoolName);
        executor.initialize();
        return executor;
    }
}
