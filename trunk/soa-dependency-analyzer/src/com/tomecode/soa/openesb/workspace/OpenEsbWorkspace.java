package com.tomecode.soa.openesb.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.Workspace;

/**
 * this object contains all projects(BPEL, ESB) in workspace
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbWorkspace implements Workspace {

	private static final long serialVersionUID = -1120117507374608673L;

	/**
	 * workspace name
	 */
	private String name;

	/**
	 * workspace folder
	 */
	private File file;

	/**
	 * list of Open ESB {@link Project}
	 */
	private List<Project> projects;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param folder
	 *            workspace folder
	 */
	public OpenEsbWorkspace(String name, File folder) {
		this.projects = new ArrayList<Project>();
		this.name = name;
		this.file = folder;
	}

	@Override
	public final String getName() {
		return name;
	}

	public final File getFile() {
		return file;
	}

	public final List<Project> getProjects() {
		return projects;
	}

	public final void addProject(OpenEsbBpelProject project) {
		projects.add(project);
	}

	public final String toString() {
		return name;
	}

}
