package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * flowN activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class FlowN extends Activity {

	private String n;

	private String indexVariable;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public FlowN(String name, String n, String indexVariable) {
		super(ActivityType.FLOWN, name);
		this.n = n;
		this.indexVariable = indexVariable;
	}

	public final void findVariable(FindUsageVariableResult findUsageVariableResult) {
		if (n != null && findUsageVariableResult.getVariable().toString().equals(n)) {
			findUsageVariableResult.addUsage(this);
		}
		if (indexVariable != null && findUsageVariableResult.getVariable().toString().equals(indexVariable)) {
			findUsageVariableResult.addUsage(this);
		}
	}
}
