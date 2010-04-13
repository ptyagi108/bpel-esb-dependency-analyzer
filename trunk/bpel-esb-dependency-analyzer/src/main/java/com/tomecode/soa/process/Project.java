package com.tomecode.soa.process;

import javax.swing.tree.TreeNode;

/**
 * 
 * Basic class
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Project implements TreeNode {

	protected final ProjectType type;

	public Project(ProjectType type) {
		this.type = type;
	}

	public final ProjectType getType() {
		return type;
	}

}
