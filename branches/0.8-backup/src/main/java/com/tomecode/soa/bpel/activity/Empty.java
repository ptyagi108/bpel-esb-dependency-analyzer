package com.tomecode.soa.bpel.activity;

/**
 * empty activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Empty extends Activity {

	private static final long serialVersionUID = -2041759641287369784L;

	public Empty(String name) {
		super(ActivityType.EMPTY, name);
	}
}
