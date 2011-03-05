package com.tomecode.soa.project;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Project type is type of BPEL or ESB
 * 
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
	 * Open ESB - BPEL project
	 */
	OPENESB_BPEL("Open ESB - BPEL"),

	/**
	 * Open ESB - ESB project
	 */
	OPENESB_ESB("Open ESB - ESB"),

	/**
	 * Oracle Service Bus 10g
	 */
	ORACLE_SERVICE_BUS_1OG("Oracle Service Bus 10g"),

	/**
	 * unknown project
	 */
	UNKNOWN("Unknown");

	private final String title;

	/**
	 * Constructor
	 * 
	 * @param title
	 */
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
