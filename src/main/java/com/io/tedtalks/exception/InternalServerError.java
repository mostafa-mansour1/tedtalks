/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.exception;


/**
 *
 * @author mostafa
 */
public class InternalServerError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalServerError(String msg) {
        
        super(msg);
    }

   
}
