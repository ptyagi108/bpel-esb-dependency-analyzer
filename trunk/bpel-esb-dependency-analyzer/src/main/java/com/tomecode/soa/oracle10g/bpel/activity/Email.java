package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * email activity in bpel process
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
		super(ActivityType.EMAIL, name);
	}

}
