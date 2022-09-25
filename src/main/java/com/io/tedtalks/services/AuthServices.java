/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.services;

import com.io.tedtalks.repository.AuthRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
/**
 *
 * @author mostafa
 */
@Service
public class AuthServices implements AuthRepository {
    @Override
	public Boolean validateBasicAuthentication(String appUserName, String appPassword, String basicAuthHeaderValue) {

		if (StringUtils.hasText(basicAuthHeaderValue) && basicAuthHeaderValue.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = basicAuthHeaderValue.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);

			if (values.length == 2) {
				String username = values[0];
				String password = values[1];
				if (appUserName.equals(username) && appPassword.equals(password)) {
					return true;
				}
			}
		}
		return false;

	}
}
