package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: route-node
 * 
 * @author Tomas Frastia
 * 
 */
public final class RouteNode extends OsbActivity {

	public RouteNode(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Route Node" : "Route Node - " + name;
	}

	@Override
	public Image getImage() {
		return ImageFactory.OSB_10G_ROUTER;
	}
}
