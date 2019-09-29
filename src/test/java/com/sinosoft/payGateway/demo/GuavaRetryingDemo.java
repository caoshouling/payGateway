package com.sinosoft.payGateway.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

/**
 * 重试机制的Demon
 * 
 * @author Administrator
 * 
 * 
 *         ★自己写代码 1.核心逻辑自己写代码if-else加try-catch，延迟用Thread.sleep(1000);
 *         为了控制系统线程数，可以使用ScheduledThreadPoolExecutor（ScheduledExecutorService）控制多少秒延迟或者周期性执行。
 * 
 *         问题： 正常逻辑和重试逻辑强耦合
 * 
 *         2.如果为了通用，可以使用动态带或者AOP的方式
 * 
 * 
 *         ★Spring Retry和guava retrying
 * 
 *         1.spring-retry :
 *         基本功能都有，但是必须是基于异常来进行控制。如果你要以返回值的某个状态来判定是否需要重试，可能只能通过自己判断返回值然后显式抛出异常了。
 *         需要依赖SpringCore 要求JDK1.7+
 * 
 *         2.guava retryer:(推荐) 与spring-retry类似，都是通过定义重试者角色来包装正常逻辑重试， 但是Guava
 *         retryer有更优的策略定义，在支持重试次数和重试频度控制基础上，能够兼容支持多个异常或者自定义实体对象的重试源定义，让重试功能有更多的灵活性。
 * 
 *         要求JDK1.6+
 *
 * 
 */
public class GuavaRetryingDemo {
	private static final Logger logger = LoggerFactory.getLogger(GuavaRetryingDemo.class);

	private static Callable<String> callableWithException() {
		
		return new Callable<String>() {
			int counter = 0;

			public String call() throws Exception {
				counter++;
				logger.info("do sth :"+ counter);
				if (counter < 2) {
					throw new RuntimeException("sorry");
				}
				Thread.sleep(1000);
				return "good";
			}
		};
	}

	public static void main(String[] params) throws Exception {
		System.out.println("正常结束，返回值："+retryMethodWithResult(10L,3,callableWithException(),RuntimeException.class,null));
	}
	/**
	 * 重试方法
	 * 
	 * @param delay_Seconds   延迟多少秒
	 * @param retryTimes      重试次数
	 * @param callableMethod  核心方法
	 * @param class_exception 需要重试的异常
	 * @param result          需要重试的结果
	 * @throws Exception
	 */
	public static <T> T retryMethodWithResult(long delay_Seconds, int retryTimes,
		Callable<T> callableMethod,Class< ? extends Exception> class_exception,T retryValue) throws Exception {

		RetryerBuilder<T> builder = RetryerBuilder.<T>newBuilder();
		
		builder.withRetryListener(new RetryListener(){

			public <V> void onRetry(Attempt<V> attempt) {
				
				logger.info("重试次数 : "+ attempt.getAttemptNumber()); 
			}
		})
		// 重调策略：失败10秒后执行
		.withWaitStrategy(WaitStrategies.fixedWait(delay_Seconds, TimeUnit.SECONDS))
		
		// 尝试次数:3次
		.withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes));
		
		if(class_exception !=null){
			builder.retryIfExceptionOfType(class_exception);
		}
		if(retryValue !=null){
			builder.retryIfResult(Predicates.equalTo(retryValue)); 
		}
		
		Retryer<T> retryer = builder.build();
	
		return retryer.call(callableMethod);
        /***
         *  主要接口
         *   
			1.  Attempt	一次执行任务	
			2	AttemptTimeLimiter	单次任务执行时间限制	如果单次任务执行超时，则终止执行当前任务
			3	BlockStrategies	任务阻塞策略（通俗的讲就是当前任务执行完，下次任务还没开始这段时间做什么），默认策略为：BlockStrategies.THREAD_SLEEP_STRATEGY
			4	RetryException	重试异常	
			5	RetryListener	自定义重试监听器	可以用于异步记录错误日志
			6	StopStrategy	停止重试策略	
			7	WaitStrategy	等待时长策略	（控制时间间隔），返回结果为下次执行时长
			8	Attempt	一次执行任务
			9	Attempt	一次执行任务
       */
		/**
		  * 停止重试:StopStrategy

			StopAfterDelayStrategy：设定一个最长允许的执行时间；比如设定最长执行10s，无论任务执行次数，只要重试的时候超出了最长时间，则任务终止，并返回重试异常RetryException；
			
			NeverStopStrategy：不停止，用于需要一直轮训知道返回期望结果的情况；
			
			StopAfterAttemptStrategy：设定最大重试次数，如果超出最大重试次数则停止重试，并返回重试异常；
	   */
	    /**
		 *  FixedWaitStrategy :固定等待时长策略；
			
			RandomWaitStrategy:随机等待时长策略（可以提供一个最小和最大时长，等待时长为其区间随机值）
			
			IncrementingWaitStrategy:递增等待时长策略（提供一个初始值和步长，等待时间随重试次数增加而增加）
			
			ExponentialWaitStrategy:指数等待时长策略；
			
			FibonacciWaitStrategy:Fibonacci 等待时长策略；
			
			ExceptionWaitStrategy:异常时长等待策略；
			
			CompositeWaitStrategy:复合时长等待策略；
		 * 
		 */
		
	}

	

}
