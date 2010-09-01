package com.tomecode.soa.project;

/**
 * Project type is type of BPEL or ESB
 * 
 * 
 * @author Frastia Tomas
 * 
 */
public enum ProjectType {

	/**
	 * ORACLE 10g BPEL process project
	 */
	ORACLE10G_BPEL("Oracle SOA Suit 10g - BPEL"),
	/**
	 * ORACLE 10g ESB service project
	 */
	ORACLE10G_ESB("Oracle SOA Suit 10g - ESB"),

	/**
	 * Open ESB - ESB and BPEL project
	 */
	OPEN_ESB_BPEL("Open ESB and BPEL"),

	ORACLE_SERVICE_BUS_1OG("Oracle Service Bus 10g"),
	/**
	 * unknown project
	 */
	UNKNOWN("Unknown");

	private final String title;

	private ProjectType(String title) {
		this.title = title;
	}

	public final String getTitle() {
		return title;
	}

	public final String toString() {
		return title;
	}

}
