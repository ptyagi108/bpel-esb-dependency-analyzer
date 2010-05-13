package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * HumanTask in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class HumanTask extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public HumanTask(String name) {
		super(ActivityType.HUMANTASK, name);
	}
}
