package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * wait activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
		super(ActivityType.ORACLE_10G_WAIT, name);
		this.variable = variable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_WAIT;
	}

	/**
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
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
