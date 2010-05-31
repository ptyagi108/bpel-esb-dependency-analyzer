package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * catch activity in bpel process
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
	public Catch(String name, String faultVariable) {
		super(ActivityType.CATCH, name);
		this.faultVariable = faultVariable;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

}
