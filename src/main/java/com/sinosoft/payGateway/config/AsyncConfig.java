package com.sinosoft.payGateway.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步任务调度配置
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableAsync
public class AsyncConfig {

	/**
	 * 核心线程条数
	 */
	private final static int CorePoolSize = 10;

	/**
	 * 线程池最大条数
	 */
	private final static int MaxPoolSize = 200;
	/**
	 * 队列大小
	 */
	private final static int QueueCapacity = 10;

	/**
	 * 线程前缀
	 * 
	 * @return
	 */
	private final static String NamePrefix = "SinoExcutor_";
    /**
     *  线程池的使用：
     *  
     *  1.异步使用： @EnableAsync + @Async 
		2.spring默认使用名称为SimpleAsyncTaskExecutor的线程池
		3.自定义线程池时，如果只自定义了一个，那么会使用自定义的。
  		      如果自定义了多个且@Async()的方法（ 即不指定名称），那么将使用默认的SimpleAsyncTaskExecutor
  		      如果自定义了多个且@Async("自定义线程池的Bean名称")时，那么会使用自定义的。
  			
     * @return
     */
	@Bean("SinoExcutor")
	public Executor MyExcutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(CorePoolSize);
		executor.setMaxPoolSize(MaxPoolSize);
		executor.setQueueCapacity(QueueCapacity);
		executor.setThreadNamePrefix(NamePrefix);

		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}
	
	

}
