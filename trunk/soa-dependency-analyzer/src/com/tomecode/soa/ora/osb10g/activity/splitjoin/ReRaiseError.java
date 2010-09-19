package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: throw in SplitJoin flow
 * 
 * @author Tomas Frastia
 * 
 */
public final class ReRaiseError extends OsbActivity {

	public final String toString() {
		return "Re-Raise Error";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_RAISE_ERROR;
	}

}
