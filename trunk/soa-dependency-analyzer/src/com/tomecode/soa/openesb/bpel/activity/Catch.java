package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * catch activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Catch extends Activity {

	private static final long serialVersionUID = 3511714056068910893L;

	private String faultVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param faultVariable
	 */
	public Catch(String name, String faultVariable) {
		super(ActivityType.OPEN_ESB_BPEL_CATCH, name);
		this.faultVariable = faultVariable;
	}

	public final String getFaultVariable() {
		return faultVariable;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_CATCH;
	}

}
