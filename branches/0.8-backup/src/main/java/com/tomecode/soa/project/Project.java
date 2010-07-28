package com.tomecode.soa.project;

import java.io.File;
import java.io.Serializable;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.workspace.Workspace;

/**
 * 
 * Basic class for all BPEL or ESB project
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode, IconNode, Serializable {

	private static final long serialVersionUID = 4360951852708549931L;
	// private Ora10gWorkspace workspace;

	private Workspace workspace;

	protected String name;

	protected File folder;
	/**
	 * project type
	 */
	protected final ProjectType type;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            project name
	 * @param folder
	 *            project folder
	 * @param type
	 *            project type
	 */
	public Project(String name, File folder, ProjectType type) {
		this.name = name;
		this.folder = folder;
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the folder
	 */
	public final File getFolder() {
		return folder;
	}

	/**
	 * @return the type
	 */
	public final ProjectType getType() {
		return type;
	}

	public final Workspace getWorkspace() {
		return workspace;
	}

	public final void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

}
