package com.tomecode.soa.oracle10g.bpel.activity;

/***
 * voice activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Voice extends Activity {

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Voice(String name) {
		super(ActivityType.VOICE, name);
	}

	public final String toString() {
		return (name != null ? name : super.toString());
	}
}
