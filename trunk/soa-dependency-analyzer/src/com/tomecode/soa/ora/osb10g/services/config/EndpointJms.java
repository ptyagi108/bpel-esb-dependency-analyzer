package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - JMS
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointJms extends EndpointConfig {

	private ProviderSpecificJms providerSpecificJms;

	public EndpointJms() {
		super(ProviderProtocol.JMS);
	}

	/**
	 * @return the providerSpecificJms
	 */
	public final ProviderSpecificJms getProviderSpecificJms() {
		return providerSpecificJms;
	}

	/**
	 * @param providerSpecificJms
	 *            the providerSpecificJms to set
	 */
	public final void setProviderSpecificJms(ProviderSpecificJms providerSpecificJms) {
		this.providerSpecificJms = providerSpecificJms;
	}

}
