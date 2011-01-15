package com.tomecode.soa.ora.suite10g.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.activity.PartnerLink;
import com.tomecode.soa.ora.suite10g.parser.Ora10gBpelParser;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 10g BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora10gBpelProject implements Project {

	private boolean folder;
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

	/**
	 * project folder
	 */
	private File file;

	/**
	 * bpel project WSDL
	 */
	private Wsdl wsdl;

	private final BpelOperations bpelOperations;

	/**
	 * bpel process structure
	 */
	private final Ora10gBpelProcessStrukture bpelProcessStrukture;
	/**
	 * list of {@link PartnerLink}
	 */
	private final List<PartnerLinkBinding> partnerLinkBindings;
	/**
	 * list of dependency projects
	 */
	private final List<Project> dependencyProjects;

	/**
	 * parent workspace
	 */
	private Ora10gWorkspace workspace;

	/**
	 * Constructor
	 */
	private Ora10gBpelProject(boolean folder) {
		this.partnerLinkBindings = new ArrayList<PartnerLinkBinding>();
		this.dependencyProjects = new ArrayList<Project>();
		this.bpelOperations = new BpelOperations(this);
		this.bpelProcessStrukture = new Ora10gBpelProcessStrukture(this);
		this.folder = folder;
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param src
	 * @param bpelXmlFile
	 * @param file
	 * @param folder
	 */
	public Ora10gBpelProject(String id, String src, File bpelXmlFile, File file, boolean folder) {
		this(folder);
		this.id = id;
		this.src = src;
		this.bpelXmlFile = bpelXmlFile;
		this.file = file;
	}

	/**
	 * Constructor
	 * 
	 * @param file
	 *            project folder
	 */
	public Ora10gBpelProject(File file, boolean folder) {
		this(folder);
		this.file = file;
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
					dependencyProjects.add(partnerLinkBinding.getDependencyEsbProject().getEsbProject());
				}
			} else {
				dependencyProjects.add(Ora10gBpelParser.parseProjectFromWsdl(partnerLinkBinding));
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
	public final String getName() {
		return getId();
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.ORACLE_10G_BPEL_PROCESS;
	}

	@Override
	public final String getToolTip() {
		return "Oracle SOA Suite 10g - BPEL Project\nName: " + getName() + "\nFile: " + (file != null ? file.getPath() : "");
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	public final void setBpelXmlFile(File bpelXmlFile) {
		this.bpelXmlFile = bpelXmlFile;
	}

	@Override
	public final boolean isFolder() {
		return folder;
	}

}
