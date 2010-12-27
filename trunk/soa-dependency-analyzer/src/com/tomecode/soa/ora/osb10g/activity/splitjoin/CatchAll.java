package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: catchAll in SplitJoin flow
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class CatchAll extends OsbActivity {

	public final String toString() {
		return "CatchAll";
	}

	@Override
	public Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_CATCHALL;
	}

}
