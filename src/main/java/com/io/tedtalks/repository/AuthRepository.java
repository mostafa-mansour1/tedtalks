/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.repository;

/**
 *
 * @author mostafa
 */
public interface AuthRepository {

    /**
     * Validate Basic Authentication.
     *
     * @param userName the user name
     * @param password the password
     * @param basicAuthHeaderValue the base 64 encoded header value
     * @return the boolean
     */
    Boolean validateBasicAuthentication(String userName, String password, String basicAuthHeaderValue);
}
