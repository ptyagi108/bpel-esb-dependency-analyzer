package com.tomecode.soa.ora.osb10g.activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic activity
 * 
 * @author Tomas Frastia
 * 
 */
public abstract class OsbActivity {

	protected final List<OsbActivity> activities;

	protected String name;
	protected OsbActivity parent;

	public OsbActivity() {
		activities = new ArrayList<OsbActivity>();
	}

	public OsbActivity(String name) {
		this();
		this.name = name;
	}

	public final void addActivity(OsbActivity osbActivity) {
		osbActivity.setParent(this);
		activities.add(osbActivity);
	}

	private final void setParent(OsbActivity osbActivity) {
		this.parent = osbActivity;
	}

	public final OsbActivity getParent() {
		return parent;
	}

	public final List<OsbActivity> getActivities() {
		return activities;
	}

	public final String getName() {
		return name;
	}

}
