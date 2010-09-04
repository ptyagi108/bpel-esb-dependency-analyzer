package com.tomecode.soa.ora.osb10g.activity;

/**
 * element: stage
 * 
 * @author Tomas Frastia
 * 
 */
public final class Stage extends OsbActivity {

	public Stage(String name) {
		super(name);
	}

	public final String toString() {
		return name == null ? "stage" : "stage - " + name;
	}

}
