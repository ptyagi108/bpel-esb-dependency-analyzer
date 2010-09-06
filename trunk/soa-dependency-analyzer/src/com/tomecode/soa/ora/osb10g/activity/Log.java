package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: log
 * 
 * @author Tomas Frastia
 * 
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
}
