package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: report
 * 
 * @author Tomas Frastia
 * 
 */
public final class Report extends OsbActivity {

	public final String toString() {
		return "Report";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_REPORT;
	}
}
