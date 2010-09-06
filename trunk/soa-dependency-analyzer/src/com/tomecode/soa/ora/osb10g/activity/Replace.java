package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: replace
 * 
 * @author Tomas Frastia
 * 
 */
public final class Replace extends OsbActivity {

	public final String toString() {
		return "replace";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_REPLACE;
	}
}
