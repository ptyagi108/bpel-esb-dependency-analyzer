package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: responseTransform parent-element: wsCallout - {@link WsCallout}
 * 
 * @author Tomas Frastia
 * 
 */
public final class WsCalloutRequestTransform extends OsbActivity {

	public final String toString() {
		return "Request Action";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_REQUEST;
	}
}
