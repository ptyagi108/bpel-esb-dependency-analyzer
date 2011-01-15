package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - SB - The SB transport allows Oracle products to
 * synchronously invoke an Oracle Service Bus proxy service using RMI.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointSB extends EndpointConfig {

	public EndpointSB() {
		super(ProviderProtocol.SB);
	}

}
