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

	private BpelProject bpelProcess;

	private BpelProject parent;

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

	public final BpelProject getBpelProcess() {
		return bpelProcess;
	}

	public final void setBpelProcess(BpelProject bpelProcess) {
		this.bpelProcess = bpelProcess;
		this.parseError  = null;
	}

	public void setParent(BpelProject parent) {
		this.parent = parent;
	}

	public final BpelProject getParent() {
		return parent;
	}

	public final Exception getParseErrror() {
		return parseError;
	}

	public final void setParseErrror(Exception parseErrror) {
		this.parseError = parseErrror;
	}

}
