package com.tomecode.soa.oracle10g.bpel;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.PartnerLink;
import com.tomecode.soa.bpel.activity.Variable;
import com.tomecode.soa.dependency.analyzer.gui.tree.node.BasicNode;
import com.tomecode.soa.dependency.analyzer.usages.FindUsagePartnerLinkResult;
import com.tomecode.soa.dependency.analyzer.usages.FindUsageVariableResult;

/**
 * 
 * View the BPEL process tree
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelProcessStrukture extends BasicNode<Activity> {

	private static final long serialVersionUID = -8082029461398462336L;

	private BpelProject bpelProject;

	/**
	 * Constructor
	 */
	public BpelProcessStrukture() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param bpelProcess
	 */
	public BpelProcessStrukture(BpelProject bpelProcess) {
		this();
		this.bpelProject = bpelProcess;
	}

	public final void addActivity(Activity activity) {
		childs.add(activity);
	}

	public final String toString() {
		return bpelProject.toString();
	}

	public final FindUsagePartnerLinkResult findUsage(PartnerLink partnerLink) {
		FindUsagePartnerLinkResult findUsagePartnerLinkResult = new FindUsagePartnerLinkResult(partnerLink);
		for (Activity activity : childs) {
			activity.findUsage(findUsagePartnerLinkResult);
		}
		return findUsagePartnerLinkResult;
	}

	public final FindUsageVariableResult findUsage(Variable variable) {
		FindUsageVariableResult findUsageVariableResult = new FindUsageVariableResult(variable);
		for (Activity activity : childs) {
			activity.findUsage(findUsageVariableResult);
		}
		return findUsageVariableResult;
	}
}
