package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.List;

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

	protected final List<String> uri = new ArrayList<String>();

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

	public final void putAllURI(List<String> uris) {
		this.uri.addAll(uris);
	}

	/**
	 * provider protocols
	 * 
	 * @author Tomas Frastia
	 * 
	 */
	public enum ProviderProtocol {
		/**
		 * EJB transport
		 */
		EJB, HTTP, JCA, JMS, LOCAL, SB, WS,
		/**
		 * file transport
		 */
		FILE, DSP, FTP, JPD, MAIL, MQ, SFTP, BPEL_10G;
	}
}
