package com.tomecode.soa.ora.osb10g.activity;

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

}
