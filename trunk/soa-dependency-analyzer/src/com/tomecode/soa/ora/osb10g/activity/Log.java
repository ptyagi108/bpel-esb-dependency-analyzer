package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: log
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Log extends OsbActivity {

	private String logLevel;

	public Log(String logLevel) {
		this.logLevel = logLevel;
	}

	public final String getLogLevel() {
		return logLevel;
	}

	public final String toString() {
		return "Log";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_LOG;
	}

	public final String getToolTip() {
		return "Type: Log\nLevel: " + (logLevel != null ? logLevel : "");
	}
}
