package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * voice activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Voice extends Activity {

	private static final long serialVersionUID = -7771451840355025106L;

	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public Voice(String name) {
		super(ActivityType.ORACLE_10G_VOICE, name);
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_VOICE;
	}
}
