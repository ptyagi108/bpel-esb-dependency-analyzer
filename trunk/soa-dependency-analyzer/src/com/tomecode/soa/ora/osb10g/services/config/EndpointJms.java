package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;
import com.tomecode.soa.protocols.Node;

/**
 * Endpoint protocol - JMS
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "Endpoint JMS", name = "Endpoint JMS")
public final class EndpointJms extends EndpointConfig<JMSServer> {

	private ProviderSpecificJms providerSpecificJms;

	public EndpointJms() {
		super(ProviderProtocol.JMS);
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
		OraSB10gBasicServiceParser.parseJMSServerUris(uris, nodes);
	}

	public final void setParentService(Service parentService) {
		for (Node<JMSServer> jmsServer : nodes) {
			jmsServer.getObj().setParentService(parentService);
		}
		if (providerSpecificJms != null) {
			for (Node<JMSServer> jmsServer : providerSpecificJms.getJmsServers()) {
				jmsServer.getObj().setParentService(parentService);
			}
		}
	}
}
