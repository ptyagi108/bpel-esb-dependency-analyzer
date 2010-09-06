package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: insert
 * 
 * @author Tomas Frastia
 * 
 */
public final class Insert extends OsbActivity {

	public final String toString() {
		return "insert";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_INSERT;
	}
}
