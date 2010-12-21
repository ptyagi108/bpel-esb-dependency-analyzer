package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: dynamic-route
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class DynamicRoute extends OsbActivity {

	public final String toString() {
		return "Dynamic Publis";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_DYNAMIC_ROUTING;
	}

	public final String getToolTip() {
		return "Type: Dynamic Publis";
	}
}
