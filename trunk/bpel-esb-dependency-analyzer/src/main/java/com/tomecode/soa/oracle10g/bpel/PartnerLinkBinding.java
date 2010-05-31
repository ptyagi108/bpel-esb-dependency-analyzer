package com.tomecode.soa.oracle10g.bpel;

import java.io.Serializable;

import com.tomecode.soa.oracle10g.esb.EsbProject;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding implements Serializable {

	private static final long serialVersionUID = -2868489731373353648L;

	private String name;
	private String wsdlLocation;

	/**
	 * dependency esb project
	 */
	private EsbProject dependencyEsbProject;
	/**
	 * dependency bpel project
	 */
	private BpelProject dependencyBpelProject;

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

	public final EsbProject getDependencyEsbProject() {
		return dependencyEsbProject;
	}

	public final BpelProject getDependencyBpelProject() {
		return dependencyBpelProject;
	}

	public final void setDependencyEsbProject(EsbProject dependencyEsbProject) {
		this.dependencyEsbProject = dependencyEsbProject;
	}

	public final void setDependencyBpelProject(BpelProject dependencyBpelProject) {
		this.dependencyBpelProject = dependencyBpelProject;
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
