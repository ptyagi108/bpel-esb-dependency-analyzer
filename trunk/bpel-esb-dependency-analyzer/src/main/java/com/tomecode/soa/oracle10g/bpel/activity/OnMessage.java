package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsagePartnerLinkResult;
import com.tomecode.soa.bpel.dependency.analyzer.utils.FindUsageVariableResult;

/**
 * OnMessage actvity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class OnMessage extends Activity {

	private String variable;
	private String partnerLink;
	private String operation;

	private String headerVariable;

	/**
	 * * Constructor
	 * 
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 * @param headerVariable
	 */
	public OnMessage(String variable, String partnerLink, String operation, String headerVariable) {
		super(ActivityType.ONMESSAGE, null);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.headerVariable = headerVariable;
	}

	public final String getHeaderVariable() {
		return headerVariable;
	}

	public final String getVariable() {
		return variable;
	}

	public final String getPartnerLink() {
		return partnerLink;
	}

	public final String getOperation() {
		return operation;
	}

	public final void findVariable(FindUsageVariableResult findUsageVariableResult) {
		if (variable != null && findUsageVariableResult.getVariable().toString().equals(variable)) {
			findUsageVariableResult.addUsage(this);
		} else if (headerVariable != null && findUsageVariableResult.getVariable().toString().equals(headerVariable)) {
			findUsageVariableResult.addUsage(this);
		}
	}

	public final void findPartnerLink(FindUsagePartnerLinkResult usage) {
		if (partnerLink != null && usage.getPartnerLink().getName().equals(partnerLink)) {
			usage.addUsage(this);
		}
	}

}
