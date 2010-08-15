package com.tomecode.soa.oracle10g.esb;


/**
 * 
 * @author Frastia Tomas
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
