package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: branch-node type: operation
 * 
 * @author Tomas Frastia
 * 
 */
public final class BranchNodeOperation extends OsbActivity {

	public BranchNodeOperation(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "operation" : "operation - " + name;
	}
}
