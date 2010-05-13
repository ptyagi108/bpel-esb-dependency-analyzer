package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * Transform activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Transform extends Activity {

	private String fromVariable;
	private String toVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param fromVariable
	 * @param toVariable
	 */
	public Transform(String name, String fromVariable, String toVariable) {
		super(ActivityType.TRANSFORMATE, name);
		this.fromVariable = fromVariable;
		this.toVariable = toVariable;
	}

	public final String getFromVariable() {
		return fromVariable;
	}

	public final String getToVariable() {
		return toVariable;
	}

	// public final String toString() {
	// return (name != null ? name : super.toString());
	// }
}
