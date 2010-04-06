package com.tomecode.soa.bpel.model;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding {

	private String name;
	private String wsdlLocation;

	private BpelProcess bpelProcess;

	private BpelProcess parent;
	
	//zoznam operacii

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

	public final BpelProcess getBpelProcess() {
		return bpelProcess;
	}

	public final void setBpelProcess(BpelProcess bpelProcess) {
		this.bpelProcess = bpelProcess;
	}

	public void setParent(BpelProcess parent) {
		this.parent = parent;
	}

	public final BpelProcess getParent() {
		return parent;
	}

}
