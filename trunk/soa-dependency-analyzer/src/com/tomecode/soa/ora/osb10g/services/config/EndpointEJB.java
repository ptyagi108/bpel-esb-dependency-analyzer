package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - EJB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointEJB extends EndpointConfig {

	private ProviderSpecificEJB providerSpecificEJB;

	public EndpointEJB() {
		super(ProviderProtocol.EJB);
	}

	/**
	 * @return the providerSpecificEJB
	 */
	public final ProviderSpecificEJB getProviderSpecificEJB() {
		return providerSpecificEJB;
	}

	/**
	 * @param providerSpecificEJB
	 *            the providerSpecificEJB to set
	 */
	public final void setProviderSpecificEJB(ProviderSpecificEJB providerSpecificEJB) {
		this.providerSpecificEJB = providerSpecificEJB;
	}

}
