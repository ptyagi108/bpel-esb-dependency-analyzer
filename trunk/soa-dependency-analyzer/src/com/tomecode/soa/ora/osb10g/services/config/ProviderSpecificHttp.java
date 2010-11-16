package com.tomecode.soa.ora.osb10g.services.config;

/**
 * element: provider-specific for HTTP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProviderSpecificHttp {

	private String requestMethod;

	public ProviderSpecificHttp() {
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
