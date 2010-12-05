package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * Receive activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Receive extends Activity {

	private static final long serialVersionUID = 1068491215667638987L;

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
		super(ActivityType.ORACLE_10G_RECEIVE, name);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_RECEIVE;
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

	@Override
	public final String getToolTip() {
		return "Activity: Receive\nName: " + name + "\nPartnerLink: " + partnerLink + "\nOperation: " + operation + "\nVariable: " + variable;
	}

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
			if (activity instanceof Receive) {
				Receive receive = (Receive) activity;
				if (partnerLink != null && receive.getPartnerLink() != null) {
					if (partnerLink.equals(receive.getPartnerLink())) {
						if (operation != null && receive.getOperation() != null) {
							return operation.equals(receive.getOperation());
						}
					}
				}
				if (operation != null && receive.getOperation() != null) {
					return operation.equals(receive.getOperation());
				}
			}
		}
		return false;
	}
}
