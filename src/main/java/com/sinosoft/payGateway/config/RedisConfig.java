package com.sinosoft.payGateway.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfig {
	
	/**
	 * 
	 * Lettuce 和 Jedis 的区别
		
		（1）Lettuce 和 Jedis 的定位都是Redis的client，所以他们当然可以直接连接redis server。
		
		（2）Jedis在实现上是直接连接的redis server，如果在多线程环境下是非线程安全的，这个时候只有使用连接池，为每个Jedis实例增加物理连接
		     用JedisPool解决
		
		（3）Lettuce的连接是基于Netty的，连接实例（StatefulRedisConnection）可以在多个线程间并发访问，应为StatefulRedisConnection是线程安全的，
		     所以一个连接实例（StatefulRedisConnection）就可以满足多线程环境下的并发访问，当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
		
		（4）springboot2之前redis的连接池为jedis，2.0以后redis的连接池改为了lettuce，lettuce能够支持redis4，需要java8及以上。lettuce是基于netty实现的与redis进行同步和异步的通信


	 */
	  /**
	   * Spring自动配置的RedisTemplate的泛型是<Object,Object>，不是很方便，所以要重写下覆盖@ConditionalOnMissingBean(name = "redisTemplate")
	   * 注意，@Bean的名称为redisTemplate，所以方法名称可以任意了。
	   *     如果没有指定Bean的名称，那么默认是方法名称，这时方法的名称只能是redisTemplate
	   * 2.0开始默认的实现是Lettuce
	   * @param factory 
	   * @return
	   */
	  @Bean("redisTemplate")
	  public RedisTemplate<String,Object> createRedisTemplate(RedisConnectionFactory factory){
	    RedisTemplate<String,Object> template = new RedisTemplate <>();
	    template.setConnectionFactory(factory);

	    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	    ObjectMapper om = new ObjectMapper();
	    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	    jackson2JsonRedisSerializer.setObjectMapper(om);

	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	    // key采用String的序列化方式
	    template.setKeySerializer(stringRedisSerializer);
	    // hash的key也采用String的序列化方式
	    template.setHashKeySerializer(stringRedisSerializer);
	    // value序列化方式采用jackson
	    template.setValueSerializer(jackson2JsonRedisSerializer);
	    // hash的value序列化方式采用jackson
	    template.setHashValueSerializer(jackson2JsonRedisSerializer);
	    template.afterPropertiesSet();

	    return template;
	  }
	 /****Redis 缓存**/
	 @Bean
     public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		    RedisSerializer<String> redisSerializer = new StringRedisSerializer();
	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
	        //解决查询缓存转换异常的问题
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);

	        // 配置序列化（解决乱码的问题）
	        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofHours(2))//默认两个小时
	                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
	                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
	                .disableCachingNullValues();
	        
	        //设置单个缓存的超时时间
			Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
			initialCacheConfigurations.put("user2",config.entryTtl(Duration.ofSeconds(60)));
			
	        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
	                .cacheDefaults(config)
	                .withInitialCacheConfigurations(initialCacheConfigurations)
	                .build();
        return cacheManager;
    }
}
