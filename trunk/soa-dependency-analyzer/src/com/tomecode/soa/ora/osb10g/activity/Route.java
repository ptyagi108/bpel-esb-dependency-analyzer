package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: route
 * 
 * @author Tomas Frastia
 * 
 */
public final class Route extends OsbActivity {

	public final String toString() {
		return "route";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ROUTE;
	}
}
