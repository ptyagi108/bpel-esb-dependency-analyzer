package com.tomecode.soa.ora.osb10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gMultiWorkspace implements MultiWorkspace {

	private String name;

	private File file;

	private final List<OraSB10gWorkspace> workspaces;

	public OraSB10gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<OraSB10gWorkspace>();
		this.name = name;
		this.file = file;
	}

	public final List<OraSB10gWorkspace> getWorkspaces() {
		return workspaces;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public WorkspaceType getType() {
		return WorkspaceType.ORACLE_SERVICE_BUS_10G;
	}

	public final void addWorkspace(OraSB10gWorkspace workspace) {
		workspace.setMultiWorkspace(this);
		this.workspaces.add(workspace);
	}

	public final String toString() {
		return name;
	}

}
