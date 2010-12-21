package com.tomecode.soa.ora.osb10g.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.ora.osb10g.activity.splitjoin.ErrorHandlers;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Basic OSB 10g activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public abstract class OsbActivity implements ImageFace {

	/**
	 * list of activities
	 */
	protected final List<OsbActivity> activities;

	/**
	 * activity name
	 */
	protected String name;

	/**
	 * parent activity
	 */
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

	protected final void setParent(OsbActivity osbActivity) {
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

	public abstract Image getImage();

	public String toString() {
		return name;
	}

	public final OsbActivity getErroHandlers() {
		for (OsbActivity activity : activities) {
			if (activity instanceof ErrorHandlers) {
				return activity;
			}
		}
		return null;
	}

	public String getToolTip() {
		if (name != null) {
			return "Name: " + name;
		}
		return null;
	}
}
