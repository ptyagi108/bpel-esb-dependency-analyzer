package com.tomecode.soa.ora.osb10g.services.config;

import java.util.List;

import com.tomecode.soa.protocols.ejb.EjbProvider;

/**
 * Endpoint protocol - EJB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointEJB extends EndpointConfig {

	private ProviderSpecificEJB providerSpecificEJB;

	private EjbProvider ejbProvider;

	public EndpointEJB() {
		super(ProviderProtocol.EJB);
	}

	/**
	 * @return the providerSpecificEJB
	 */
	public final ProviderSpecificEJB getProviderSpecificEJB() {
		return providerSpecificEJB;
	}

	/**
	 * @param providerSpecificEJB
	 *            the providerSpecificEJB to set
	 */
	public final void setProviderSpecificEJB(ProviderSpecificEJB providerSpecificEJB) {
		this.providerSpecificEJB = providerSpecificEJB;
	}

	public void putAllURI(List<String> uris) {
		this.uris.addAll(uris);
	}

	public final EjbProvider getEjbProvider() {
		return ejbProvider;
	}

	public final void setEjbProvider(EjbProvider ejbProvider) {
		this.ejbProvider = ejbProvider;
	}

}
