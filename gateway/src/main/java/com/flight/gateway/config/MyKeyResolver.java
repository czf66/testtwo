package com.flight.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Component
public class MyKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        final String path = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        return Mono.just(path);
    }
}
