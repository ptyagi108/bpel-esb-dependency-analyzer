package com.tomecode.soa.ora.suite10g.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * partner link in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 * 
 */
public final class PartnerLink extends Activity {

	private static final long serialVersionUID = -6888516272264499697L;

	private String partnerLinkType;

	private String myRole;

	private String partnerRole;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param partnerLinkType
	 * @param myRole
	 * @param partnerRole
	 */
	public PartnerLink(String name, String partnerLinkType, String myRole, String partnerRole) {
		super(ActivityType.ORACLE_10G_PARTNERLINK, name);
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;

	}

	public final Image getImage() {
		return ImageFactory.ORACLE_10G_PARTNERLINK;
	}

	public final String getPartnerLinkType() {
		return partnerLinkType;
	}

	public final String getMyRole() {
		return myRole;
	}

	public final String getPartnerRole() {
		return partnerRole;
	}

	@Override
	public final String getToolTip() {
		return "PartnerLink: " + name + "\nMyRole: " + (myRole != null ? myRole : "") + "\nPartnerRole: " + (partnerRole != null ? partnerLinkType : "");
	}

	public final String toString() {
		return name;
	}

}
