package com.tomecode.soa.oracle10g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.oracle10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * Oracle 10g BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProject implements Project {

	/**
	 * if true then project is in *.jws
	 */
	private boolean isInJws;

	private String id;

	private String src;
	/**
	 * *.bpel file
	 */
	private File bpelXmlFile;

	private File file;

	private Wsdl wsdl;

	private final BpelOperations bpelOperations;

	private final Ora10gBpelProcessStrukture bpelProcessStrukture;

	private final List<PartnerLinkBinding> partnerLinkBindings;
	private final List<Project> dependencyProjects;

	private Ora10gWorkspace workspace;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param src
	 * @param bpelXmlFile
	 */
	public BpelProject(String id, String src, File bpelXmlFile) {
		this.partnerLinkBindings = new ArrayList<PartnerLinkBinding>();
		this.dependencyProjects = new ArrayList<Project>();
		this.bpelOperations = new BpelOperations(this);
		this.bpelProcessStrukture = new Ora10gBpelProcessStrukture(this);
		this.id = id;
		this.src = src;
		this.bpelXmlFile = bpelXmlFile;
	}

	@Override
	public File getFile() {
		return file;
	}

	public final String getId() {
		return id;
	}

	public final String getSrc() {
		return src;
	}

	public final File getBpelXmlFile() {
		return bpelXmlFile;
	}

	public final boolean isInJws() {
		return isInJws;
	}

	public final void setInJws(boolean isInJws) {
		this.isInJws = isInJws;
	}

	public final Wsdl getWsdl() {
		return wsdl;
	}

	public final void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}

	@Override
	public final ProjectType getType() {
		return ProjectType.ORACLE10G_BPEL;
	}

	public final BpelOperations getBpelOperations() {
		return bpelOperations;
	}

	public final Ora10gBpelProcessStrukture getBpelProcessStrukture() {
		return bpelProcessStrukture;
	}

	public final List<Project> getDependencyProjects() {
		return dependencyProjects;
	}

	public final void addPartnerLinkBinding(PartnerLinkBinding partnerLink) {
		partnerLink.setParent(this);
		partnerLinkBindings.add(partnerLink);
	}

	public final PartnerLinkBinding findPartnerLinkBinding(String partnerLinkName) {
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			if (partnerLinkBinding.getName().equals(partnerLinkName)) {
				return partnerLinkBinding;
			}
		}
		return null;
	}

	public final List<PartnerLinkBinding> getPartnerLinkBindings() {
		return partnerLinkBindings;
	}

	/**
	 * analysis of {@link Project} dependencies
	 */
	public final void analysisProjectDependencies() {
		dependencyProjects.clear();
		for (PartnerLinkBinding partnerLinkBinding : partnerLinkBindings) {
			if (partnerLinkBinding.getDependencyBpelProject() != null) {
				if (!dependencyProjects.contains(partnerLinkBinding.getDependencyBpelProject())) {
					dependencyProjects.add(partnerLinkBinding.getDependencyBpelProject());
				}
			} else if (partnerLinkBinding.getDependencyEsbProject() != null) {
				if (!dependencyProjects.contains(partnerLinkBinding.getDependencyEsbProject())) {
					dependencyProjects.add(partnerLinkBinding.getDependencyEsbProject());
				}
			} else {
				dependencyProjects.add(new UnknownProject(partnerLinkBinding));
			}
		}

	}

	public final void setWorkspace(Ora10gWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		return getId();
	}

	@Override
	public String getName() {
		return getId();
	}

	@Override
	public final Workspace getWorkspace() {
		return workspace;
	}

}
