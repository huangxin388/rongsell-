package com.bupt.rongsell.config.web;


import com.bupt.rongsell.filter.CrossFilter;
import com.bupt.rongsell.filter.SessionExpireFilter;
import com.bupt.rongsell.interceptor.AuthorityInterceptor;
import com.bupt.rongsell.interceptor.LoginInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 开启Mvc,自动注入spring容器。 WebMvcConfigurerAdapter：配置视图解析器
 * 当一个类实现了这个接口（ApplicationContextAware）之后，这个类就可以方便获得ApplicationContext中的所有bean
 * @Author huang xin
 * @Date 2019/11/18 9:46
 * @Version 1.0
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer, ApplicationContextAware {
    /**
     * Spring容器
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



    /**
     * 定义默认的请求处理器
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 文件上传解析器
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver createMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("utf-8");
        // 1024 * 1024 * 500 = 500M
        multipartResolver.setMaxUploadSize(524288000);
        multipartResolver.setMaxInMemorySize(524288000);
        return multipartResolver;
    }

    @Bean
    public SessionExpireFilter loadSessionExpireFilter() {
        return new SessionExpireFilter();
    }

    @Bean
    public FilterRegistrationBean crossFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 跨域
        registration.setFilter(new CrossFilter());
        // 重置session过期时间
        registration.setFilter(loadSessionExpireFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("crossFilter");
        return registration;
    }

    @Bean
    public AuthorityInterceptor loadAuthorityInterceptor() {
        return new AuthorityInterceptor();
    }

    @Bean
    public LoginInterceptor loadLoginInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * /** 所有路径及里面的子路径
     * /*当前路径下的所有路径，不包含子路径
     * /web项目的根路径请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration authorityRegistration = registry.addInterceptor(loadAuthorityInterceptor());
        authorityRegistration.addPathPatterns("/manage/**");
//        InterceptorRegistration loginRegistration = registry.addInterceptor(loadLoginInterceptor());
//        List<String> interceptLoginPatterns = new ArrayList<>();
//        interceptLoginPatterns.add("/cart/addproduct");
//        interceptLoginPatterns.add("/cart/updateProductInfo");
//        interceptLoginPatterns.add("/cart/deleteProduct");
//        interceptLoginPatterns.add("/cart/getcartinfo");
//        loginRegistration.addPathPatterns(interceptLoginPatterns);
    }
}
