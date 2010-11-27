package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * terminationHandler activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
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

}
