package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.bpel.dependency.analyzer.usages.FindUsagePartnerLinkResult;

/**
 * 
 * 
 * Receive activity
 * 
 * @author Tomas Frastia
 * 
 */
public final class Receive extends Activity {

	private String variable;

	private String partnerLink;

	private String operation;

	/**
	 * * Constructor
	 * 
	 * @param name
	 *            activity name
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 */
	public Receive(String name, String variable, String partnerLink, String operation) {
		super(ActivityType.RECEIVE, name);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
	}

	public final String getOperation() {
		return operation;
	}

	public final String getPartnerLink() {
		return partnerLink;
	}

	public final String getVariable() {
		return variable;
	}

	/**
	 * find partnerLink in activity
	 */
	public final void findPartnerLink(FindUsagePartnerLinkResult usage) {
		if (partnerLink != null && usage.getPartnerLink().getName().equals(partnerLink)) {
			usage.addUsage(this);
		}
	}
}
