package com.tomecode.soa.wsdl;

import java.util.ArrayList;
import java.util.List;

/**
 * portType in wsdl file
 * 
 * @author Tomas Frastia
 * 
 */
public final class PortType {

	private String name;

	private List<WsdlOperation> wsdlOperations;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public PortType(String name) {
		this.name = name;
		this.wsdlOperations = new ArrayList<WsdlOperation>();
	}

	public final String getName() {
		return name;
	}

	public final List<WsdlOperation> getWsdlOperations() {
		return wsdlOperations;
	}

	/**
	 * add new {@link WsdlOperation}
	 * 
	 * @param wsdlOperation
	 */
	public final void addWsdlOperations(WsdlOperation wsdlOperation) {
		wsdlOperations.add(wsdlOperation);
	}

}
