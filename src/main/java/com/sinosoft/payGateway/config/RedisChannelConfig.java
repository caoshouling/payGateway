package com.sinosoft.payGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
/**
 * redis 的发布和订阅
 *
 **/
@Configuration
public class RedisChannelConfig{
	
	
	@Bean
	public MessageListenerAdapter listenerAdapter( ) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(new RedisChannelListenner());
		adapter.setSerializer(new JdkSerializationRedisSerializer());
		return adapter;
		
	}
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅所有news.* 频道内容
        container.addMessageListener(listenerAdapter, new PatternTopic("news"));
        return container;
    }
}

