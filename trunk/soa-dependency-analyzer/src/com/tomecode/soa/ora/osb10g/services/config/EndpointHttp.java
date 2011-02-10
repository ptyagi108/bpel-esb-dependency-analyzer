package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.ora.osb10g.parser.OraSB10gBasicServiceParser;
import com.tomecode.soa.protocols.http.HttpServer;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - HTTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointHttp extends EndpointConfig<HttpServer> {

	@PropertyViewData(title = "Endpoint HTTP Request Method")
	private String requestMethod;

	public EndpointHttp() {
		super(ProviderProtocol.HTTP);
	}

	public final void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
		OraSB10gBasicServiceParser.parseHttpServersUris(uris, nodes);
	}

	/**
	 * @return the requestMethod
	 */
	public final String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod
	 *            the requestMethod to set
	 */
	public final void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

}
