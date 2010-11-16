package com.tomecode.soa.ora.osb10g.services.config;

/**
 * Endpoint protocol - DSP
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EndpointDsp extends EndpointConfig {

	private ProviderSpecificDsp providerSpecificDsp;

	public EndpointDsp() {
		super(ProviderProtocol.DSP);
	}

	/**
	 * @return the providerSpecificDsp
	 */
	public final ProviderSpecificDsp getProviderSpecificDsp() {
		return providerSpecificDsp;
	}

	/**
	 * @param providerSpecificDsp
	 *            the providerSpecificDsp to set
	 */
	public final void setProviderSpecificDsp(ProviderSpecificDsp providerSpecificDsp) {
		this.providerSpecificDsp = providerSpecificDsp;
	}

}
