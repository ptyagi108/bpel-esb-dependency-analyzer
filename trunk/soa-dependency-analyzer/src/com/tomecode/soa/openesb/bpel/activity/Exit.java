package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * 
 * Exit activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Exit extends Activity {

	private static final long serialVersionUID = 5160735435575604497L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Exit(String name) {
		super(ActivityType.OPEN_ESB_BPEL_EXIT, name);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_EXIT;
	}
}
