package com.tomecode.soa.process;

import javax.swing.tree.TreeNode;

/**
 * 
 * Basic class
 * 
 * @author Frastia Tomas
 * 
 */
public abstract class Service implements TreeNode {

	protected final ServiceType type;

	public Service(ServiceType type) {
		this.type = type;
	}

	public final ServiceType getType() {
		return type;
	}

}
