package com.tomecode.soa.process;

import javax.swing.ImageIcon;
import javax.swing.tree.TreeNode;

import com.tomecode.soa.oracle10g.Workspace;

/**
 * 
 * Basic class for all BPEL or ESB project
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode {

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

	public abstract ImageIcon getIcon();

}
