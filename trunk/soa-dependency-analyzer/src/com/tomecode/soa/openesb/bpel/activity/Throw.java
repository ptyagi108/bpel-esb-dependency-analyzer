package com.tomecode.soa.openesb.bpel.activity;

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
		super(ActivityType.OPEN_ESB_BPEL_THROW, name);
		this.faultVariable = faultVariable;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_THROW;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

}
