package com.flight.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //配置跨域
        //允许哪种方法类型跨域 get post delete put
        config.addAllowedMethod("*");
        // 允许哪些请求源跨域
        config.addAllowedOrigin("*");
        //允许哪种请求头跨域
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        //允许跨域的路径
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}