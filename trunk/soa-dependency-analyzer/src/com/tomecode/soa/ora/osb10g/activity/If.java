package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * parent element: ifThenElse - first child element: case
 * 
 * @author Tomas Frastia
 * 
 */
public final class If extends OsbActivity {

	public final String toString() {
		return "if";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ELSEIF;
	}
}
