package com.bupt.core.config.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.util.Hashing;
import redis.clients.jedis.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * 分布式的redis配置文件
 * @Author huang xin
 * @Date 2020/4/9 22:04
 * @Version 1.0
 */
@Configuration
public class SharedRedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.host2}")
    private String host2;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.port2}")
    private int port2;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.maxTotal}")
    private int maxTotal;

    @Value("${spring.redis.jedis.pool.maxIdle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.minIdle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.redis.jedis.pool.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.redis.jedis.pool.maxWait}")
    private long maxWaitMillis;

    @Value("${spring.redis.jedis.pool.blockWhenExhausted}")
    private boolean  blockWhenExhausted;

    Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public ShardedJedisPool shardedRedisPoolFactory()  throws Exception{
        logger.info("JedisPool注入成功！！");
        logger.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisShardInfo info1 = new JedisShardInfo(host, port, timeout);
        info1.setPassword(password);
        JedisShardInfo info2 = new JedisShardInfo(host2, port2, timeout);
        info2.setPassword(password);
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);
        // MURMUR_HASH  一致性算法
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,
                jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
        return shardedJedisPool;
    }
}
