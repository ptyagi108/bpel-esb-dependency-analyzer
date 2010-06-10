package com.tomecode.soa.dependency.analyzer.gui.cellview;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;

/**
 * self edge for self dependencies
 * 
 * @author Tomas Frastia
 * 
 */
public final class SelfDefaultEdge extends DefaultEdge {

	private static final long serialVersionUID = 7948521909280850946L;

	public SelfDefaultEdge(Object paramObject, AttributeMap paramAttributeMap) {
		super(paramObject, paramAttributeMap);
	}
}
