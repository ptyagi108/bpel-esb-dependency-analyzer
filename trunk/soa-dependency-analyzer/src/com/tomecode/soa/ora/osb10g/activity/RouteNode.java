package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: route-node
 * 
 * @author Tomas Frastia
 * 
 */
public final class RouteNode extends OsbActivity {

	public RouteNode(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "Route Node" : "Route Node - " + name;
	}
}
