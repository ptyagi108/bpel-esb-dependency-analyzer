package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * 
 * throw activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Throw extends Activity {

	private String faultVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 */
	public Throw(String name, String faultVariable) {
		super(ActivtyType.THROW, name);
		this.faultVariable = faultVariable;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

	public final String toString() {
		return (name != null ? name : super.toString());
	}
}
