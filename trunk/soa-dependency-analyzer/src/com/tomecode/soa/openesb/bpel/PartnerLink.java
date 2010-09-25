package com.tomecode.soa.openesb.bpel;

import java.util.ArrayList;
import java.util.List;

import com.tomecode.soa.wsdl.Wsdl;

/**
 * Partner link in Open ESB - BPEL Process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class PartnerLink {

	private String name;

	private String partnerLinkType;

	private String myRole;

	private String partnerRole;

	private String initializePartnerRole;

	private Wsdl wsdl;

	private List<OpenEsbBpelProcess> dependenciesProcesses;

	public PartnerLink(String name, String initializePartnerRole, String partnerLinkType, String myRole, String partnerRole) {
		this.name = name;
		this.initializePartnerRole = initializePartnerRole;
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;
		this.dependenciesProcesses = new ArrayList<OpenEsbBpelProcess>();
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
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

}
