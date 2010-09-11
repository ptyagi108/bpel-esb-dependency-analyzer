package com.tomecode.soa.openesb.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * Open ESB - BPEL project
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbBpelProject implements Project {

	private static final long serialVersionUID = 2921064536043532535L;

	private String name;

	private File file;

	/**
	 * list of BPEL or ESB process
	 */
	private final List<OpenEsbBpelProcess> bpelProcesses;

	/**
	 * list of WSDL files in BPEL process
	 */
	private final List<Wsdl> wsdlFiles;

	private OpenEsbWorkspace workspace;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            BPEL process name
	 * @param file
	 *            BPEL process file
	 */
	public OpenEsbBpelProject(String name, File file) {
		this.name = name;
		this.file = file;
		this.bpelProcesses = new ArrayList<OpenEsbBpelProcess>();
		this.wsdlFiles = new ArrayList<Wsdl>();
	}

	/**
	 * add {@link OpenEsbBpelProcess} to list of process
	 * 
	 * @param bpelProcess
	 */
	public final void addBpelProcess(OpenEsbBpelProcess bpelProcess) {
		bpelProcess.setProject(this);
		this.bpelProcesses.add(bpelProcess);

	}

	public final List<OpenEsbBpelProcess> getProcesses() {
		return bpelProcesses;
	}

	@Override
	public final File getFile() {
		return file;
	}

	public final String getName() {
		return name;
	}

	@Override
	public final ProjectType getType() {
		return ProjectType.OPEN_ESB_BPEL;
	}

	public final void setWorkspace(OpenEsbWorkspace workspace) {
		this.workspace = workspace;
	}

	public final String toString() {
		return getName();
	}

	@Override
	public final Workspace getWorkpsace() {
		return workspace;
	}

	public final void addWsdlFile(Wsdl wsdl) {
		this.wsdlFiles.add(wsdl);
	}

	/**
	 * @return the wsdlFiles
	 */
	public final List<Wsdl> getWsdlFiles() {
		return wsdlFiles;
	}

	public final Wsdl findWsdlByPartnerLinkType(String partnerLinkType) {
		for (Wsdl wsdl : wsdlFiles) {
			if (wsdl.containsPartnerLinkType(partnerLinkType)) {
				return wsdl;
			}
		}
		return null;
	}

	@Override
	public Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_BPEL_MODULE;
	}

}
