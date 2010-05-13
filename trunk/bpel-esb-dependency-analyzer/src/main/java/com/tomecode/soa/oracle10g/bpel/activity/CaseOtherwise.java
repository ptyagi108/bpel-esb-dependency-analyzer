package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * case from switch in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public class CaseOtherwise extends Activity {
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public CaseOtherwise(String name) {
		super(ActivityType.OTHERWISE, name);
	}

	public CaseOtherwise() {
		this(null);
	}

	public final String toString() {
		return name;
	}
}
