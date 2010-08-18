package com.tomecode.soa.openesb.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * 
 * Multiple workspace contains all workspaces and projects
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbMultiWorkspace implements MultiWorkspace {

	private static final long serialVersionUID = -5117584282066150277L;

	/**
	 * workspace name
	 */
	private String name;

	/**
	 * workspace folder
	 */
	private File file;

	/**
	 * list of {@link Workspace}
	 */
	private List<OpenEsbWorkspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param workspceFolder
	 *            workspace folder
	 */
	public OpenEsbMultiWorkspace(String name, File workspceFolder) {
		super();
		this.workspaces = new ArrayList<OpenEsbWorkspace>();
		this.name = name;
		this.file = workspceFolder;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final File getFile() {
		return file;
	}

	public final List<OpenEsbWorkspace> getWorkspaces() {
		return workspaces;
	}

	public final void addWorkspace(OpenEsbWorkspace workspace) {
		workspaces.add(workspace);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.OPEN_ESB;
	}
}
