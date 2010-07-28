package com.tomecode.soa.openesb.bpel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Open ESB - BPEL project
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public final class OpenEsbBpelProcess {

	private File bpenProcessFile;

	private String name;

	private final List<Import> imports;

	private final List<PartnerLink> partnerLinks;

	private OpenEsbBpelProcessStructure processStructure;

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
	 * @return the processStructure
	 */
	public final OpenEsbBpelProcessStructure getProcessStructure() {
		return processStructure;
	}

	public final void setProcessStructure(OpenEsbBpelProcessStructure processStructure) {
		this.processStructure = processStructure;
	}

}
