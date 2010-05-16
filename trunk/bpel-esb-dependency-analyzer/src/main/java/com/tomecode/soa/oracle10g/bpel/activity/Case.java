package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * case from switch in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Case extends Activity {

	private String variable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 */
	public Case(String name, String variable) {
		super(ActivityType.CASE, name);
		this.variable = variable;
	}

	/**
	 * find variable in activity
	 */
	public final void findVariable(FindUsageVariableResult findUsageVariableResult) {
		if (variable != null && findUsageVariableResult.getVariable().toString().equals(variable)) {
			findUsageVariableResult.addUsage(this);
		}
	}

}
