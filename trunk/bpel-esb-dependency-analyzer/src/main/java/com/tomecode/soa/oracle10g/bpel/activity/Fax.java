package com.tomecode.soa.oracle10g.bpel.activity;

/***
 * fax activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Fax extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Fax(String name) {
		super(ActivityType.FAX, name);
	}

}
