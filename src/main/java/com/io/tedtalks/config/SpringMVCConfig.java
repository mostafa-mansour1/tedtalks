/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.config;

import com.io.tedtalks.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author mostafa
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        System.out.println("----------->addInterceptors");
        registry.addInterceptor(securityInterceptor);
    }
}
