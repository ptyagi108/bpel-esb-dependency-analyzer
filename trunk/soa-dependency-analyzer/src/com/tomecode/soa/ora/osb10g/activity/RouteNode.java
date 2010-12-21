package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: route-node
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class RouteNode extends OsbActivity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public RouteNode(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Route Node" : "Route Node - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ROUTER;
	}

	public final String getToolTip() {
		return "Type: Route Node\nName: " + (name != null ? name : "");
	}
}
