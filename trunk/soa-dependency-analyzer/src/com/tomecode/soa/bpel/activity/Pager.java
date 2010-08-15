package com.tomecode.soa.bpel.activity;

/**
 * pager activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Pager extends Activity {

	private static final long serialVersionUID = 3262302996646664193L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Pager(String name) {
		super(ActivityType.ORACLE_10G_EMAIL, name);
	}

}
