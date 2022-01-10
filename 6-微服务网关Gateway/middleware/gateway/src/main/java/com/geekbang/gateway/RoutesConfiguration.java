package com.geekbang.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

@Configuration
public class RoutesConfiguration {

    @Autowired
    private KeyResolver hostAddrKeyResolver;

    @Autowired
    @Qualifier("customerRateLimiter")
    private RateLimiter customerRateLimiter;

    @Autowired
    @Qualifier("tempalteRateLimiter")
    private RateLimiter templateRateLimiter;

    @Bean
    public RouteLocator declare(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route.path("/gateway/coupon-customer/**")
                        .filters(f -> f.stripPrefix(1)
                            .requestRateLimiter(limiter-> {
                                limiter.setKeyResolver(hostAddrKeyResolver);
                                limiter.setRateLimiter(customerRateLimiter);
                                limiter.setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
                            }
                            )
                        )
                        .uri("lb://coupon-customer-serv")
                ).route(route -> route
                        // 如果一个请求命中了多个路由，order越小的路由优先级越高
                        .order(1)
                        .path("/gateway/template/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(c-> {
                                     c.setKeyResolver(hostAddrKeyResolver);
                                     c.setRateLimiter(templateRateLimiter);
                                     c.setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
                                }
                                )
                        )
                        .uri("lb://coupon-template-serv")
                ).route(route -> route
                        .path("/gateway/calculator/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://coupon-calculation-serv")
//                )
//                .route("id-001", route -> route
//                        // 在指定时间之前
//                        .before(ZonedDateTime.parse("2022-12-25T14:33:47.789+08:00"))
//                        // 在指定时间之后
//                        .or().after(ZonedDateTime.parse("2022-12-25T14:33:47.789+08:00"))
//                        // 或者在某个时间段以内
//                        .or().between(
//                                ZonedDateTime.parse("2021-12-25T14:33:47.789+08:00"),
//                                ZonedDateTime.parse("2022-12-25T14:33:47.789+08:00"))
//                        .uri("http://time.geekbang.org")
//                ).route(route -> route
//                        .path("/test/**")
//                        .uri("http://www.test.com")
            ).build();

    }

}
