package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch
 * 
 * @author Tomas Frastia
 * 
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public class Branch extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param errorHandler
	 */
	public Branch(String name, String errorHandler) {
		super(name, errorHandler);
	}

	public final String toString() {
		return name == null ? "Branch" : "Branch - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH;
	}

	public final String getToolTip() {
		return "Type: Branch\nName: " + (name != null ? name : "");
	}
}
