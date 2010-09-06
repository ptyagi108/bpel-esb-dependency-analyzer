package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: router
 * 
 * @author Tomas Frastia
 * 
 */
public final class Router extends OsbActivity {

	public final String toString() {
		return "router";
	}

	@Override
	public Image getImage() {
		return ImageFactory.OSB_10G_ROUTER;
	}
}
