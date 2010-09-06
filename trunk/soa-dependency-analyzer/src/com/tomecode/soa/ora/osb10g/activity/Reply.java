package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: reply
 * 
 * @author Tomas Frastia
 * 
 */
public final class Reply extends OsbActivity {

	public final String toString() {
		return "reply";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_REPLY;
	}
}
