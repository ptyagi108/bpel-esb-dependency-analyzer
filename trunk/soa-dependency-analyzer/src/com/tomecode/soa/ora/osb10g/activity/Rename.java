package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: rename
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Rename extends OsbActivity {

	public final String toString() {
		return "rename";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_RENAME;
	}

	public final String getToolTip() {
		return "Type: Rename";
	}
}
