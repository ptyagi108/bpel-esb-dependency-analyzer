package com.tomecode.soa.bpel.activity;

/**
 * wait activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class While extends Activity {

	private static final long serialVersionUID = 912793397218119096L;

	private String variable;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 */
	public While(ActivityType type, String name, String condition, String variable) {
		super(type, name);
		this.variable = variable;
		this.condition = condition;
	}

	/**
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
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
