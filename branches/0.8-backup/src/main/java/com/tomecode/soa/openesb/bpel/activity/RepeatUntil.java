package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * repeatUntil activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class RepeatUntil extends Activity {

	private static final long serialVersionUID = -389192710518075116L;

	private String condition;

	public RepeatUntil(String name, String condition) {
		super();
		this.name = name;
		this.condition = condition;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

}
