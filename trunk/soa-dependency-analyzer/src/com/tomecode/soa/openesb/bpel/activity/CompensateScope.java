package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * compensateScope activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class CompensateScope extends Activity {

	private static final long serialVersionUID = -2418281156036537845L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public CompensateScope(String name) {
		super(ActivityType.OPEN_ESB_BPEL_COMPENSATESCOPE, name);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_COMPENSATESCOPE;
	}
}
