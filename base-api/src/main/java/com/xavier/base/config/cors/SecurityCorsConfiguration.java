package com.xavier.base.config.cors;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class SecurityCorsConfiguration {

    private static final String PATH_MATCHED_STR = "/**";

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);

        List<String> list = Collections.singletonList(CorsConfiguration.ALL);
        corsConfiguration.setAllowedOrigins(list);
        corsConfiguration.setAllowedHeaders(list);
        corsConfiguration.setAllowedMethods(list);

        source.registerCorsConfiguration(PATH_MATCHED_STR, corsConfiguration);
        return new FilterRegistrationBean(new CorsFilter(source));
    }
}
