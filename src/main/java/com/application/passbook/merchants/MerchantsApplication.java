package com.application.passbook.merchants;

import com.application.passbook.merchants.security.AuthCheckInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@SpringBootApplication
public class MerchantsApplication implements WebMvcConfigurer {

    @Resource
    private AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
                .addPathPatterns("/merchants/**");
    }

    public static void main(String[] args) {
        SpringApplication.run(MerchantsApplication.class, args);
    }

}
