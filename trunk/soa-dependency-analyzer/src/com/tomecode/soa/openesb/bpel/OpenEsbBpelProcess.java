package com.tomecode.soa.openesb.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.services.BpelProcess;

/**
 * 
 * Open ESB - BPEL project
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public final class OpenEsbBpelProcess implements BpelProcess {

	/**
	 * BPEL process file
	 */
	private File bpenProcessFile;

	/**
	 * BPEL process name
	 */
	private String name;

	private final List<Import> imports;

	private final List<PartnerLink> partnerLinks;

	private OpenEsbBpelProcessStructure processStructure;

	/**
	 * Constructor
	 * 
	 * @param bpelProcessFile
	 *            BPEL process file
	 * @param name
	 *            BPEL process name
	 */
	public OpenEsbBpelProcess(File bpelProcessFile, String name) {
		this.bpenProcessFile = bpelProcessFile;
		this.name = name;
		this.imports = new ArrayList<Import>();
		this.partnerLinks = new ArrayList<PartnerLink>();
	}

	/**
	 * @return the bpenProcessFile
	 */
	public final File getBpenProcessFile() {
		return bpenProcessFile;
	}

	/**
	 * @param bpenProcessFile
	 *            the bpenProcessFile to set
	 */
	public final void setBpenProcessFile(File bpenProcessFile) {
		this.bpenProcessFile = bpenProcessFile;
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

	public final String toString() {
		return name;
	}

}
