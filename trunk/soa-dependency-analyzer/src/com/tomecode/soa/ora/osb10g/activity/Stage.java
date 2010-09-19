package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: stage
 * 
 * @author Tomas Frastia
 * 
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

}
