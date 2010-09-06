package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: dynamic-route
 * 
 * @author Tomas Frastia
 * 
 */
public final class DynamicRoute extends OsbActivity {

	public final String toString() {
		return "Dynamic Publis";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_DYNAMIC_ROUTING;
	}
}
