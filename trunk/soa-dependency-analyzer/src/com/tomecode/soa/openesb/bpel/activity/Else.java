package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * else activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Else extends Activity {

	private static final long serialVersionUID = -5418661201196319018L;

	/**
	 * Constructor
	 */
	public Else() {
		super(ActivityType.OPEN_ESB_BPEL_ELSE, null);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_ELSE;
	}
}
