/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.interceptor;

import com.io.tedtalks.repository.AuthRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mostafa
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {

    private final Logger log = LogManager.getLogger(getClass());

    private static final String AUTH_HEADER_PARAMETER_AUTHERIZATION = "authorization";

    // Get application user name from props.
    @Value("${app.api.basic.username}")
    private String userName;

    // Get application password from props.
    @Value("${app.api.basic.password}")
    private String password;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if ("/".equals(request.getRequestURI())) {
            return true;
        }
        Boolean isValidBasicAuthRequest = false;

        log.info("[Inside PRE Handle interceptor][" + request + "]" + "[" + request.getMethod() + "]"
                + request.getRequestURI());

        try {

            // Grab basic header value from request header object.
            String basicAuthHeaderValue = request.getHeader(AUTH_HEADER_PARAMETER_AUTHERIZATION);

            // Process basic authentication
            isValidBasicAuthRequest = authRepository.validateBasicAuthentication(userName, password,
                    basicAuthHeaderValue);

            // If this is invalid request, then set the status as UNAUTHORIZED.
            if (!isValidBasicAuthRequest) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        } catch (Exception e) {
            log.error("Error occured while authenticating request : " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return isValidBasicAuthRequest;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        log.info("[Inside POST Handle Interceptor]" + request.getRequestURI());

    }
}
