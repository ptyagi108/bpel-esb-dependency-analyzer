package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: branch-node type: condition
 * 
 * @author Tomas Frastia
 * 
 */
public final class BranchNodeCondition extends OsbActivity {

	public BranchNodeCondition(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "condition" : "condition - " + name;
	}
}
