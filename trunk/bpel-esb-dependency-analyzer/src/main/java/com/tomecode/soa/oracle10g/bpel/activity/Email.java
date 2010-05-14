package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * email activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Email extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Email(String name) {
		super(ActivityType.EMAIL, name);
	}

}
