package com.tomecode.soa.oracle10g.parser;

/**
 * 
 * The basic exeception, if something is not right in the parser for BPEL and
 * ESB
 * 
 * @author Tomas Frastia
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
