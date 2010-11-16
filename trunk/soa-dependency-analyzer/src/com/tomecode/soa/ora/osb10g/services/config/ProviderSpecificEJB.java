package com.tomecode.soa.ora.osb10g.services.config;

/**
 * element: provider-specific for EJB
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class ProviderSpecificEJB {

	private String clientJar;

	private String ejbHome;

	private String ejbObject;

	public ProviderSpecificEJB() {
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

	/**
	 * @return the ejbHome
	 */
	public final String getEjbHome() {
		return ejbHome;
	}

	/**
	 * @param ejbHome
	 *            the ejbHome to set
	 */
	public final void setEjbHome(String ejbHome) {
		this.ejbHome = ejbHome;
	}

	/**
	 * @return the ejbObject
	 */
	public final String getEjbObject() {
		return ejbObject;
	}

	/**
	 * @param ejbObject
	 *            the ejbObject to set
	 */
	public final void setEjbObject(String ejbObject) {
		this.ejbObject = ejbObject;
	}

}
