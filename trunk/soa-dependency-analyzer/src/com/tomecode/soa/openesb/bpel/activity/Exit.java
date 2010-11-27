package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

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

}
