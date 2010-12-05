package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * element: receive
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 * 
 */
public final class Receive extends OsbActivity {

	private String partnerLink;

	private String variable;
	private String operation;

	public Receive(String partnerLink, String operation, String variable) {
		this.partnerLink = partnerLink;
		this.variable = variable;
		this.operation = operation;
	}

	/**
	 * @return the partnerLink
	 */
	public final String getPartnerLink() {
		return partnerLink;
	}

	/**
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	public final String toString() {
		return "Receive";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_RECEIVE;
	}

	@Override
	public final String getToolTip() {
		return "Activity: Receive\nName: " + name + "\nPartnerLink: " + partnerLink + "\nOperation: " + operation + "\nVariable: " + variable;
	}

}
