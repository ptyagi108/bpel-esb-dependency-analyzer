package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * sms activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Sms extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Sms(String name) {
		super(ActivityType.SMS, name);
	}

	// public final String toString() {
	// return (name != null ? name : super.toString());
	// }
}
