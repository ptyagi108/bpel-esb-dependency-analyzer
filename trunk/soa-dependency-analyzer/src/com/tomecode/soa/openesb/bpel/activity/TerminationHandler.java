package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * terminationHandler activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class TerminationHandler extends Activity {

	private static final long serialVersionUID = -870356828860684328L;

	/**
	 * Constructor
	 */
	public TerminationHandler() {
		super(ActivityType.OPEN_ESB_BPEL_TERMINATION_HANDLER, null);
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_TERMINATION_HANDLER;
	}
}
