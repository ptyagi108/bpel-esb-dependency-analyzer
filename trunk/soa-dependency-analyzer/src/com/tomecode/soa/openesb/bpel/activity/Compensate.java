package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * compensate activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
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


}
