package com.tomecode.soa.activity;

import org.eclipse.swt.graphics.Image;

/**
 * (c) Copyright Tomecode.com, 2011. All rights reserved.
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class BasicActivity extends Activity {

	public BasicActivity(ActivityType type, String name) {
		super(type, name);
	}

	@Override
	public Image getImage() {
		return null;
	}

}
