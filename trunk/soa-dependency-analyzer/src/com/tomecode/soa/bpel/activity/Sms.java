package com.tomecode.soa.bpel.activity;

/**
 * sms activity in BPEL process
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
		super(ActivityType.ORACLE_10G_SMS, name);
	}

}
