package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: delete
 * 
 * @author Tomas Frastia
 * 
 */
public final class Delete extends OsbActivity {

	public final String toString() {
		return "delete";
	}

	@Override
	public Image getImage() {
		return ImageFactory.OSB_10G_DELETE;
	}
}
