package com.tomecode.soa.ora.osb10g.services.config;

import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.ora.osb10g.services.Proxy;

/**
 * 
 * Endpoint config for OSB 10g {@link Proxy} or {@link BusinessService}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class EndpointConfig {

	protected final ProviderProtocol protocol;

	/**
	 * 
	 * Constructor
	 * 
	 * @param providerProtocol
	 */
	public EndpointConfig(ProviderProtocol providerProtocol) {
		this.protocol = providerProtocol;
	}

	/**
	 * @return the protocol
	 */
	public final ProviderProtocol getProtocol() {
		return protocol;
	}

	/**
	 * provider protocols
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public enum ProviderProtocol {
		HTTP, JCA, JMS, LOCAL, SB, WS;
	}
}
