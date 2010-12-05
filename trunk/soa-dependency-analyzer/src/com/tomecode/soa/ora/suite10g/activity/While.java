package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * wait activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
	public While(String name, String condition, String variable) {
		super(ActivityType.ORACLE_10G_WHILE, name);
		this.variable = variable;
		this.condition = condition;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_WHILE;
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
