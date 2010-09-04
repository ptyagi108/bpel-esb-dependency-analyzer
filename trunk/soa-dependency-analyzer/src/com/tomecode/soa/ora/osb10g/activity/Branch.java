package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: branch
 * 
 * @author Tomas Frastia
 * 
 */
public class Branch extends OsbActivity {

	public Branch(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "branch" : "branch - " + name;
	}
}
