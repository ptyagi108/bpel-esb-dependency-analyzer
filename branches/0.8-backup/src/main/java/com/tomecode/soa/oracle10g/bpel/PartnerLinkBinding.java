package com.tomecode.soa.oracle10g.bpel;

import java.io.Serializable;

import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.UnknownProject;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding implements Serializable {

	private static final long serialVersionUID = -2868489731373353648L;
	/**
	 * partner link name
	 */
	private String name;
	/**
	 * wsdl file location
	 */
	private String wsdlLocation;

	/**
	 * dependency esb project
	 */
	private EsbProject dependencyEsbProject;
	/**
	 * dependency bpel project
	 */
	private BpelProject dependencyBpelProject;

	private UnknownProject unknownProject;

	private BpelProject parent;

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
		if (dependencyEsbProject != null) {
			unknownProject = null;
		}
	}

	public final void setDependencyBpelProject(BpelProject dependencyBpelProject) {
		this.dependencyBpelProject = dependencyBpelProject;
		if (dependencyBpelProject != null) {
			unknownProject = null;
		}
	}

	public void setParent(BpelProject parent) {
		this.parent = parent;
	}

	public final BpelProject getParent() {
		return parent;
	}

	public final String toString() {
		return name;
	}

	public final UnknownProject getUnknownProject() {
		return unknownProject;
	}

	public final void setUnknownProject(UnknownProject unknownProject) {
		this.unknownProject = unknownProject;
	}
}
