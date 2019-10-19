package com.sinosoft.payGateway.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

/**
 * 
 * 
 限流算法：

	  固定窗口（计数器法）：最简单的算法。比如我们规定，对于A接口来说，我们1分钟的访问次数不能超过100个。
	            缺点：临界问题。比如规定1分钟100并发。但是在00:30 - 01:00秒和01:00 - 01:30秒期间内各自多可以达到100.
		         这样00:30 - 01:30秒内的并发就达到了200
	
	  滑动窗口：滑动窗口，又称rolling window。为了解决计数器法统计精度太低的问题，引入了滑动窗口算法。
	           一个时间窗口就是一分钟。然后我们将时间窗口进行划分。
		   滑动窗口也出现临界问题。当滑动窗口的格子划分的越多，那么滑动窗口的滚动就越平滑，限流的统计就会越精确
		   
	
	  漏桶算法：恒定的速率输出。当桶满了之后，多余的水将会溢出。
	
	
	  令牌桶算法：固定容量的桶，恒定的速率放令牌。如果桶满了，那么丢弃多余的。
	              必须获得令牌才能消费。
		      特点：能应对突发流量，可以优先考虑
	   
	  比较：
	     （1）漏桶算法和令牌桶算法最明显的区别是令牌桶算法允许流量一定程度的突发。
	     （2）Semaphore：限制了并发访问的数量而不是使用速率。同一时间中，只允许多少数量的并发。
		  限流：某一段时间内允许多少并发。

 * Guava限流算法：
 * 
 * Guava提供的RateLimiter可以限制物理或逻辑资源的被访问速率，咋一听有点像java并发包下的Samephore，但是又不相同，
 *  RateLimiter控制的是速率，Samephore控制的是并发量。
 *  RateLimiter的原理类似于令牌桶，它主要由许可发出的速率来定义，如果没有额外的配置，许可证将按每秒许可证规定的固定速度分配，许可将被平滑地分发，
 *   若请求超过permitsPerSecond则RateLimiter按照每秒 1/permitsPerSecond 的速率释放许可。
 * 
 * RateLimiter有两个实现类：SmoothBursty和SmoothWarmingUp（预热加令牌）
 * 
 * tryAcquire一般用来实现令牌桶算法。
 * limiter.tryAcquire() 尝试获取，返回true或者false。
 * limiter.tryAcquire(1, 1000, TimeUnit.MILLISECONDS) 可以等待，如果超时就返回false
 * 
 * acquire()能保证特定的最快速率消费，返回的是等待时间；可以用来实现漏桶算法，需要人工编码实现漏桶容器。
 * double waitTime = limiter.acquire(count) 方法传入的参数是需要的令牌个数，当令牌不足时会进行等待，该方法返回的是等待的时间
 *    acquire() 等价于 acquire(1)
 *    比如说，设置的每秒处理个数是2，那么limiter.acquire();如果请求间隔小于500毫秒，那么会阻塞达到500毫秒后才执行完释放。
 *    如果获取个数比较多。每秒处理个数是2，acquire(4);那么需要两秒才能获取到4个，会等待两秒
 *     
 */
public class GuavaRatelimitTest {
	private static final Logger logger = LoggerFactory.getLogger(GuavaRatelimitTest.class);

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
		
		LimitCount();
	}
	//令牌算法；单位时间处理请求数。 和Qps和Tps相关
	public static void RateLimit() throws Exception {
		
		String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	    RateLimiter limiter = RateLimiter.create(2); // 这里的1表示每秒允许处理的量为2个
	    for (int i = 1; i <= 10; i++) {
	    	
	    	//1秒内还是获取不到就返回false
//	    	if(limiter.tryAcquire(1, 1000, TimeUnit.MILLISECONDS)){
//	        	 System.out.println(i+"-获取成功：true");
//	        }else{
//	        	 System.err.println(i+"-获取失败：false");
//	        }
	    	//返回的是等待的时间
	        double rate = limiter.acquire();// 请求RateLimiter, 超过permits会被阻塞   acquire() 等价于 acquire(1)
	        System.out.println(i+"-等待时间：" + rate);
	        
	    }
	    String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	    System.out.println("start time:" + start);
	    System.out.println("end time:" + end);
		System.out.println("正常结束，返回值：");
	}
	/**
	 * 固定窗口算法（计数器算法）
	 * @author Administrator
	 *
	 */
	private class CounterTest {
	    public long timeStamp = getNowTime();
	    public int reqCount = 0;
	    public final int limit = 100; // 时间窗口内最大请求数
	    public final long interval = 1000; // 时间窗口ms

	    public boolean grant() {
	        long now = getNowTime();
	        if (now < timeStamp + interval) {
	            // 在时间窗口内
	            reqCount++;
	            // 判断当前时间窗口内是否超过最大请求控制数
	            return reqCount <= limit;
	        } else {
	            timeStamp = now;
	            // 超时后重置
	            reqCount = 1;
	            return true;
	        }
	    }

	    public long getNowTime() {
	        return System.currentTimeMillis();
	    }
	}
	
	
    /**
	 * 限制一段时间总次数：限制某个接口或服务 每秒/每分钟/每天 的请求次数或调用次数。例如限制服务每秒的调用次数为50
	 */
    public static void LimitCount() throws Exception {
    	
    	 long permit = 50;//
    	 LoadingCache<Long, AtomicLong> counter =
    	            CacheBuilder.newBuilder()
    	                    .expireAfterWrite(2, TimeUnit.SECONDS)
    	                    .build(new CacheLoader<Long, AtomicLong>() {
    	                        @Override
    	                        public AtomicLong load(Long seconds) throws Exception {
    	                            return new AtomicLong(0);
    	                        }
    	                    });
    	 for (int i = 1; i <= 100; i++) {
    		    //得到当前秒
    	        long currentSeconds = System.currentTimeMillis() / 1000;
    	        
    	        System.err.println(currentSeconds + " = "+counter.get(currentSeconds));
    	        
    	        if(counter.get(currentSeconds).incrementAndGet() > permit -1) {
    	        	System.err.println(" 消费太频繁了，请稍后再试！");
    	        }else{
    	        	System.err.println(" 业务处理-->");
    	        }
    		
    	}
       
 
    }
}
