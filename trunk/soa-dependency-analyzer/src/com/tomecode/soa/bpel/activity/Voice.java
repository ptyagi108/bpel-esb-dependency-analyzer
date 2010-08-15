package com.tomecode.soa.bpel.activity;

/***
 * voice activity in BPEL process
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
		super(ActivityType.ORACLE_10G_VOICE, name);
	}

}
