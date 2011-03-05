package com.tomecode.soa.openesb.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Multiple workspace contains all workspaces and projects
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "Multi-Workspace...", name = "Open ESB")
public final class OpenEsbMultiWorkspace implements MultiWorkspace {

	private static final long serialVersionUID = -5117584282066150277L;

	/**
	 * workspace name
	 */
	@PropertyViewData(title = "Name")
	private String name;

	/**
	 * workspace folder
	 */
	@PropertyViewData(title = "Path")
	private File file;

	/**
	 * list of {@link Workspace}
	 */
	private List<Workspace> workspaces;

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
		this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.file = workspceFolder;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final File getPath() {
		return file;
	}

	public final List<Workspace> getWorkspaces() {
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

	public final boolean removeWorkspace(Workspace workspace) {
		for (int i = 0; i <= workspaces.size() - 1; i++) {
			if (workspaces.get(i).equals(workspace)) {
				workspaces.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		for (Workspace openEsbWorkspace : workspaces) {
			if (openEsbWorkspace.equals(workspace)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Image getImage(boolean small) {
		return ImageFactory.MULTI_WORKSPACE;
	}

	@Override
	public final String getToolTip() {
		return "Multi-Workspace: Open ESB\nName: " + name + "\nPath:" + (file != null ? file.getPath() : "");
	}

}
