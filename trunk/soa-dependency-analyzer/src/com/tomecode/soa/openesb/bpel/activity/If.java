package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * if activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class If extends Activity {

	private static final long serialVersionUID = -4506312286588488284L;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public If(String name, String condition) {
		super(ActivityType.OPEN_ESB_BPEL_IF, name);
		this.condition = condition;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_IF;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

}
