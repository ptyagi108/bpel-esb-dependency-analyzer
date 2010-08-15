package com.tomecode.soa.dependency.analyzer.tree.node;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Tomas Frastia
 * 
 */
public final class WorkspaceRootNode {

	private final List<Object> objects;

	public WorkspaceRootNode() {
		objects = new ArrayList<Object>();
	}

	public final void add(Object object) {
		objects.add(object);
	}

	public final boolean hasChildren() {
		return !objects.isEmpty();
	}

	public final Object[] getChildren() {
		return objects.toArray();
	}
}
