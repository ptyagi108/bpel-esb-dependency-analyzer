package com.tomecode.soa.ora.osb10g.services.config;

/**
 * element: provider-specific for DSP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProviderSpecificDsp {

	private boolean requestResponse;

	public ProviderSpecificDsp() {
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
