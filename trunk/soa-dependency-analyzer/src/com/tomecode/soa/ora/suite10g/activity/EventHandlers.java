package com.tomecode.soa.ora.suite10g.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class EventHandlers extends Activity {

	public EventHandlers(String name) {
		super(ActivityType.ORACLE_10G_EVENTHANDLERS, name);
	}
}
