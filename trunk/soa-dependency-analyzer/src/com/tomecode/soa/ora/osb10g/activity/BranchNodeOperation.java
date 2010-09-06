package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: branch-node type: operation
 * 
 * @author Tomas Frastia
 * 
 */
public final class BranchNodeOperation extends OsbActivity {

	public BranchNodeOperation(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Branch Operation" : "Branch Operation - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH_OPERATION;
	}
}
