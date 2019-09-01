package com.sinosoft.payGateway.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Function;
/**注意导入包是否齐全**/
import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	
	// 定义分隔符
    private static final String splitor = ";";
    
	@Bean
	public Docket createRestApi() {
		
		//api描述
		ApiInfo apiInfo=  new ApiInfoBuilder()
				.title("支付网关系统的Api文档")
				.description("采用Swagger2自动生成的restful风格Api文档")
				.version("1.0")
				.build();
	    //通过参数构造器为swagger添加对header参数的支持，如果不需要的话可以删掉
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("token")
		         .description("用户的Token信息（可选）")
		         .modelRef(new ModelRef("string")).parameterType("header")
		         .required(false).build(); //header中的token参数现在设置为非必填，传空也可以
		pars.add(ticketPar.build());
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo)
				.select()
				//单个包的写法：.apis(RequestHandlerSelectors.basePackage("ins.platform.dcode.api"))
				.apis(basePackage("ins.platform.dcode.api"+splitor+"com.sinosoft.payGateway.payment.api"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars); //如果没有全局参数这一行可以删掉
	}
    //由于swagger默认不支持多个包，下面代码是为了支持多包
	public static Predicate<RequestHandler> basePackage(final String basePackage) {
		return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
	}

	private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
		return input -> {
			// 循环判断匹配
			for (String strPackage : basePackage.split(splitor)) {
				boolean isMatch = input.getPackage().getName().startsWith(strPackage);
				if (isMatch) {
					return true;
				}
			}
			return false;
		};
	}

	@SuppressWarnings("deprecation")
	private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
		return Optional.fromNullable(input.declaringClass());
	}

	

}



