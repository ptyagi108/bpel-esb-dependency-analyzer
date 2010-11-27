package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * else activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Else extends Activity {

	private static final long serialVersionUID = -5418661201196319018L;

	/**
	 * Constructor
	 */
	public Else() {
		super(ActivityType.OPEN_ESB_BPEL_ELSE, null);
	}

}
