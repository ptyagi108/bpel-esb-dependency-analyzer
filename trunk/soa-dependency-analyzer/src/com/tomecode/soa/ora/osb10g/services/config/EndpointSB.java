package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - SB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointSB extends EndpointConfig {

	private String serviceName;

	public EndpointSB(String serviceName) {
		super(ProviderProtocol.SB);
		this.serviceName = serviceName;
	}

	/**
	 * @return the serviceName
	 */
	public final String getServiceName() {
		return serviceName;
	}

}
