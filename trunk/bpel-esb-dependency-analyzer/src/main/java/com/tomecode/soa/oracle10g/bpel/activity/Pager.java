package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * pager activity in bpel process
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
		super(ActivityType.EMAIL, name);
	}

}
