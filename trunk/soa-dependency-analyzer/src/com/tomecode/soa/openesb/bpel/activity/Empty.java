package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * empty activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Empty extends Activity {

	private static final long serialVersionUID = -2041759641287369784L;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param name
	 */
	public Empty(String name) {
		super(ActivityType.OPEN_ESB_BPEL_EMPTY, name);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_EMPTY;
	}
}
