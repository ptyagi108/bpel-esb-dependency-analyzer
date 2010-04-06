package com.tomecode.soa.bpel.dependency.analyzer.parser;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public class BpelParserException extends Exception {

	private boolean userException;

	public BpelParserException(Exception e) {
		super(e.getMessage(), e);
	}

	public BpelParserException(String message, boolean userException) {
		super(message);
		this.userException = userException;
	}

	public final boolean isUserException() {
		return userException;
	}

	private static final long serialVersionUID = -6809950576963369778L;

}
