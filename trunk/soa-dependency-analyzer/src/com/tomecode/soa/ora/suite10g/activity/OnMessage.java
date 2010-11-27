package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * OnMessage activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OnMessage extends Activity {

	private static final long serialVersionUID = 1604503742786050538L;

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
		super(ActivityType.ORACLE_10G_ONMESSAGE, null);
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

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_ONMESSAGE;
	}

	// /**
	// * find variable in activity
	// */
	// public final void findVariable(FindUsageVariableResult
	// findUsageVariableResult) {
	// if (variable != null &&
	// findUsageVariableResult.getVariable().toString().equals(variable)) {
	// findUsageVariableResult.addUsage(this);
	// } else if (headerVariable != null &&
	// findUsageVariableResult.getVariable().toString().equals(headerVariable))
	// {
	// findUsageVariableResult.addUsage(this);
	// }
	// }
	//
	// /**
	// * find partnerLink in activity
	// */
	// public final void findPartnerLink(FindUsagePartnerLinkResult usage) {
	// if (partnerLink != null &&
	// usage.getPartnerLink().getName().equals(partnerLink)) {
	// usage.addUsage(this);
	// }
	// }

	public final boolean compare(Activity activity) {
		if (super.compare(activity)) {
			if (activity instanceof OnMessage) {
				OnMessage onMessage = (OnMessage) activity;
				if (partnerLink != null && onMessage.getPartnerLink() != null) {
					if (partnerLink.equals(onMessage.getPartnerLink())) {
						if (operation != null && onMessage.getOperation() != null) {
							return operation.equals(onMessage.getOperation());
						}
					}
				}
				if (operation != null && onMessage.getOperation() != null) {
					return operation.equals(onMessage.getOperation());
				}
			}
		}
		return false;
	}
}
