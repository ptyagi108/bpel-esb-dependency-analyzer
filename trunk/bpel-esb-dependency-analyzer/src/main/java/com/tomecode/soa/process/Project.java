package com.tomecode.soa.process;

import javax.swing.tree.TreeNode;

/**
 * 
 * Basic class for all BPEL or ESB project
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode {

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

}
