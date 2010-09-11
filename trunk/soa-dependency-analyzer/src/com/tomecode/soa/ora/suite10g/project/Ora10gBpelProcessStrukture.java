package com.tomecode.soa.ora.suite10g.project;

import com.tomecode.soa.bpel.activity.Activity;

/**
 * 
 * View the BPEL process tree
 * 
 * 
 * @author Tomas Frastia
 * 
 */
public final class Ora10gBpelProcessStrukture extends Activity {

	private static final long serialVersionUID = -8082029461398462336L;

	private BpelProject bpelProject;

	/**
	 * Constructor
	 */
	public Ora10gBpelProcessStrukture() {
		// super();
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param bpelProcess
	 */
	public Ora10gBpelProcessStrukture(BpelProject bpelProcess) {
		this();
		this.bpelProject = bpelProcess;
	}

	public void addActivity(Activity activity) {
		super.addActivity(activity);// childs.add(activity);
	}

	public final String toString() {
		return bpelProject.toString();
	}

	public final BpelProject getProject() {
		return bpelProject;
	}

	// public final FindUsagePartnerLinkResult findUsage(PartnerLink
	// partnerLink) {
	// FindUsagePartnerLinkResult findUsagePartnerLinkResult = new
	// FindUsagePartnerLinkResult(partnerLink);
	// for (Activity activity : childs) {
	// activity.findUsage(findUsagePartnerLinkResult);
	// }
	// return findUsagePartnerLinkResult;
	// }
	//
	// public final FindUsageVariableResult findUsage(Variable variable) {
	// FindUsageVariableResult findUsageVariableResult = new
	// FindUsageVariableResult(variable);
	// for (Activity activity : childs) {
	// activity.findUsage(findUsageVariableResult);
	// }
	// return findUsageVariableResult;
	// }
}
