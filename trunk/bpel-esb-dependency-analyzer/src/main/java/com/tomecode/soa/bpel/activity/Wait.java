package com.tomecode.soa.bpel.activity;

import com.tomecode.soa.dependency.analyzer.usages.FindUsageVariableResult;

/**
 * wait activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Wait extends Activity {

	private static final long serialVersionUID = 8837403543290074974L;

	private String variable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 */
	public Wait(String name, String variable) {
		super(ActivityType.WAIT, name);
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
