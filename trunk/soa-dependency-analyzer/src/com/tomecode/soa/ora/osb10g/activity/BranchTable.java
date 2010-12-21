package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: branch-table
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class BranchTable extends OsbActivity {

	private String variable;

	/**
	 * Constructor
	 * 
	 * @param variable
	 */
	public BranchTable(String variable) {
		super();
		this.variable = variable;
	}

	public final String getVariable() {
		return variable;
	}

	public final String toString() {
		return "branch-table";
	}

	@Override
	public final Image getImage() {
		return null;
	}

	public final String getToolTip() {
		return "Type: branch-table\nVariable: " + (variable != null ? variable : "");
	}

}
