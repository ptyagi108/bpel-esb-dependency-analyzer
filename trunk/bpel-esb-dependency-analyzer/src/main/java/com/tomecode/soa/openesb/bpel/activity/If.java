package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * if activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class If extends Activity {

	private static final long serialVersionUID = -4506312286588488284L;

	private String condition;

	public If(String name, String condition) {
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
