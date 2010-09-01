package com.tomecode.soa.ora.osb10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.ora.osb10g.project.OraSB10gProject;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gWorkspace implements Workspace {

	private String name;
	private File file;

	private MultiWorkspace parent;

	private final List<OraSB10gProject> projects;

	public OraSB10gWorkspace(String name, File file) {
		this.projects = new ArrayList<OraSB10gProject>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final File getFile() {
		return file;
	}

	@Override
	public final MultiWorkspace getMultiWorkspace() {
		return parent;
	}

	public final void setMultiWorkspace(OraSB10gMultiWorkspace multiWorkspace) {
		this.parent = multiWorkspace;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.ORACLE_SERVICE_BUS_10G;
	}

	public final void addProject(OraSB10gProject project) {
		project.setWorkspace(this);
		this.projects.add(project);
	}

	public final List<OraSB10gProject> getProjects() {
		return this.projects;
	}

	public final String toString() {
		return name;
	}
}
