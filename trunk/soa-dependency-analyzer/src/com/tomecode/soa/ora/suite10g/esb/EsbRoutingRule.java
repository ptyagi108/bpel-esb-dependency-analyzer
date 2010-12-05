package com.tomecode.soa.ora.suite10g.esb;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * routing rule in ESB
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EsbRoutingRule {

	private static final long serialVersionUID = -342513167744595428L;
	private String qname;
	private String serviceQName;

	/**
	 * Constructor
	 * 
	 * @param qname
	 * @param serviceQName
	 */
	public EsbRoutingRule(String qname, String serviceQName) {
		this.qname = qname;
		this.serviceQName = serviceQName;
	}

	public final String getQname() {
		return qname;
	}

	public final String getServiceQName() {
		return serviceQName;
	}

	public final String toString() {
		return serviceQName;
	}

}
