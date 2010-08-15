package com.tomecode.soa.bpel.activity;

/**
 * email activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Email extends Activity {

	private static final long serialVersionUID = 1523369652771148368L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Email(String name) {
		super(ActivityType.ORACLE_10G_EMAIL, name);
	}

}
