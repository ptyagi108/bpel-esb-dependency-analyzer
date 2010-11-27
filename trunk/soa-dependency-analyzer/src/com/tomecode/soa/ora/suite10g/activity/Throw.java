package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * 
 * throw activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Throw extends Activity {

	private static final long serialVersionUID = -6296106634300381880L;

	private String faultVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 */
	public Throw(String name, String faultVariable) {
		super(ActivityType.ORACLE_10G_THROW, name);
		this.faultVariable = faultVariable;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_THROW;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

}
