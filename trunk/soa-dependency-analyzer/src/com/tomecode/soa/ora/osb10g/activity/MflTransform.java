package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: mflTransform
 * 
 * @author Tomas Frastia
 * 
 */
public final class MflTransform extends OsbActivity {

	public final String toString() {
		return "Mfl Transform";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_MFL;
	}
}
