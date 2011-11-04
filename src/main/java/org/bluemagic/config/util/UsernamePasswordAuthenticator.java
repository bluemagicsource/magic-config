package org.bluemagic.config.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
*Generic url username password implementation.
**/
public class UsernamePasswordAuthenticator extends Authenticator {

	/**
	* Default Constructor
	*@param username - Required, String
	*@param password - Required, char[]
	*/
	public UsernamePasswordAuthenticator(String username, char[] password) {

	   this.username = username;
	   this.password = password;

  	}
	
	/**
	* Provides the username and password authentication for any url connection.
	**/
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {

	   return new PasswordAuthentication(username, password);
	}

	private String username = null;
	private char[] password = null;
}
