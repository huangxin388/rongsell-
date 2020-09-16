package com.bupt.apizuul.config;

import com.bupt.apizuul.filters.CrossFilter;
import com.bupt.apizuul.filters.SessionExpireFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huang xin
 * @Date 2020/9/15 18:17
 * @Version 1.0
 */
@Configuration
public class ZuulConfig {

    @Bean
    public CrossFilter crossFilter() {
        return new CrossFilter();
    }

    @Bean
    public SessionExpireFilter sessionExpireFilter() {
        return new SessionExpireFilter();
    }
}
