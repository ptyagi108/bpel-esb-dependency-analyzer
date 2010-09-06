package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: branch
 * 
 * @author Tomas Frastia
 * 
 */
public class Branch extends OsbActivity {

	public Branch(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Branch" : "Branch - " + name;
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_BRANCH;
	}
}
