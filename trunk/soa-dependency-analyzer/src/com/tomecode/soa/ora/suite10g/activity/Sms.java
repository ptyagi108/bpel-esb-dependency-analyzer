package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * sms activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Sms extends Activity {

	private static final long serialVersionUID = 509780617476931941L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Sms(String name) {
		super(ActivityType.ORACLE_10G_SMS, name);
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_SMS;
	}
}
