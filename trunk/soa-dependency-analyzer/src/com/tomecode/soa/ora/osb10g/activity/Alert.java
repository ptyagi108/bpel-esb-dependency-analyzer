package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: alert
 * 
 * @author Tomas Frastia
 * 
 */
public final class Alert extends OsbActivity {

	private String severity;

	public Alert(String severity) {
		this.severity = severity;
	}

	public final String getSeverity() {
		return severity;
	}

}
