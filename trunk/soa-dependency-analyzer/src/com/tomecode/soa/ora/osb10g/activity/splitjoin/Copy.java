package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element:copy in SplitJoin flow
 * 
 * @author Tomas Frastia
 * 
 */
public final class Copy extends OsbActivity {

	public final String toString() {
		return "Copy";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_COPY;
	}

}
