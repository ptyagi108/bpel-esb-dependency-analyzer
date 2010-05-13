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
public enum ActivtyType {

	SCOPE("scope", IconFactory.SCOPE), SEQUENCE("sequence", IconFactory.SEQUENCE), ASSIGN("assign", IconFactory.ASSIGN), EMPTY("empty", IconFactory.EMPTY), RECEIVE("receive", IconFactory.RECEIVE, true), INVOKE("invoke", IconFactory.INVOKE, true), REPLY("reply",
			IconFactory.REPLY, true), SWITCH("switch", IconFactory.SWITCH), CATCH("catch", IconFactory.CATCH, true), CATCHALL("catchAll", IconFactory.CATCHALL), ONALARM("onAlarm", IconFactory.ONALARM), ONMESSAGE("onMessage", IconFactory.ONMESSAGE, true), COMPENSATIONHANDLER(
			"compensationHandler", IconFactory.COMPENSATIONHANDLER), JAVA_EMBEDDING("exec", IconFactory.BPELX_EXEC), PICK("pick", IconFactory.PICK), FLOW("flow", IconFactory.FLOW), FLOWN("flowN", IconFactory.FLOWN), COMPENSATE("compensate", IconFactory.COMPENSTATE), TERMINATE(
			"terminate", IconFactory.TERMINATE), THROW("throw", IconFactory.THROW, true), PARTNERLINK("partnerLink", null), VARIABLE("variable", null), TRANSFORMATE("transformation", null, true);

	private final String name;
	private final ImageIcon imageIcon;

	private final boolean containsVariable;

	private ActivtyType(String name, ImageIcon imageIcon) {
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
	private ActivtyType(String name, ImageIcon imageIcon, boolean containsVariable) {
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
	 * parse string to {@link ActivtyType}
	 * 
	 * @param name
	 * @return
	 */
	public static final ActivtyType parseActivtyType(String name) {
		for (ActivtyType activtyType : values()) {
			if (activtyType.getName().equals(name)) {
				return activtyType;
			}
		}
		return null;
	}
}
