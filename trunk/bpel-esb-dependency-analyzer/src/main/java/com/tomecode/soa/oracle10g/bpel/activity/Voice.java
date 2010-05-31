package com.tomecode.soa.oracle10g.bpel.activity;

/***
 * voice activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Voice extends Activity {

	private static final long serialVersionUID = -7771451840355025106L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Voice(String name) {
		super(ActivityType.VOICE, name);
	}

}
