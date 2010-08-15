package com.tomecode.soa.openesb.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.openesb.workspace.OpenEsbWorkspace;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;

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
	}

	public final void addBpelProcess(OpenEsbBpelProcess bpelProcess) {
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

	public final OpenEsbWorkspace getWorkspace() {
		return workspace;
	}

	public final String toString() {
		return getName();
	}
}
