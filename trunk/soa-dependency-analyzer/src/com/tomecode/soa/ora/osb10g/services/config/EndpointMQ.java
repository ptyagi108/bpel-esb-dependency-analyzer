package com.tomecode.soa.ora.osb10g.services.config;

/**
 * 
 * * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Endpoint protocol - DSP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@SuppressWarnings("rawtypes")
public final class EndpointMQ extends EndpointConfig {

	public EndpointMQ() {
		super(ProviderProtocol.MQ);
	}
}
