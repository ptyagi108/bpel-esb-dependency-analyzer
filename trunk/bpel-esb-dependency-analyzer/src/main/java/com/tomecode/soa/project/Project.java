package com.tomecode.soa.project;

import java.io.Serializable;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.oracle10g.Workspace;

/**
 * 
 * Basic class for all BPEL or ESB project
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode, IconNode, Serializable {

	private static final long serialVersionUID = 4360951852708549931L;
	private Workspace workspace;
	/**
	 * if true then project is in *.jws
	 */
	private boolean isInJws;
	/**
	 * project type
	 */
	protected final ProjectType type;

	/**
	 * Constructor
	 * 
	 * @param type
	 */
	public Project(ProjectType type) {
		this.type = type;
	}

	/**
	 * {@link ProjectType}
	 * 
	 * @return
	 */
	public final ProjectType getType() {
		return type;
	}

	public final boolean isInJws() {
		return isInJws;
	}

	public final void setInJws(boolean isInJws) {
		this.isInJws = isInJws;
	}

	public final Workspace getWorkspace() {
		return workspace;
	}

	public final void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

}
