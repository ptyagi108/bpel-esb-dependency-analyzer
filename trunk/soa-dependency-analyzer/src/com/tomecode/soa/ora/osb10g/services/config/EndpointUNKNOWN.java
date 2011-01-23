package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - UNKNOWN - custom adapter
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointUNKNOWN extends EndpointConfig {

	private String providerId;

	public EndpointUNKNOWN() {
		super(ProviderProtocol.UNKNOWN);
	}

	/**
	 * @return the providerId
	 */
	public final String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public final void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public final String toString() {
		return "Unknow Endpoint - providerId: " + providerId;
	}

}
