package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.jms.JMSServer;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;

/**
 * Endpoint protocol - JMS
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointJms extends EndpointConfig {

	private ProviderSpecificJms providerSpecificJms;

	private final List<JMSServer> jmsServers;

	public EndpointJms() {
		super(ProviderProtocol.JMS);
		this.jmsServers = new ArrayList<JMSServer>();
	}

	/**
	 * @return the providerSpecificJms
	 */
	public final ProviderSpecificJms getProviderSpecificJms() {
		return providerSpecificJms;
	}

	/**
	 * @param providerSpecificJms
	 *            the providerSpecificJms to set
	 */
	public final void setProviderSpecificJms(ProviderSpecificJms providerSpecificJms) {
		this.providerSpecificJms = providerSpecificJms;
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseJMSServerUris(uris, jmsServers);
	}

	public final List<JMSServer> getJmsServers() {
		return jmsServers;
	}

}
