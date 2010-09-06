package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: branch-node type: condition
 * 
 * @author Tomas Frastia
 * 
 */
public final class BranchNodeCondition extends OsbActivity {

	public BranchNodeCondition(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Branch Condition" : "Branch Condition - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH_NODE_CONDITION;
	}
}
