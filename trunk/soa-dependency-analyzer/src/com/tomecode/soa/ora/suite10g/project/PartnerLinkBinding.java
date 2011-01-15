package com.tomecode.soa.ora.suite10g.project;

import java.io.Serializable;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.UnknownProject;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class PartnerLinkBinding implements ImageFace, Serializable {

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
	private final BpelEsbDependency[] dependencyEsbProject;
	/**
	 * dependency BPEL project
	 */
	private final Ora10gBpelProject[] dependencyBpelProject;

	private final UnknownProject[] unknownProject;

	private Ora10gBpelProject parent;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            partner link name
	 * @param wsdlLocation
	 *            WSDL URL
	 */
	public PartnerLinkBinding(String name, String wsdlLocation) {
		dependencyBpelProject = new Ora10gBpelProject[1];
		dependencyEsbProject = new BpelEsbDependency[1];
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

	public final BpelEsbDependency getDependencyEsbProject() {
		return dependencyEsbProject[0];
	}

	public final Ora10gBpelProject getDependencyBpelProject() {
		return dependencyBpelProject[0];
	}

	public final void setDependencyEsbProject(BpelEsbDependency dependencyEsbProject) {
		this.dependencyEsbProject[0] = dependencyEsbProject;
		if (this.dependencyEsbProject[0] != null) {
			unknownProject[0] = null;
		}
	}

	public final void setDependencyBpelProject(Ora10gBpelProject dependencyBpelProject) {
		this.dependencyBpelProject[0] = dependencyBpelProject;
		if (this.dependencyBpelProject[0] != null) {
			unknownProject[0] = null;
		}
	}

	public void setParent(Ora10gBpelProject parent) {
		this.parent = parent;
	}

	public final Ora10gBpelProject getParent() {
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
			return dependencyEsbProject[0].getEsbProjectAsList();
		} else if (unknownProject[0] != null) {
			return unknownProject;
		}

		return null;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_PARTNERLINK;
	}

	@Override
	public final String getToolTip() {
		return "PartnerLink Name: " + name + "\nWSDL: " + (wsdlLocation != null ? wsdlLocation : "");
	}
}
