package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - HTTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointHttp extends EndpointConfig {

	private ProviderSpecificHttp providerSpecificHttp;

	public EndpointHttp() {
		super(ProviderProtocol.HTTP);
	}

	/**
	 * @return the providerSpecificHttp
	 */
	public final ProviderSpecificHttp getProviderSpecificHttp() {
		return providerSpecificHttp;
	}

	/**
	 * @param providerSpecificHttp
	 *            the providerSpecificHttp to set
	 */
	public final void setProviderSpecificHttp(ProviderSpecificHttp providerSpecificHttp) {
		this.providerSpecificHttp = providerSpecificHttp;
	}

}
