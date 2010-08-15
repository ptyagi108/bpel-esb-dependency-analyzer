package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * if activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class If extends Activity {

	private static final long serialVersionUID = -4506312286588488284L;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public If(ActivityType type, String name, String condition) {
		super(type, name);
		this.condition = condition;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

}
