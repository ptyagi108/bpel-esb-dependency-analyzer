package com.tomecode.soa.oracle10g.bpel;

import com.tomecode.soa.process.Project;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding {

	private String name;
	private String wsdlLocation;

	private Project dependencyProject;

	private BpelProject parent;

	private Exception parseError;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            partner link name
	 * @param wsdlLocation
	 *            wsdl url
	 */
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

	public final Project getDependencyProject() {
		return dependencyProject;
	}

	public final void setDependencyProject(Project project) {
		this.dependencyProject = project;
		this.parseError = null;
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
