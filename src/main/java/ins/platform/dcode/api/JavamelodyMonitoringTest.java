package ins.platform.dcode.api;

import java.util.Random;

import org.springframework.stereotype.Component;

import net.bull.javamelody.MonitoredWithSpring;
@Component
public class JavamelodyMonitoringTest {

	/**
	 * 自动监控的：
	 *   @Controller, @RestController, @Service, @Async, @FeignClient 
	 *   RestTemplate  bean
	 *   @Async, @Scheduled
	 *   @Schedules 
	 * 对于@Component或其他定义为Spring Bean的方法：加入@MonitoredWithSpring
	 * 
	 * @throws InterruptedException
	 */
	@MonitoredWithSpring
	public  int JavamelodyMonitoringMethod() throws InterruptedException{
		
		int a =   new Random().nextInt(3);
		Thread.sleep(a*1000);
		return a*1000;
	}
	
	
} 
