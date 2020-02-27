package com.yefeng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfigration {
    @Bean(name= "jedis.pool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("jedis.config") JedisPoolConfig config,
                               @Value("${jedis.pool.host}")String host,
                               @Value("${jedis.pool.port}")int port,
                               @Value("${jedis.pool.timeout}")int timeout,
                               @Value("${jedis.pool.password}")String password) {
        return new JedisPool(config, host, port,timeout,password);
    }

    @Bean(name= "jedis.config")
    public JedisPoolConfig jedisPoolConfig (@Value("${jedis.config.maxTotal}")int maxTotal,
                                            @Value("${jedis.config.maxIdle}")int maxIdle,
                                            @Value("${jedis.config.maxWaitMillis}")int maxWaitMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }
}
