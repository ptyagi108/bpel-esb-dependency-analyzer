package com.tomecode.soa.wsdl;

import java.io.Serializable;

/**
 * operation in wsdl
 * 
 * @author Tomas Frastia
 * 
 */
public final class WsdlOperation implements Serializable {

	private static final long serialVersionUID = 8680563798539886063L;
	/**
	 * wsdl operation name
	 */
	private String name;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public WsdlOperation(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final String toString() {
		return name;
	}
}
