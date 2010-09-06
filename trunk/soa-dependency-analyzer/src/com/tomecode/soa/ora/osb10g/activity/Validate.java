package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element : validate
 * 
 * @author Tomas Frastia
 * 
 */
public final class Validate extends OsbActivity {

	public final String toString() {
		return "validate";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_VALIDATE;
	}
}
