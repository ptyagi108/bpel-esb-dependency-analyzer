package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * 
 * rethrow activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Rethrow extends Activity {

	private static final long serialVersionUID = -6103173655996168649L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Rethrow(String name) {
		super(ActivityType.OPEN_ESB_BPEL_RETHROW, name);
	}

}
