package com.tomecode.soa.bpel.activity;

/**
 * case from switch in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public class CaseOtherwise extends Activity {

	private static final long serialVersionUID = -4292260142977278326L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public CaseOtherwise(String name) {
		super(ActivityType.OTHERWISE, name);
	}

	/**
	 * Constructor
	 */
	public CaseOtherwise() {
		this(null);
	}

	public final String toString() {
		return name;
	}
}
