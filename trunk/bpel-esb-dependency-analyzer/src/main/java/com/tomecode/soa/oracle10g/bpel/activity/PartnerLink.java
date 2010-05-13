package com.tomecode.soa.oracle10g.bpel.activity;

import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;

/**
 * partner link in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class PartnerLink extends Activity {

	private String partnerLinkType;

	private String myRole;

	private String partnerRole;

	private BpelProcessStrukture strukture;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param partnerLinkType
	 * @param myRole
	 * @param partnerRole
	 * @param strukture
	 */
	public PartnerLink(String name, String partnerLinkType, String myRole, String partnerRole, BpelProcessStrukture strukture) {
		super(ActivtyType.PARTNERLINK, name);
		this.partnerLinkType = partnerLinkType;
		this.myRole = myRole;
		this.partnerRole = partnerRole;
		this.strukture = strukture;
	}

	public final BpelProcessStrukture getStrukture() {
		return strukture;
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
