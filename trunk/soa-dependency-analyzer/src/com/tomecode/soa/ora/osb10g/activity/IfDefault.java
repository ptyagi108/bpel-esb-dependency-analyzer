package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * parent element: ifThenElse - last child element: case
 * 
 * @author Tomas Frastia
 * 
 */
public final class IfDefault extends OsbActivity {

	public final String toString() {
		return "else";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_DEFAULT;
	}
}
