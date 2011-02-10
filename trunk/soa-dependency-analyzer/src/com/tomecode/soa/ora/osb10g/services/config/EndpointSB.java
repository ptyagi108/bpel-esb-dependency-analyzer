package com.tomecode.soa.ora.osb10g.services.config;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - SB - The SB transport allows Oracle products to
 * synchronously invoke an Oracle Service Bus proxy service using RMI.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@SuppressWarnings("rawtypes")
public final class EndpointSB extends EndpointConfig {

	public EndpointSB() {
		super(ProviderProtocol.SB);
	}

}
