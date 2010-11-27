package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * case from switch in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SWITCH;
	}

	/**
	 * 
	 * variable in case activity
	 * 
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
