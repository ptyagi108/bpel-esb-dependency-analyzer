package com.tomecode.soa.bpel.activity;

/**
 * catch activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Catch extends Activity {

	private static final long serialVersionUID = 3511714056068910893L;

	private String faultVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 */
	public Catch(ActivityType type, String name, String faultVariable) {
		super(type, name);
		this.faultVariable = faultVariable;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

}
