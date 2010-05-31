package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * sms activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Sms extends Activity {

	private static final long serialVersionUID = 509780617476931941L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Sms(String name) {
		super(ActivityType.SMS, name);
	}

}
