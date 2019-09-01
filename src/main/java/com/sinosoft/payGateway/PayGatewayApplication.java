package com.sinosoft.payGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;



@SpringBootApplication
@ComponentScan
public class PayGatewayApplication{

	public static void main(String[] args) {
		SpringApplication.run(PayGatewayApplication.class, args);
	}

}
