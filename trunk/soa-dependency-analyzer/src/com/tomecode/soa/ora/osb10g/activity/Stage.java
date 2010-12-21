package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: stage
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Stage extends OsbActivity {

	public Stage(String name) {
		super(name);
	}

	public final Image getImage() {
		return ImageFactory.OSB_10G_PROXY_SERVICE;
	}

	public final String toString() {
		return name == null ? "stage" : "stage - " + name;
	}

	public final String getToolTip() {
		return "Type: Stage\nName: " + (name != null ? name : "");
	}
}
