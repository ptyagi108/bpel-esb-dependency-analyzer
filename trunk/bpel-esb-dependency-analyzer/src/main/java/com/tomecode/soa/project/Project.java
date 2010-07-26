package com.tomecode.soa.project;

import java.io.Serializable;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.dependency.analyzer.gui.tree.node.IconNode;
import com.tomecode.soa.oracle10g.Ora10gWorkspace;

/**
 * 
 * Basic class for all BPEL or ESB project
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode, IconNode, Serializable {

	private static final long serialVersionUID = 4360951852708549931L;
	private Ora10gWorkspace workspace;
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

	public final Ora10gWorkspace getWorkspace() {
		return workspace;
	}

	public final void setWorkspace(Ora10gWorkspace workspace) {
		this.workspace = workspace;
	}

}
