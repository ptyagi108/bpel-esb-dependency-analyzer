package com.tomecode.soa.parser;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * The basic exception, if something is not right in the parser for BPEL or ESB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class ServiceParserException extends Exception {

	private static final long serialVersionUID = -6809950576963369778L;

	private boolean userException;

	/**
	 * Constructor
	 * 
	 * @param e
	 */
	public ServiceParserException(Exception e) {
		super(e);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            custom 'user' error message
	 * @param userException
	 */
	public ServiceParserException(String message, boolean userException) {
		super(message);
		this.userException = userException;
	}

	public final boolean isUserException() {
		return userException;
	}

}
