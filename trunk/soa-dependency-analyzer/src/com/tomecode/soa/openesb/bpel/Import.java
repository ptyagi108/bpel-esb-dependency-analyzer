package com.tomecode.soa.openesb.bpel;

/**
 * 
 * Import element in BPEL XML in Open ESB BPEL process
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class Import {

	/**
	 * imported WSDL
	 */
	private String wsdl;

	public Import(String wsdl) {

		this.wsdl = wsdl;
	}

	/**
	 * @return the WSDL
	 */
	public final String getWsdl() {
		return wsdl;
	}

	/**
	 * @param wsdl
	 *            the WSDL to set
	 */
	public final void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

}
