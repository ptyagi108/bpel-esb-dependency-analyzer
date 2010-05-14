package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * pager activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Pager extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Pager(String name) {
		super(ActivityType.EMAIL, name);
	}

}
