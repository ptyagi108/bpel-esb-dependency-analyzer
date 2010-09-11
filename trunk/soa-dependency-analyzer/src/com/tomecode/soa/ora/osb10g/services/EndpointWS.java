package com.tomecode.soa.ora.osb10g.services;

import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;

/**
 * Endpoint protocol - WS
 * 
 * @author Tomas Frastia
 * 
 */
public final class EndpointWS extends EndpointConfig {

	private String endPointUri;

	public EndpointWS(String endPointUri) {
		super(ProviderProtocol.WS);
		this.endPointUri = endPointUri;
	}

	/**
	 * @return the endPointUri
	 */
	public final String getEndPointUri() {
		return endPointUri;
	}

}
