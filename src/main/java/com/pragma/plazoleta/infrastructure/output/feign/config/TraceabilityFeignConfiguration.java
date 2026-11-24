package com.pragma.plazoleta.infrastructure.output.feign.config;

import com.pragma.plazoleta.infrastructure.output.feign.security.ServiceTokenProvider;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class TraceabilityFeignConfiguration {
    private final ServiceTokenProvider serviceTokenProvider;

    @Bean
    public RequestInterceptor serviceTokenInterceptor() {
        return template -> template.header(
                "Authorization",
                "Bearer " + serviceTokenProvider.generateToken()
        );
    }
}