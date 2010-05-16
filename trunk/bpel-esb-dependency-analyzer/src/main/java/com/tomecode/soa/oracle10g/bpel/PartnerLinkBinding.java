package com.tomecode.soa.oracle10g.bpel;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.DependencyNode;
import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.EsbServiceNode;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

/**
 * Contains data for partnerlink
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLinkBinding {

	private String name;
	private String wsdlLocation;

	private EsbServiceNode dependencyEsbProject;

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

	public final DependencyNode getDependencyEsbProject() {
		return dependencyEsbProject;
	}

	public final BpelProject getDependencyBpelProject() {
		return dependencyBpelProject;
	}

	public final void setDependencyProject(Project project) {
		if (project != null) {
			if (project.getType() == ProjectType.ORACLE10G_ESB) {
				this.dependencyEsbProject = new EsbServiceNode((EsbProject) project);
			} else {
				this.dependencyBpelProject = (BpelProject) project;
			}
			this.parseError = null;
		}
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
