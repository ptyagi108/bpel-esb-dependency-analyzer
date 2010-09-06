package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: requestTransform parent-element: wsCallout - {@link WsCallout}
 * 
 * @author Tomas Frastia
 * 
 */
public final class WsCalloutResponseTransform extends OsbActivity {

	public final String toString() {
		return "Response Action";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_RESPONSE;
	}
}
