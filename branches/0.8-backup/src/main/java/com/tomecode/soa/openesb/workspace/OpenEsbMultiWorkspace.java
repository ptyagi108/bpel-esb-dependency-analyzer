package com.tomecode.soa.openesb.workspace;

import java.io.File;

import javax.swing.ImageIcon;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
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
public final class OpenEsbMultiWorkspace extends BasicNode<OpenEsbWorkspace> implements MultiWorkspace {

	private static final long serialVersionUID = -5117584282066150277L;

	/**
	 * workspace name
	 */
	private String name;

	/**
	 * workspace folder
	 */
	private File folder;

	/**
	 * list of {@link Workspace}
	 */
	// private List<Workspace> workspaces;

	public OpenEsbMultiWorkspace(String name, File workspceFolder) {
		super();
		// this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.folder = workspceFolder;
	}

	@Override
	public final File getFolder() {
		return this.folder;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public ImageIcon getIcon() {
		return null;
	}

	// @Override
	// public final List<OpenEsbWorkspace> getWorkspaces() {
	// return childs; // return this.workspaces;
	// }

}
