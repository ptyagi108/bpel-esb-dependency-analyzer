package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.protocols.ejb.EjbProvider;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - EJB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointEJB extends EndpointConfig<EjbProvider> {

	@PropertyViewData(title = "Endpoint Client JAR: ")
	private String clientJar;

	public EndpointEJB() {
		super(ProviderProtocol.EJB);
	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
	}

	/**
	 * @return the clientJar
	 */
	public final String getClientJar() {
		return clientJar;
	}

	/**
	 * @param clientJar
	 *            the clientJar to set
	 */
	public final void setClientJar(String clientJar) {
		this.clientJar = clientJar;
	}

	public final void addEjbProvider(EjbProvider ejbProvider) {
		if (!existsChild(ejbProvider)) {
			nodes.add(ejbProvider);
		}
	}

}
