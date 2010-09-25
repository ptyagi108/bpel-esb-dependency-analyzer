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

	private String endpointUri;

	public EndpointHttp(String endpointUri) {
		super(ProviderProtocol.HTTP);
		this.endpointUri = endpointUri;
	}

	/**
	 * @return the endpointUri
	 */
	public final String getEndpointUri() {
		return endpointUri;
	}

}
