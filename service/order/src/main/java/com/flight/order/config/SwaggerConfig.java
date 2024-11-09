package com.flight.order.config;

import com.flight.swagger.config.BaseSwaggerConfig;
import com.flight.swagger.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Lwwwwaaa
 * @since 2022/10/17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.flight.order.controller")
                .title("智能航班")
                .description("智能航班订单接口文档")
                .contactName("")
                .version("1.0")
//                .enableSecurity(true)
                .build();
    }
}
