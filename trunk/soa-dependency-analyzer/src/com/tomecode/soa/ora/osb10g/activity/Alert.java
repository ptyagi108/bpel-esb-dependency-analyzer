package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: alert
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Alert extends OsbActivity {

	private String severity;

	/**
	 * Constructor
	 * 
	 * @param severity
	 */
	public Alert(String severity) {
		this.severity = severity;
	}

	public final String getSeverity() {
		return severity;
	}

	public final String toString() {
		return "Alert";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ALERT;
	}

	public final String getToolTip() {
		return "Type: Alert\nSeverity: " + (severity != null ? severity : "");
	}

}
