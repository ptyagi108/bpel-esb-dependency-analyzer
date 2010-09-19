package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: replace in SplitJoin
 * 
 * @author Tomas Frastia
 * 
 */
public final class Replace extends OsbActivity {

	public final String toString() {
		return "Replace Action";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_REPLACE;
	}

}
