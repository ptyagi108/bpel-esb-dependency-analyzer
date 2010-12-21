package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: wsCallout
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class WsCallout extends OsbActivity {

	public final String toString() {
		return "Service Callout";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_WSCALLOUT;
	}

	public final String getToolTip() {
		return "Type: Service Callout";
	}
}
