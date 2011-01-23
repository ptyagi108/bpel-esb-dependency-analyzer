package com.tomecode.soa.ora.suite10g.workspace;

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
 * Contains one or more {@link Ora10gWorkspace}
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
@PropertyGroupView(type = "Multi-Workspace...", name = "Oracle SOA Suite 10g")
public final class Ora10gMultiWorkspace implements MultiWorkspace {

	/**
	 * workspace name
	 */
	@PropertyViewData(title = "Name")
	private String name;;
	/**
	 * workspace folder
	 */
	@PropertyViewData(title = "Path")
	private File file;

	/**
	 * list of {@link Ora10gWorkspace}
	 */
	private List<Workspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name (user enter)
	 * @param file
	 *            workspace folder( user enter)
	 */
	public Ora10gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final File getPath() {
		return this.file;
	}

	public final String toString() {
		return getName();
	}

	public final boolean hasChildren() {
		return !this.workspaces.isEmpty();
	}

	public final Object[] getChildren() {
		return workspaces.toArray();
	}

	public final void addWorkspace(Ora10gWorkspace workspace) {
		workspace.setMultiWorkspace(this);
		this.workspaces.add(workspace);
	}

	public final List<Workspace> getWorkspaces() {
		return workspaces;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.ORACLE_1OG;
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
		for (Workspace ora10gWorkspace : workspaces) {
			if (workspace.equals(ora10gWorkspace)) {
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
		return "Multi-Workspace: Oracle SOA Suite 10g\nName: " + name + "\nPath:" + (file != null ? file.getPath() : "");
	}
}
