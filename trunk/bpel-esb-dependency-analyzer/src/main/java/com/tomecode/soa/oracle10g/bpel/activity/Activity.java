package com.tomecode.soa.oracle10g.bpel.activity;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * 
 * BPEL Activity
 * 
 * @author Tomas Frastia
 * 
 */
public class Activity implements TreeNode {

	/**
	 * actvity type
	 */
	private ActivityType activtyType;

	/**
	 * child activities
	 */
	private final Vector<Activity> activities;

	private Activity parent;

	private String type;

	protected String name;

	/**
	 * Constructor
	 */
	public Activity() {
		this.activities = new Vector<Activity>();
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 */
	public Activity(String type) {
		this();
		this.type = type;
		this.activtyType = ActivityType.parseActivtyType(type);
	}

	public Activity(String type, String name) {
		this(type);
		this.name = name;
	}

	public Activity(ActivityType type, String name) {
		this();
		this.activtyType = type;
		this.type = activtyType.toString();
		this.name = name;
	}

	public final void addActivity(Activity activity) {
		activity.setParent(this);
		this.activities.add(activity);
	}

	public final ActivityType getActivtyType() {
		return activtyType;
	}

	private void setParent(Activity parent) {
		this.parent = parent;
	}

	public final String getName() {
		return name;
	}

	@Override
	public Enumeration<?> children() {
		return activities.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return !activities.isEmpty();
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return activities.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return activities.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return activities.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return this.parent;
	}

	public Activity getParentActivity() {
		return this.parent;
	}

	@Override
	public boolean isLeaf() {
		return activities.isEmpty();
	}

	public String toString() {
		return type + (name == null ? "" : (":" + name));
	}

	/**
	 * find usage for {@link Variable}
	 * 
	 * @param findUsageVariableResult
	 */
	public final void findUsage(FindUsageVariableResult findUsageVariableResult) {
		for (Activity activity : activities) {
			if (activity.getActivtyType() != null) {
				if (activity.getActivtyType().isContainsVariable()) {
					findVariableInActivty(findUsageVariableResult, activity);
				} else {
					activity.findUsage(findUsageVariableResult);
				}
			} else {
				activity.findUsage(findUsageVariableResult);
			}
		}
	}

	/**
	 * find usage for {@link PartnerLink}
	 * 
	 * @param findUsagePartnerLinkResult
	 */
	public final void findUsage(FindUsagePartnerLinkResult findUsagePartnerLinkResult) {
		for (Activity activity : activities) {
			if (activity.getActivtyType() != null) {
				if (activity.getActivtyType().isContainsVariable()) {
					findPartnerLinkInActivty(findUsagePartnerLinkResult, activity);
				} else {
					activity.findUsage(findUsagePartnerLinkResult);
				}
			} else {
				activity.findUsage(findUsagePartnerLinkResult);
			}
		}
	}

	/**
	 * find usage for partnerLink in activities
	 * 
	 * @param findUsagePartnerLinkResult
	 * @param activity
	 */
	private final void findPartnerLinkInActivty(FindUsagePartnerLinkResult findUsagePartnerLinkResult, Activity activity) {
		if (activity.getActivtyType() == ActivityType.RECEIVE) {
			if (findUsagePartnerLinkResult.getPartnerLink().getName().equals(((Receive) activity).getPartnerLink())) {
				findUsagePartnerLinkResult.addUsage(activity);
			}
		} else if (activity.getActivtyType() == ActivityType.INVOKE) {
			Invoke invoke = (Invoke) activity;
			if (findUsagePartnerLinkResult.getPartnerLink().getName().equals(invoke.getPartnerLink())) {
				findUsagePartnerLinkResult.addUsage(activity);
			}
		} else if (activity.getActivtyType() == ActivityType.REPLY) {
			if (findUsagePartnerLinkResult.getPartnerLink().getName().equals(((Reply) activity).getPartnerLink())) {
				findUsagePartnerLinkResult.addUsage(activity);
			}
		}
	}

	/**
	 * find usage for variable in bpel actvities
	 * 
	 * @param findUsageVariableResult
	 * @param activity
	 */
	private final void findVariableInActivty(FindUsageVariableResult findUsageVariableResult, Activity activity) {
		if (activity.getActivtyType() == ActivityType.RECEIVE) {
			if (findUsageVariableResult.getVariable().getName().equals(((Receive) activity).getVariable())) {
				findUsageVariableResult.addUsage(activity);
			}
		} else if (activity.getActivtyType() == ActivityType.INVOKE) {
			Invoke invoke = (Invoke) activity;
			if (findUsageVariableResult.getVariable().getName().equals(invoke.getInputVariable()) || findUsageVariableResult.getVariable().getName().equals(invoke.getOutputVariable())) {
				findUsageVariableResult.addUsage(activity);
			}
		} else if (activity.getActivtyType() == ActivityType.REPLY) {
			if (findUsageVariableResult.getVariable().getName().equals(((Reply) activity).getVariable())) {
				findUsageVariableResult.addUsage(activity);
			}
		}
	}
}
