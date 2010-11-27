package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * elseif activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Elseif extends Activity {

	private static final long serialVersionUID = -4347239186652486377L;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public Elseif(String name, String condition) {
		super(ActivityType.OPEN_ESB_BPEL_ELSEIF, name);
		this.condition = condition;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_ELSEIF;
	}
}
