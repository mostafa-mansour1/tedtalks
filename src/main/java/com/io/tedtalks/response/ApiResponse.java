/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.response;

/**
 *
 * @author mostafa
 */
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiResponse {

    private Integer statusCode;
//    private Integer errorCode;
    private Date timestamp;
    private String message;
//    private Object body;
    private List<String> errors;

  

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(statusCode);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
//    public Integer getErrorCode() {
//        return errorCode;
//    }
//
//    public void setErrorCode(Integer errorCode) {
//        this.errorCode = errorCode;
//    }
    
    
//    public Object getBody() {
//        return body;
//    }
//
//    public void setBody(Object body) {
//        this.body = body;
//    }


    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }

    public void setError(final String error) {
        errors = Arrays.asList(error);
    }

    public ApiResponse( final String message,final HttpStatus status) {
        super();
          this.statusCode = status.value();
        this.timestamp = new Date();
        this.message = message;
    }
    
      
    public ApiResponse(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.statusCode = status.value();
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public ApiResponse(final HttpStatus status, final String message, final String error) {
        super();
        this.statusCode = status.value();
        this.message = message;
        this.timestamp = new Date();
        errors = Arrays.asList(error);
    }

    public ApiResponse(Integer statusCode,
            Date timestamp, 
            String message
    ) {
        super();
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
    }

}
