package com.tomecode.soa.bpel.activity;

/**
 * 
 * throw activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Throw extends Activity {

	private static final long serialVersionUID = -6296106634300381880L;

	private String faultVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 */
	public Throw(ActivityType type, String name, String faultVariable) {
		super(type, name);
		this.faultVariable = faultVariable;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

}
