package com.tomecode.soa.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * BPEL Activities
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public class Activity implements ImageFace {

	private static final long serialVersionUID = -6772380545433052884L;

	private List<Activity> activities;
	/**
	 * activity type
	 */
	private ActivityType activtyType;

	/**
	 * parent activity
	 */
	private Activity parent;

	protected String name;

	/**
	 * Constructor
	 */
	public Activity() {
		activities = new ArrayList<Activity>();
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            {@link ActivityType}
	 * @param name
	 *            activity name
	 */
	public Activity(ActivityType type, String name) {
		this();
		this.activtyType = type;
		this.name = name;
	}

	/**
	 * add activity and set parent
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activity.setParent(this);
		activities.add(activity);
	}

	public final ActivityType getActivtyType() {
		return activtyType;
	}

	private void setParent(Activity parent) {
		this.parent = parent;
	}

	public final Activity getParent() {
		return parent;
	}

	public final String getName() {
		return name;
	}

	public String toString() {
		if (activtyType != null) {
			return activtyType.getName() + (name == null ? "" : (" - " + name));
		}
		return name == null ? "" : name;
	}

	// /**
	// * find usage for {@link Variable}
	// *
	// * @param findUsageVariableResult
	// */
	// public final void findUsage(FindUsageVariableResult
	// findUsageVariableResult) {
	// for (Activity activity : this.childs) {
	// if (activity.getActivtyType() != null) {
	// if (activity.getActivtyType().isContainsVariable()) {
	// findVariableInActivty(findUsageVariableResult, activity);
	// }
	// }
	//
	// activity.findVariable(findUsageVariableResult);
	// activity.findUsage(findUsageVariableResult);
	// }
	// }
	//
	// public void findVariable(FindUsageVariableResult findUsageVariableResult)
	// {
	//
	// }
	//
	// public void findPartnerLink(FindUsagePartnerLinkResult
	// findUsagePartnerLinkResult) {
	//
	// }
	//
	// /**
	// * find usage for {@link PartnerLink}
	// *
	// * @param findUsagePartnerLinkResult
	// */
	// public final void findUsage(FindUsagePartnerLinkResult
	// findUsagePartnerLinkResult) {
	// for (Activity activity : childs) {
	//
	// activity.findPartnerLink(findUsagePartnerLinkResult);
	// activity.findUsage(findUsagePartnerLinkResult);
	// }
	// }
	//
	// /**
	// * find usage for variable in bpel actvities
	// *
	// * @param findUsageVariableResult
	// * @param activity
	// */
	// private final void findVariableInActivty(FindUsageVariableResult
	// findUsageVariableResult, Activity activity) {
	// if (activity.getActivtyType() == ActivityType.RECEIVE) {
	// if (findUsageVariableResult.getVariable().getName().equals(((Receive)
	// activity).getVariable())) {
	// findUsageVariableResult.addUsage(activity);
	// }
	// } else if (activity.getActivtyType() == ActivityType.INVOKE) {
	// Invoke invoke = (Invoke) activity;
	// if
	// (findUsageVariableResult.getVariable().getName().equals(invoke.getInputVariable())
	// ||
	// findUsageVariableResult.getVariable().getName().equals(invoke.getOutputVariable()))
	// {
	// findUsageVariableResult.addUsage(activity);
	// }
	// } else if (activity.getActivtyType() == ActivityType.REPLY) {
	// if (findUsageVariableResult.getVariable().getName().equals(((Reply)
	// activity).getVariable())) {
	// findUsageVariableResult.addUsage(activity);
	// }
	// }
	// }

	public final List<Activity> getActivities() {
		return activities;
	}

	public boolean compare(Activity activity) {
		return toString().equals(activity.toString());
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getToolTip() {
		return name;
	}

	// @Override
	// public abstract Image getImage();
}
