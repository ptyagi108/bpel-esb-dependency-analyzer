package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * case from switch in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public class Case extends Activity {
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Case(String name) {
		super(ActivityType.CASE, name);
	}

	// public final String toString() {
	// return (name != null) ? name : super.toString();
	// }
}
