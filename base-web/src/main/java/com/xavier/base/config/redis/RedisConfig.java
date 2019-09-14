package com.xavier.base.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.expire-time:1200}")
	private Long EXPIRE_TIME;

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {


		RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(
						RedisCacheConfiguration
								.defaultCacheConfig()
								.entryTtl(Duration.ofSeconds(EXPIRE_TIME))
				)
				.build();

		return cacheManager;
	}
}
