package com.sinosoft.payGateway.interceptor;


import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.ImmutableList;
import com.sinosoft.payGateway.exception.BusinessException;

import ins.platform.annotation.Limit;
import ins.platform.common.LimitType;
import ins.platform.kit.HttpKit;

/**
 * 使用redis的分布式实现。
 * 固定窗口（计数器法）
 * @author Administrator
 *
 */
@Aspect
@Configuration
public class LimitInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LimitInterceptor.class);;
    
    private final String REDIS_SCRIPT = buildLuaScript();
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Around("execution(public * *(..)) && @annotation(ins.platform.annotation.Limit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnno = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnno.limitType();
        String name = limitAnno.name();
        
        String key = null;
        int limitPeriod = limitAnno.period();
        int limitCount = limitAnno.count();
        switch (limitType) {
        case IP:
        	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            key = HttpKit.getIpAddr(request);
            break;
        case CUSTOMER:
            // TODO 如果此处想根据表达式或者一些规则生成 请看 一起来学Spring Boot | 第二十三篇：轻松搞定重复提交（分布式锁）
            key = limitAnno.key();
            break;
        default:
            break;
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnno.prefix(), key));
        try {
            RedisScript<Number> redisScript = new DefaultRedisScript<Number>(REDIS_SCRIPT, Number.class);
            Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            logger.info("Access try count is {} for name={} and key = {}", count, name, key);
            if(count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                throw new BusinessException("访问过于频繁！");
            }
        } catch (Throwable e) {
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            }
            throw new RuntimeException("限流服务异常");
        }
    }
    
    /**
     * 限流 脚本:计数器的限流方式
     *   缺点：无法处理临界的时候，大量请求的的情况
     * @return lua脚本
     */
    private String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c")
           .append("\nc = redis.call('get', KEYS[1])")
           // 调用不超过最大值，则直接返回
           .append("\nif c and tonumber(c) > tonumber(ARGV[1]) then")
           .append("\nreturn c;")
           .append("\nend")
           // 执行计算器自加
           .append("\nc = redis.call('incr', KEYS[1])")
           .append("\nif tonumber(c) == 1 then")
           // 从第一次调用开始限流，设置对应键值的过期
           .append("\nredis.call('expire', KEYS[1], ARGV[2])")
           .append("\nend")
           .append("\nreturn c;");
        return lua.toString();
    }
}