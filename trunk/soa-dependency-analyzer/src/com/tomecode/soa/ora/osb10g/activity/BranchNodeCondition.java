package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch-node type: condition
 * 
 * @author Tomas Frastia
 * 
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class BranchNodeCondition extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public BranchNodeCondition(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final String toString() {
		return name == null ? "Branch Condition" : "Branch Condition - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH_NODE_CONDITION;
	}

	public final String getToolTip() {
		return "Type: Branch Condition\nName: " + (name != null ? name : "");
	}
}
