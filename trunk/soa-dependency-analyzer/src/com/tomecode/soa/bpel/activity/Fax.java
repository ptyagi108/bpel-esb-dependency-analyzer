package com.tomecode.soa.bpel.activity;

/***
 * fax activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Fax extends Activity {

	private static final long serialVersionUID = 1088119687553235066L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Fax(String name) {
		super(ActivityType.ORACLE_10G_FAX, name);
	}

}
