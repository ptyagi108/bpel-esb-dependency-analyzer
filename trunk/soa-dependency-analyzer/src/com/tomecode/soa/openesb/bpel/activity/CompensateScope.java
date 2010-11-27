package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * compensateScope activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
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

}
