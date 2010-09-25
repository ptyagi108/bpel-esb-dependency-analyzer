package com.tomecode.soa.ora.suite10g.project;

import java.io.Serializable;

import com.tomecode.soa.ora.suite10g.esb.EsbProject;
import com.tomecode.soa.project.UnknownProject;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class PartnerLinkBinding implements Serializable {

	private static final long serialVersionUID = -2868489731373353648L;
	/**
	 * partner link name
	 */
	private String name;
	/**
	 * WSDL file location
	 */
	private String wsdlLocation;

	/**
	 * dependency ESB project
	 */
	private final EsbProject[] dependencyEsbProject;
	/**
	 * dependency BPEL project
	 */
	private final BpelProject[] dependencyBpelProject;

	private final UnknownProject[] unknownProject;

	private BpelProject parent;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            partner link name
	 * @param wsdlLocation
	 *            WSDL URL
	 */
	public PartnerLinkBinding(String name, String wsdlLocation) {
		dependencyBpelProject = new BpelProject[1];
		dependencyEsbProject = new EsbProject[1];
		unknownProject = new UnknownProject[1];
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
		return dependencyEsbProject[0];
	}

	public final BpelProject getDependencyBpelProject() {
		return dependencyBpelProject[0];
	}

	public final void setDependencyEsbProject(EsbProject dependencyEsbProject) {
		this.dependencyEsbProject[0] = dependencyEsbProject;
		if (this.dependencyEsbProject[0] != null) {
			unknownProject[0] = null;
		}
	}

	public final void setDependencyBpelProject(BpelProject dependencyBpelProject) {
		this.dependencyBpelProject[0] = dependencyBpelProject;
		if (this.dependencyBpelProject[0] != null) {
			unknownProject[0] = null;
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
		return unknownProject[0];
	}

	public final void setUnknownProject(UnknownProject unknownProject) {
		this.unknownProject[0] = unknownProject;
	}

	public final boolean hasChildren() {
		return (dependencyBpelProject[0] != null || dependencyEsbProject[0] != null || unknownProject[0] != null);
	}

	public final Object[] getChildren() {
		if (dependencyBpelProject[0] != null) {
			return dependencyBpelProject;// .getBpelOperations().getOperations().toArray();
		} else if (dependencyEsbProject[0] != null) {
			return dependencyEsbProject;
		} else if (unknownProject[0] != null) {
			return unknownProject;
		}

		return null;
	}
}
