package com.tomecode.soa.dependency.analyzer.tree.node;


/**
 * simple empty node
 * 
 * @author Tomas Frastia
 * 
 */
public final class EmptyNode {

	private final Object[] objects;

	public EmptyNode() {
		objects = new Object[1];
	}

	public EmptyNode(Object object) {
		this();
		set(object);
	}

	public final void set(Object object) {
		objects[0] = object;
	}

	public final boolean hasChildren() {
		return objects.length != 0;
	}

	public final Object[] getChildren() {
		return objects;
	}
}
