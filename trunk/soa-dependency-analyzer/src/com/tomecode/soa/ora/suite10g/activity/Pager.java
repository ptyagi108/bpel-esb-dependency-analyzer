package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * pager activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 * 
 */
public final class Pager extends Activity {

	private static final long serialVersionUID = 3262302996646664193L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Pager(String name) {
		super(ActivityType.ORACLE_10G_PAGER, name);
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_PAGER;
	}
}
