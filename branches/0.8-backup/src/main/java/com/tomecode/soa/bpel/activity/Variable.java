package com.tomecode.soa.bpel.activity;

/**
 * 
 * 
 * Variable in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Variable extends Activity {

	private static final long serialVersionUID = 1387914145237466640L;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name
	 * @param messageType
	 */
	public Variable(String name, String messageType) {
		super(ActivityType.VARIABLE, name);
	}

	public final String toString() {
		return name;
	}

}
