package com.tomecode.soa.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * email activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Email extends Activity {

	private static final long serialVersionUID = 1523369652771148368L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Email(String name) {
		super(ActivityType.ORACLE_10G_EMAIL, name);
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_EMAIL;
	}
}
