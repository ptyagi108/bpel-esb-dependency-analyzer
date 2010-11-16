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
	private final List<OraSB10gWorkspace> workspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name
	 * @param file
	 *            workspace folder
	 */
	public OraSB10gMultiWorkspace(String name, File file) {
		this.workspaces = new ArrayList<OraSB10gWorkspace>();
		this.name = name;
		this.file = file;
	}

	public List<OraSB10gWorkspace> getWorkspaces() {
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
				return workspaces.remove(i);
			}
		}
		return null;
	}

	@Override
	public final boolean containsWorkspace(Workspace workspace) {
		for (OraSB10gWorkspace oraSB10gWorkspace : workspaces) {
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

}
