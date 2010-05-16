package com.tomecode.soa.wsdl;

/**
 * operation in wsdl
 * 
 * @author Tomas Frastia
 * 
 */
public final class WsdlOperation {

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

}
