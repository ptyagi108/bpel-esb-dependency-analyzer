package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.protocols.http.HttpServer;

/**
 * Endpoint protocol - HTTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointHttp extends EndpointConfig<HttpServer> {

	private ProviderSpecificHttp providerSpecificHttp;

	/**
	 * list of {@link HttpServer}
	 */
	// private final List<HttpServer> httpServers;

	public EndpointHttp() {
		super(ProviderProtocol.HTTP);
		// this.httpServers = new ArrayList<HttpServer>();
	}

	/**
	 * @return the providerSpecificHttp
	 */
	public final ProviderSpecificHttp getProviderSpecificHttp() {
		return providerSpecificHttp;
	}

	/**
	 * @param providerSpecificHttp
	 *            the providerSpecificHttp to set
	 */
	public final void setProviderSpecificHttp(ProviderSpecificHttp providerSpecificHttp) {
		this.providerSpecificHttp = providerSpecificHttp;
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseHttpServersUris(uris, nodes);
	}

	// public final List<HttpServer> getHttpServers() {
	// return httpServers;
	// }

}
