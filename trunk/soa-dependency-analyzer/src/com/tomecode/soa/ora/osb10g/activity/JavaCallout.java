package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: javaCallout
 * 
 * @author Tomas Frastia
 * 
 */
public final class JavaCallout extends OsbActivity {

	public final String toString() {
		return "javaCallout";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_JAVA_CALLOUT;
	}
}
