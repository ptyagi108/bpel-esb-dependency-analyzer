package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: transport-headers
 * 
 * @author Tomas Frastia
 * 
 */
public final class TransportHeaders extends OsbActivity {

	public final String toString() {
		return "Transport Headers";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_TRANSPORTHEADERS;
	}
}
