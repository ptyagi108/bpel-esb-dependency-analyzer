package com.tomecode.soa.oracle10g.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.workspace.MultiWorkspace;

/**
 * 
 * Contains one or more {@link Ora10gWorkspace}
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public class Ora10gMultiWorkspace implements MultiWorkspace {

	/**
	 * workspace name
	 */
	private String name;;
	/**
	 * workspace folder
	 */
	private File file;

	/**
	 * list of {@link Ora10gWorkspace}
	 */
	private List<Ora10gWorkspace> oracle10gWorkspaces;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            workspace name (user enter)
	 * @param file
	 *            workspace folder( user enter)
	 */
	public Ora10gMultiWorkspace(String name, File file) {
		this.oracle10gWorkspaces = new ArrayList<Ora10gWorkspace>();
		this.name = name;
		this.file = file;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final File getFile() {
		return this.file;
	}

	public final String toString() {
		return getName();
	}

	public final boolean hasChildren() {
		return !this.oracle10gWorkspaces.isEmpty();
	}

	public final Object[] getChildren() {
		return oracle10gWorkspaces.toArray();
	}

	public final void addWorkspace(Ora10gWorkspace workspace) {
		this.oracle10gWorkspaces.add(workspace);
	}

	public final List<Ora10gWorkspace> getWorkspaces() {
		return oracle10gWorkspaces;
	}

	@Override
	public final WorkspaceType getType() {
		return WorkspaceType.ORACLE_1OG;
	}
}
