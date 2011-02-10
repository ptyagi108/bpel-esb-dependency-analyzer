package com.tomecode.soa.ora.osb10g.services.config;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Endpoint protocol - DSP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@SuppressWarnings("rawtypes")
public final class EndpointDsp extends EndpointConfig {

	@PropertyViewData(title = "Endpoint Request-response: ")
	private boolean requestResponse;

	public EndpointDsp() {
		super(ProviderProtocol.DSP);
	}

	/**
	 * @return the requestResponse
	 */
	public final boolean isRequestResponse() {
		return requestResponse;
	}

	/**
	 * @param requestResponse
	 *            the requestResponse to set
	 */
	public final void setRequestResponse(boolean requestResponse) {
		this.requestResponse = requestResponse;
	}
}
