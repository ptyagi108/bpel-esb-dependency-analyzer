package com.tomecode.soa.ora.osb10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * element: routingTable
 * 
 * @author Tomas Frastia
 * 
 */
public final class RoutingTable extends OsbActivity {

	public final String toString() {
		return "Routing Table";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_ROUTING_TABLE;
	}

}
