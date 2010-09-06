package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: case parent: routingTable
 * 
 * @author Tomas Frastia
 * 
 */
public final class RoutingTableCase extends OsbActivity {

	public final String toString() {
		return "Case";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_CASE;
	}
}
