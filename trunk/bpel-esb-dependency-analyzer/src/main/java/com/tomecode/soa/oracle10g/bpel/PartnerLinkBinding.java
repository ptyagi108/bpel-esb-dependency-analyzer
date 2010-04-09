package com.tomecode.soa.oracle10g.bpel;


/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding {

	private String name;
	private String wsdlLocation;

	private Bpel bpelProcess;

	private Bpel parent;

	private Exception parseError;

	// zoznam operacii

	public PartnerLinkBinding(String name, String wsdlLocation) {
		this.name = name;
		this.wsdlLocation = wsdlLocation;
	}

	public final String getName() {
		return name;
	}

	public final String getWsdlLocation() {
		return wsdlLocation;
	}

	public final Bpel getBpelProcess() {
		return bpelProcess;
	}

	public final void setBpelProcess(Bpel bpelProcess) {
		this.bpelProcess = bpelProcess;
		this.parseError  = null;
	}

	public void setParent(Bpel parent) {
		this.parent = parent;
	}

	public final Bpel getParent() {
		return parent;
	}

	public final Exception getParseErrror() {
		return parseError;
	}

	public final void setParseErrror(Exception parseErrror) {
		this.parseError = parseErrror;
	}

}
