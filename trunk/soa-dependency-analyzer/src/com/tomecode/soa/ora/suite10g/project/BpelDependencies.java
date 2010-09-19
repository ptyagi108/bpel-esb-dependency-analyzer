package com.tomecode.soa.ora.suite10g.project;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * 
 * object for show dependence tree without activity
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelDependencies {

	private Activity activity;

	private final List<BpelDependencies> dependencies;

	public BpelDependencies() {
		this.dependencies = new ArrayList<BpelDependencies>();
	}

}
