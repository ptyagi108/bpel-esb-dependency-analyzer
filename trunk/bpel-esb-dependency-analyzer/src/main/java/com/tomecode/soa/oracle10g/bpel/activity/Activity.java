package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.bpel.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsageVariableResult;

/**
 * 
 * BPEL Activities
 * 
 * @author Tomas Frastia
 * 
 */
public class Activity extends BasicNode<Activity> {

	private static final long serialVersionUID = -6772380545433052884L;

	/**
	 * actvity type
	 */
	private ActivityType activtyType;

	/**
	 * parent activity
	 */
	private Activity parent;

	private String type;

	protected String name;

	/**
	 * Constructor
	 */
	public Activity() {
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

	/**
	 * Constructor
	 * 
	 * @param type
	 *            {@link ActivityType}
	 * @param name
	 *            name of activity
	 */
	public Activity(String type, String name) {
		this(type);
		this.name = name;
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            {@link ActivityType}
	 * @param name
	 *            name of activity
	 */
	public Activity(ActivityType type, String name) {
		this();
		this.activtyType = type;
		this.type = activtyType.toString();
		this.name = name;
	}

	/**
	 * add activity and set parent
	 * 
	 * @param activity
	 */
	public final void addActivity(Activity activity) {
		activity.setParent(this);
		childs.add(activity);
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
		return type.toLowerCase() + (name == null ? "" : (" - " + name));
	}

	/**
	 * find usage for {@link Variable}
	 * 
	 * @param findUsageVariableResult
	 */
	public final void findUsage(FindUsageVariableResult findUsageVariableResult) {
		for (Activity activity : this.childs) {
			if (activity.getActivtyType() != null) {
				if (activity.getActivtyType().isContainsVariable()) {
					findVariableInActivty(findUsageVariableResult, activity);
				}
			}

			activity.findVariable(findUsageVariableResult);
			activity.findUsage(findUsageVariableResult);
		}
	}

	public void findVariable(FindUsageVariableResult findUsageVariableResult) {

	}

	public void findPartnerLink(FindUsagePartnerLinkResult findUsagePartnerLinkResult) {

	}

	/**
	 * find usage for {@link PartnerLink}
	 * 
	 * @param findUsagePartnerLinkResult
	 */
	public final void findUsage(FindUsagePartnerLinkResult findUsagePartnerLinkResult) {
		for (Activity activity : childs) {

			activity.findPartnerLink(findUsagePartnerLinkResult);
			activity.findUsage(findUsagePartnerLinkResult);
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
