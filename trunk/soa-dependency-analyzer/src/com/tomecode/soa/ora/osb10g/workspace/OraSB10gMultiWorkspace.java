package com.tomecode.soa.ora.osb10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;
import com.tomecode.soa.workspace.Workspace.WorkspaceType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Oracle Service Bus 10g MULTI workspace
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gMultiWorkspace implements MultiWorkspace {

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
	private final List<Workspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace folder
	 */
	public OraSB10gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<Workspace>();
		this.name = name;
		this.file = file;
	}

	public List<Workspace> getWorkspaces() {
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

	public final OraSB10gWorkspace removeWorkspace(Workspace workspace) {
		for (int i = 0; i <= workspaces.size() - 1; i++) {
			if (workspaces.get(i).equals(workspace)) {
				return (OraSB10gWorkspace) workspaces.remove(i);
			}
		}
		return null;
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		for (Workspace oraSB10gWorkspace : workspaces) {
			if (oraSB10gWorkspace.equals(workspace)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.MULTI_WORKSPACE;
	}

	@Override
	public final String getToolTip() {
		return "MultiWorkspace: Oracle Service Bus 10g" + name + "\n\n" + (file != null ? file.getPath() : "");
	}

}
