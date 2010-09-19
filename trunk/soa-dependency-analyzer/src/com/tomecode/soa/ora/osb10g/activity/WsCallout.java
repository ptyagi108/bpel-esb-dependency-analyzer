package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: wsCallout
 * 
 * @author Tomas Frastia
 * 
 */
public final class WsCallout extends OsbActivity {

	public final String toString() {
		return "Service Callout";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_WSCALLOUT;
	}
}
