package com.tomecode.soa.openesb.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.project.OpenEsbBpelProject;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * this object contains all projects(BPEL, ESB) in workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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

	private OpenEsbMultiWorkspace multiWorkspace;

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

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.OPEN_ESB;
	}

	public final void setMultiWorkspace(OpenEsbMultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	@Override
	public final MultiWorkspace getMultiWorkspace() {
		return multiWorkspace;
	}

	public final Image getImage() {
		return ImageFactory.WORKSPACE;
	}

	@Override
	public final String getToolTip() {
		return "Workspace: Open Esb\nName: " + name + "\nPath:" + (file != null ? file.getPath() : "");
	}

}
