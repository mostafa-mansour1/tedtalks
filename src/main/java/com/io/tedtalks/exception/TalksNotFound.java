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
public class TalksNotFound extends RuntimeException {
     private static final long serialVersionUID = 1L;

    public TalksNotFound(String msg) {
        super(msg);
    }
}
