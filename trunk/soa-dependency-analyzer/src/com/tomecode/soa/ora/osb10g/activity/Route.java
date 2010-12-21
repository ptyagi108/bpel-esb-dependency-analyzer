package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: route
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Route extends OsbActivity {

	public final String toString() {
		return "Routing";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ROUTE;
	}

	public final String getToolTip() {
		return "Type: Routing";
	}
}
