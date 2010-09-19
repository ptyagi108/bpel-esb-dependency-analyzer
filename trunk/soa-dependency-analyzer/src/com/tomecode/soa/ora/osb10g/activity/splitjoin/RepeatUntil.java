package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: repeatUntil in SplitJoin element
 * 
 * @author Tomas Frastia
 * 
 */
public final class RepeatUntil extends OsbActivity {

	public final String toString() {
		return "Repeat Until";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_WHILE;
	}

}
