package com.tomecode.soa.openesb.bpel.activity;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.openesb.bpel.OpenEsbBpelProcess;
import com.tomecode.soa.wsdl.Wsdl;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Partner link in Open ESB - BPEL Process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class PartnerLink extends Activity {

	private String partnerLinkType;

	private String myRole;

	private String partnerRole;

	private String initializePartnerRole;

	private Wsdl wsdl;

	private List<OpenEsbBpelProcess> dependenciesProcesses;

	/**
	 * 
	 * @param name
	 * @param initializePartnerRole
	 * @param partnerLinkType
	 * @param myRole
	 * @param partnerRole
	 */
	public PartnerLink(String name, String initializePartnerRole, String partnerLinkType, String myRole, String partnerRole) {
		super(ActivityType.OPEN_ESB_BPEL_PARTNERLINK, name);
		this.initializePartnerRole = initializePartnerRole;
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;
		this.dependenciesProcesses = new ArrayList<OpenEsbBpelProcess>();
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_PARTNERLINK;
	}

	/**
	 * @return the initializePartnerRole
	 */
	public final String getInitializePartnerRole() {
		return initializePartnerRole;
	}

	/**
	 * @return the partnerLinkType
	 */
	public final String getPartnerLinkType() {
		return partnerLinkType;
	}

	/**
	 * @return the myRole
	 */
	public final String getMyRole() {
		return myRole;
	}

	/**
	 * @return the partnerRole
	 */
	public final String getPartnerRole() {
		return partnerRole;
	}

	public final void setWsdl(Wsdl wsdl) {
		this.wsdl = wsdl;
	}

	public final Wsdl getWsdl() {
		return wsdl;
	}

	public final void addDependency(OpenEsbBpelProcess depProcess) {
		if (!dependenciesProcesses.contains(depProcess)) {
			dependenciesProcesses.add(depProcess);
		}
	}

	/**
	 * @return the dependenciesProcesses
	 */
	public final List<OpenEsbBpelProcess> getDependenciesProcesses() {
		return dependenciesProcesses;
	}

	public final String toString() {
		return name == null ? partnerLinkType : name;
	}

	@Override
	public final String getToolTip() {
		return "PartnerLink: " + name + "\nWSDL: " + (wsdl != null ? wsdl.getName() : "");
	}

}
