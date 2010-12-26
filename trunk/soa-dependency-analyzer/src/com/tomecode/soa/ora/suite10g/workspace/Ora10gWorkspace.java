package com.tomecode.soa.ora.suite10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle 10g SOA SUITE workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora10gWorkspace implements Workspace {

	/**
	 * workspace name
	 */
	private String name;

	/**
	 * workspace file
	 */
	private File file;

	/**
	 * list of workspace projects
	 */
	private final List<Project> projects;

	private Ora10gMultiWorkspace multiWorkspace;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace file (.jws)
	 */
	public Ora10gWorkspace(String name, File file) {
		this.projects = new ArrayList<Project>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final File getFile() {
		return this.file;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	public final Object[] getChildren() {
		return projects.toArray();
	}

	public final boolean hasChildren() {
		return !projects.isEmpty();
	}

	public final List<Project> getProjects() {
		return projects;
	}

	/**
	 * add new {@link Project} and set parent
	 * 
	 * @param project
	 */
	public final void addProject(Project project) {
		projects.add(project);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.ORACLE_1OG;
	}

	public final void setMultiWorkspace(Ora10gMultiWorkspace multiWorkspace) {
		this.multiWorkspace = multiWorkspace;
	}

	@Override
	public MultiWorkspace getMultiWorkspace() {
		return multiWorkspace;
	}

	public final Image getImage() {
		return ImageFactory.WORKSPACE;
	}

	@Override
	public final String getToolTip() {
		return "Workspace: Oracle SOA Suite 10g\nName: " + name + "\nPath:" + (file != null ? file.getPath() : "");
	}
}
