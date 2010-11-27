package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * repeatUntil activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class RepeatUntil extends Activity {

	private static final long serialVersionUID = -389192710518075116L;

	private String condition;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param condition
	 */
	public RepeatUntil(String name, String condition) {
		super(ActivityType.OPEN_ESB_BPEL_REPEAT_UNTIL, name);
		this.condition = condition;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_REPEAT_UNTIL;
	}

	/**
	 * @return the condition
	 */
	public final String getCondition() {
		return condition;
	}

}
