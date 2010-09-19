package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: javaCallout in SplitJoin
 * 
 * @author Tomas Frastia
 * 
 */
public final class JavaCallout extends OsbActivity {

	public final String toString() {
		return "Java Callout";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_JAVA_CALLOUT;
	}

}
