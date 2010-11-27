package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * compensate activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Compensate extends Activity {

	private static final long serialVersionUID = 1085400307603709390L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Compensate(String name) {
		super(ActivityType.OPEN_ESB_BPEL_COMPENSATE, name);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_COMPENSATE;
	}
}
