package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * onAlarm activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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

	/**
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ONALARM;
	}
	//
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
