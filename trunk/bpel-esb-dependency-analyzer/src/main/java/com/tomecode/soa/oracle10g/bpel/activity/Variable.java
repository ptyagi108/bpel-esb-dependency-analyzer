package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;

/**
 * 
 * 
 * Variable - special activity
 * 
 * @author Tomas Frastia
 * 
 */
public final class Variable extends Activity {

	private static final long serialVersionUID = 1387914145237466640L;

	private BpelProcessStrukture strukture;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name
	 * @param messageType
	 * @param strukture
	 */
	public Variable(String name, String messageType, BpelProcessStrukture strukture) {
		super(ActivityType.VARIABLE, name);
		this.strukture = strukture;
	}

	public final BpelProcessStrukture getStrukture() {
		return strukture;
	}

	public final String toString() {
		return name;
	}

}
