package com.tomecode.soa.openesb.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * 
 * 
 * Multiple workspace contains all workspaces and projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
		workspace.setMultiWorkspace(this);
		workspaces.add(workspace);
	}

	public final String toString() {
		return name;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.OPEN_ESB;
	}

	public final OpenEsbWorkspace removeWorkspace(Workspace workspace) {
		for (int i = 0; i <= workspaces.size() - 1; i++) {
			if (workspaces.get(i).equals(workspace)) {
				return workspaces.remove(i);
			}
		}
		return null;
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		for (OpenEsbWorkspace openEsbWorkspace : workspaces) {
			if (openEsbWorkspace.equals(workspace)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.MULTI_WORKSPACE;
	}
}
