package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: default-branch
 * 
 * @author Tomas Frastia
 * 
 */
public final class DefaultBranch extends OsbActivity {

	public final String toString() {
		return "Default";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_PIPELINE_REQUEST;
	}
}
