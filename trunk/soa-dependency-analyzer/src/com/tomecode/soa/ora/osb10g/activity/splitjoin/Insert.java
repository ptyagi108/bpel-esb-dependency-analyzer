package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: insert in SplitJoin flow
 * 
 * @author Tomas Frastia
 * 
 */
public final class Insert extends OsbActivity {

	public final String toString() {
		return "Insert Action";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_INSERT;
	}

}
