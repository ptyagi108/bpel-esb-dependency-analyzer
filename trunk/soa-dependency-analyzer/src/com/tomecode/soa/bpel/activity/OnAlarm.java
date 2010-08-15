package com.tomecode.soa.bpel.activity;


/**
 * onAlarm activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class OnAlarm extends Activity {

	private static final long serialVersionUID = 8364805233236556426L;

	private String variable;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public OnAlarm(String variable) {
		super(ActivityType.ORACLE_10G_ONALARM, null);
		this.variable = variable;
	}
//
//	/**
//	 * find variable in activity
//	 */
//	public final void findVariable(FindUsageVariableResult findUsageVariableResult) {
//		if (variable != null && findUsageVariableResult.getVariable().toString().equals(variable)) {
//			findUsageVariableResult.addUsage(this);
//		}
//	}
}
