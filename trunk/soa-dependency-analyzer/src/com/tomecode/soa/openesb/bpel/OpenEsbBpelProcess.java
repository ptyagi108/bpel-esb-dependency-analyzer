package com.tomecode.soa.openesb.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.activity.PartnerLink;
import com.tomecode.soa.openesb.bpel.dependencies.BpelActivityDependency;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * Open ESB - BPEL project
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class OpenEsbBpelProcess implements BpelProcess {

	/**
	 * BPEL process file
	 */
	private File file;

	/**
	 * BPEL process name
	 */
	private String name;

	private final List<Import> imports;

	private final List<PartnerLink> partnerLinks;

	private OpenEsbBpelProcessStructure processStructure;

	private OpenEsbBpelProject project;

	// private final ProcessDependencies processDependencies;

	private final List<BpelActivityDependency> activityDependencies;

	/**
	 * Constructor
	 * 
	 * @param bpelProcessFile
	 *            BPEL process file
	 * @param name
	 *            BPEL process name
	 */
	public OpenEsbBpelProcess(File bpelProcessFile, String name) {
		this.file = bpelProcessFile;
		this.name = name;
		this.imports = new ArrayList<Import>();
		this.partnerLinks = new ArrayList<PartnerLink>();
		// this.processDependencies = new ProcessDependencies(this);
		this.activityDependencies = new ArrayList<BpelActivityDependency>();
	}

	/**
	 * @param bpenProcessFile
	 *            the bpenProcessFile to set
	 */
	public final void setBpenProcessFile(File bpenProcessFile) {
		this.file = bpenProcessFile;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	public final void addImport(Import importt) {
		imports.add(importt);
	}

	/**
	 * @return the imports
	 */
	public final List<Import> getImports() {
		return imports;
	}

	public final void addPartnerLinks(PartnerLink partnerLink) {
		this.partnerLinks.add(partnerLink);
	}

	/**
	 * @return the partnerLinks
	 */
	public final List<PartnerLink> getPartnerLinks() {
		return partnerLinks;
	}

	/**
	 * @return the processStructure
	 */
	public final OpenEsbBpelProcessStructure getProcessStructure() {
		return processStructure;
	}

	public final void setProcessStructure(OpenEsbBpelProcessStructure processStructure) {
		this.processStructure = processStructure;
	}

	public final Object[] getProcessStructureChildren() {
		return processStructure.getActivities().toArray();
	}

	/**
	 * 
	 * @return
	 */
	public final boolean hasProcessStructureChildren() {
		return processStructure != null && !processStructure.getActivities().isEmpty();
	}

	/**
	 * @return the project
	 */
	public final OpenEsbBpelProject getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public final void setProject(OpenEsbBpelProject project) {
		this.project = project;
	}

	public final String toString() {
		return name;
	}

	// /**
	// * @return the processDependencies
	// */
	// public final ProcessDependencies getProcessDependencies() {
	// return processDependencies;
	// }

	/**
	 * @return the bpelActivityDependency
	 */
	public final List<BpelActivityDependency> getActivityDependencies() {
		return activityDependencies;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_PROCESS;
	}

	public final void addActivityDependency(Activity activity, String partnerLink) {
		this.activityDependencies.add(new BpelActivityDependency(activity, partnerLink));
	}

	public final PartnerLink findPartnerLink(String partnerLink) {
		for (PartnerLink link : partnerLinks) {
			if (link.getName().equals(partnerLink)) {
				return link;
			}
		}
		return null;
	}

	@Override
	public final File getFile() {
		return file;
	}
}
