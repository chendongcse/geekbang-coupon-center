package com.geekbang.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RedisLimiterConfig {

    @Bean
    @Primary
    public KeyResolver remoteHostLimiterKey() {
        return exchange -> Mono.just(
                exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress()
        );
    }

    @Bean("tempalteRateLimiter")
    public RedisRateLimiter templateRateLimiter() {
        return new RedisRateLimiter(10, 20);
    }

    @Bean("customerRateLimiter")
    public RedisRateLimiter customerRateLimiter() {
        return new RedisRateLimiter(1, 1);
    }

    @Bean("defaultRateLimiter")
    @Primary
    public RedisRateLimiter defaultRateLimiter() {
        return new RedisRateLimiter(50, 100);
    }
}
