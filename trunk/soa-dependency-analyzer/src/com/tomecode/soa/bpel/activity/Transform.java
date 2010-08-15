package com.tomecode.soa.bpel.activity;

/**
 * Transform activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Transform extends Activity {

	private static final long serialVersionUID = 752167762231326145L;

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
		super(ActivityType.ORACLE_10G_TRANSFORMATE, name);
		this.fromVariable = fromVariable;
		this.toVariable = toVariable;
	}

	public final String getFromVariable() {
		return fromVariable;
	}

	public final String getToVariable() {
		return toVariable;
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult usage) {
	// if (fromVariable != null &&
	// fromVariable.equals(usage.getVariable().toString())) {
	// usage.addUsage(this);
	// } else if (toVariable != null &&
	// toVariable.equals(usage.getVariable().toString())) {
	// usage.addUsage(this);
	// }
	// }

}
