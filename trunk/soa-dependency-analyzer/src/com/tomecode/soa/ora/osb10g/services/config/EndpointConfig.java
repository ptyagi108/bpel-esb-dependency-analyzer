package com.tomecode.soa.ora.osb10g.services.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.ora.osb10g.services.Proxy;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint config for OSB 10g {@link Proxy} or {@link BusinessService}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class EndpointConfig<T> {

	@PropertyViewData(title = "Endpoint Protocol")
	protected ProviderProtocol protocol;

	@PropertyViewData(title = "Endpoint is Inbound")
	private boolean inbound;
	/**
	 * list of uris
	 */
	@PropertyViewData(title = "URIs")
	protected final List<String> uris;

	protected final List<T> nodes;

	public EndpointConfig() {
		this.uris = new ArrayList<String>();
		this.nodes = new ArrayList<T>();
	}

	/**
	 * 
	 * Constructor
	 * 
	 * @param providerProtocol
	 */
	public EndpointConfig(ProviderProtocol providerProtocol) {
		this();
		this.protocol = providerProtocol;
	}

	/**
	 * @return the protocol
	 */
	public final ProviderProtocol getProtocol() {
		return protocol;
	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
	}

	public final List<String> getUris() {
		return uris;
	}

	public final boolean isInbound() {
		return inbound;
	}

	public final void setInbound(boolean inbound) {
		this.inbound = inbound;
	}

	public final String makeuris() {
		String uris = "";
		Iterator<String> i = getUris().iterator();
		while (i.hasNext()) {
			uris += i.next();
			if (i.hasNext()) {
				uris += "\n";
			}
		}

		return uris;
	}

	protected final boolean existsChild(Object obj) {
		for (T node : nodes) {
			if (obj.equals(node)) {
				return true;
			}
		}
		return false;
	}

	public final List<T> getNodes() {
		return nodes;
	}

	/**
	 * provider protocols
	 * 
	 * @author Tomas Frastia
	 * @see http://www.tomecode.com
	 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
		FILE, DSP, FTP, JPD, MAIL, MQ, SFTP, BPEL_10G, UNKNOWN;
	}
}
