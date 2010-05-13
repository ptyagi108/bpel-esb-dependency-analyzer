package com.tomecode.soa.oracle10g.bpel.activity;

import javax.swing.ImageIcon;

import com.tomecode.soa.bpel.dependency.analyzer.icons.IconFactory;

/***
 * 
 * Bpel activity types
 * 
 * @author Tomas Frastia
 * 
 */
public enum ActivityType {

	SCOPE("scope", IconFactory.SCOPE), SEQUENCE("sequence", IconFactory.SEQUENCE), ASSIGN("assign", IconFactory.ASSIGN), EMPTY("empty", IconFactory.EMPTY), RECEIVE("receive", IconFactory.RECEIVE, true), INVOKE("invoke", IconFactory.INVOKE, true), REPLY("reply",
			IconFactory.REPLY, true), SWITCH("switch", IconFactory.SWITCH), CATCH("catch", IconFactory.CATCH, true), CATCHALL("catchAll", IconFactory.CATCHALL), ONALARM("onAlarm", IconFactory.ONALARM), ONMESSAGE("onMessage", IconFactory.ONMESSAGE, true), COMPENSATIONHANDLER(
			"compensationHandler", IconFactory.COMPENSATIONHANDLER), JAVA_EMBEDDING("exec", IconFactory.BPELX_EXEC), PICK("pick", IconFactory.PICK), FLOW("flow", IconFactory.FLOW), FLOWN("flowN", IconFactory.FLOWN), COMPENSATE("compensate", IconFactory.COMPENSTATE), TERMINATE(
			"terminate", IconFactory.TERMINATE), THROW("throw", IconFactory.THROW, true), PARTNERLINK("partnerLink", IconFactory.PARTNERLINK), VARIABLE("variable", IconFactory.VARIABLE), TRANSFORMATE("transformation", null, true), EMAIL("email", IconFactory.EMAIL), FAX("fax",
			IconFactory.FAX), SMS("sms", IconFactory.SMS), VOICE("voice", IconFactory.VOICE), CASE("case", IconFactory.SWITCH), OTHERWISE("otherwise", IconFactory.SWITCH), HUMANTASK("workflow", IconFactory.HUMANTASK);

	private final String name;
	private final ImageIcon imageIcon;

	private final boolean containsVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name in *.bpel file
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, ImageIcon imageIcon) {
		this(name, imageIcon, false);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            xml element name
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, ImageIcon imageIcon, boolean containsVariable) {
		this.name = name;
		this.imageIcon = imageIcon;
		this.containsVariable = containsVariable;
	}

	public final String getName() {
		return name;
	}

	public final ImageIcon getImageIcon() {
		return imageIcon;
	}

	public final boolean isContainsVariable() {
		return containsVariable;
	}

	/**
	 * parse string to {@link ActivityType}
	 * 
	 * @param name
	 * @return
	 */
	public static final ActivityType parseActivtyType(String name) {
		for (ActivityType activtyType : values()) {
			if (activtyType.getName().equals(name)) {
				return activtyType;
			}
		}
		return null;
	}
}
