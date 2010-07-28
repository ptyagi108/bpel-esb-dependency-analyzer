package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * elseif activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Elseif extends Activity {

	private static final long serialVersionUID = -4347239186652486377L;

	private String condition;

	public Elseif(String name, String condition) {
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
