package com.tomecode.soa.bpel.activity;

/**
 * HumanTask in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class HumanTask extends Activity {

	private static final long serialVersionUID = 4638676565146012787L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public HumanTask(String name) {
		super(ActivityType.ORACLE_10G_HUMANTASK, name);
	}

}
