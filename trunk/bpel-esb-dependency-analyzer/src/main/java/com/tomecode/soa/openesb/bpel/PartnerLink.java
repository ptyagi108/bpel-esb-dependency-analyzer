package com.tomecode.soa.openesb.bpel;

/**
 * Partner link in Open ESB - BPEL Process
 * 
 * @author Frastia Tomas
 * 
 */
public final class PartnerLink {

	private String name;

	private String partnerLinkType;

	private String myRole;

	private String partnerRole;

	private String initializePartnerRole;

	public PartnerLink(String name, String initializePartnerRole, String partnerLinkType, String myRole, String partnerRole) {
		this.name = name;
		this.initializePartnerRole = initializePartnerRole;
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;
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

}
