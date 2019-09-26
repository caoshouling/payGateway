package com.sinosoft.payGateway.config;

import java.io.UnsupportedEncodingException;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


public class RedisChannelListenner implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] pattern) {
		
		byte[] channal = message.getChannel();
		byte[]  bs = message.getBody();
	      try {
	        String content = new String(bs,"UTF-8");
	        String p = new String(channal,"UTF-8");
	        System.out.println("get "+content+" from "+p);
	      } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	      }
	}

}
