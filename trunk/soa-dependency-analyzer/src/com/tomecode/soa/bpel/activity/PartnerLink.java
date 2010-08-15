package com.tomecode.soa.bpel.activity;

/**
 * partner link in BPEL process
 * 
 * @author Tomas Frastia
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
	 * @param type
	 * @param name
	 * @param partnerLinkType
	 * @param myRole
	 * @param partnerRole
	 */
	public PartnerLink(ActivityType type, String name, String partnerLinkType, String myRole, String partnerRole) {
		super(type, name);
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;

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

	public final String toString() {
		return name;
	}

}
