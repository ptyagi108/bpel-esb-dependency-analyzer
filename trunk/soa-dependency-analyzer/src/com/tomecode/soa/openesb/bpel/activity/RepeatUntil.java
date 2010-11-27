package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * repeatUntil activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class RepeatUntil extends Activity {

	private static final long serialVersionUID = -389192710518075116L;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public RepeatUntil(String name, String condition) {
		super(ActivityType.OPEN_ESB_BPEL_REPEAT_UNTIL, name);
		this.condition = condition;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

}
