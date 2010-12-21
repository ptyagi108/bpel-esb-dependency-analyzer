package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: requestTransform parent-element: wsCallout - {@link WsCallout}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class WsCalloutResponseTransform extends OsbActivity {

	public final String toString() {
		return "Response Action";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_RESPONSE;
	}

	public final String getToolTip() {
		return "Type: Response Action";
	}
}
