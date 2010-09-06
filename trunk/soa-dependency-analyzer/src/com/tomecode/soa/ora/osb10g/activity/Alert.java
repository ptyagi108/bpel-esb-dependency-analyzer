package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

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

	public final String toString() {
		return "Alert";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ALERT;
	}

}
