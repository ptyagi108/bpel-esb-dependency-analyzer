package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: ifThenElse
 * 
 * @author Tomas Frastia
 * 
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

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_IFTHENELSE;
	}

}
