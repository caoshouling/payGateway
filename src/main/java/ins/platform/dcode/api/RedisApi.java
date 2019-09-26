package ins.platform.dcode.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.payGateway.common.JsonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="/redis", tags="redis测试接口")
@Controller
@RequestMapping("/redis")
public class RedisApi {
   
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
  
    
    @ApiOperation(value = "简单测试", notes = "测试redis是否能用")
	@GetMapping("/hello")
	@ResponseBody
	public JsonResponse redisTest () throws Exception {
		
		stringRedisTemplate.opsForValue().set("name", "caoshouling");
		String result= " hello " + stringRedisTemplate.opsForValue().get("name");
		return new JsonResponse(result).success();
	}
	@ApiOperation(value = "redis低级别的API操作", notes = "RedisConnection低级别的API操作")
	@GetMapping("/connection")
	@ResponseBody
	public JsonResponse connection () throws Exception {
		String name = "admin";
		stringRedisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				
				String password = "123";
				connection.set(name.getBytes(), password.getBytes());
				return null;
			}
		
		});
		
		String result = stringRedisTemplate.opsForValue().get(name);
		
		return new JsonResponse(result).success();
	}
	
	/**
	 * 
	 * 
	 *  // send message through connection RedisConnection con = ...
		byte[] msg = ...
		byte[] channel = ...
		con.publish(msg, channel); 

		// send message through RedisTemplate
		RedisTemplate template = ...
		template.convertAndSend("hello!", "world");
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "redis 发布订阅发消息", notes = "convertAndSend发布订阅发消息")
	@GetMapping("/sendMessage")
	@ResponseBody
	public JsonResponse sendMsg () throws Exception {
		
		stringRedisTemplate.convertAndSend("news", "hello world");
		
		return new JsonResponse().success();
	}
}
