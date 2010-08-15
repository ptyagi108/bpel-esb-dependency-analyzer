package com.tomecode.soa.bpel.activity;

/**
 * case from switch in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Case extends Activity {

	private static final long serialVersionUID = 804854324731704322L;

	/**
	 * variable in case
	 */
	private String variable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 */
	public Case(String name, String variable) {
		super(ActivityType.ORACLE_10G_CASE, name);
		this.variable = variable;
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult
	// findUsageVariableResult) {
	// if (variable != null &&
	// findUsageVariableResult.getVariable().toString().equals(variable)) {
	// findUsageVariableResult.addUsage(this);
	// }
	// }

}
