package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - JCA
 * 
 * @author Tomas Frastia
 * 
 */
public final class EndpointJca extends EndpointConfig {

	private final String jcaUri;

	public EndpointJca(String jcaUri) {
		super(ProviderProtocol.JCA);
		this.jcaUri = jcaUri;
	}

	/**
	 * @return the jcaUri
	 */
	public final String getJcaUri() {
		return jcaUri;
	}

}
