package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: ifThenElse
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class IfThenElse extends OsbActivity {

	public final boolean hasIf() {
		for (OsbActivity activity : activities) {
			if (activity instanceof If) {
				return true;
			}
		}
		return false;
	}

	public final String toString() {
		return "If Then";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_IFTHENELSE;
	}

	public final String getToolTip() {
		return "Type: If Then";
	}
}
