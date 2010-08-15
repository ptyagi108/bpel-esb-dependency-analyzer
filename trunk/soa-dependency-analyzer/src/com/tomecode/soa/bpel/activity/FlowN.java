package com.tomecode.soa.bpel.activity;


/**
 * flowN activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class FlowN extends Activity {

	private static final long serialVersionUID = 8165417878176837717L;

	private String n;

	private String indexVariable;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public FlowN(String name, String n, String indexVariable) {
		super(ActivityType.ORACLE_10G_FLOWN, name);
		this.n = n;
		this.indexVariable = indexVariable;
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult
	// findUsageVariableResult) {
	// if (n != null &&
	// findUsageVariableResult.getVariable().toString().equals(n)) {
	// findUsageVariableResult.addUsage(this);
	// }
	// if (indexVariable != null &&
	// findUsageVariableResult.getVariable().toString().equals(indexVariable)) {
	// findUsageVariableResult.addUsage(this);
	// }
	// }
}
