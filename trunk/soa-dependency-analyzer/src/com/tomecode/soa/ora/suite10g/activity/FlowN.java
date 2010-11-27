package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * flowN activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_FLOWN;
	}

	/**
	 * @return the n
	 */
	public final String getN() {
		return n;
	}

	/**
	 * @return the indexVariable
	 */
	public final String getIndexVariable() {
		return indexVariable;
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
