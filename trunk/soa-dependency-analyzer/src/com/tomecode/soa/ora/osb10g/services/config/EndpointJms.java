package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - JMS
 * 
 * @author Tomas Frastia
 * 
 */
public final class EndpointJms extends EndpointConfig {

	private String jmsUri;

	public EndpointJms(String jmsUri) {
		super(ProviderProtocol.JMS);
		this.jmsUri = jmsUri;
	}

	/**
	 * @return the jmsUri
	 */
	public final String getJmsUri() {
		return jmsUri;
	}

}
