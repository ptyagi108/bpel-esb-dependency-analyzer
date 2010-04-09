package com.tomecode.soa.oracle10g.esb;

/**
 * 
 * @author Frastia Tomas
 * 
 */
public final class EsbRoutingRule {

	private String qname;
	private String serviceQName;

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

}
