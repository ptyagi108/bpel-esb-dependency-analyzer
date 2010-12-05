package com.tomecode.soa.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/***
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * activity types
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public enum ActivityType {

	PARTNERLINKS("partnerLinks", null), VARIABLES("variables", null),

	// -----------ORACLE 10G SOA SUITE
	ORACLE_10G_SCOPE("scope", ImageFactory.ORACLE_10G_SCOPE), ORACLE_10G_WAIT("wait"), ORACLE_10G_WHILE("while"), ORACLE_10G_SEQUENCE("sequence"), ASSIGN("assign"), ORACLE_10G_EMPTY("empty"), ORACLE_10G_RECEIVE(
			"receive", null, true), ORACLE_10G_INVOKE("invoke", null, true), ORACLE_10G_REPLY("reply", null, true), SWITCH("switch", ImageFactory.ORACLE_10G_SWITCH), ORACLE_10G_CATCH("catch", null,
			true), CATCHALL("catchAll"), ORACLE_10G_ONALARM("onAlarm"), ORACLE_10G_ONMESSAGE("onMessage", null, true), ORACLE_10G_COMPENSATIONHANDLER("compensationHandler"), ORACLE_10G_JAVA_EMBEDDING(
			"exec", ImageFactory.ORACLE_10G_BPELX_EXEC), ORACLE_10G_PICK("pick"), ORACLE_10G_FLOW("flow"), ORACLE_10G_FLOWN("flowN"), ORACLE_10G_COMPENSATE("compensate",
			ImageFactory.ORACLE_10G_COMPENSTATE), ORACLE_10G_TERMINATE("terminate", ImageFactory.ORACLE_10G_TERMINATE), ORACLE_10G_THROW("throw", null, true), ORACLE_10G_PARTNERLINK("partnerLink"), ORACLE_10G_VARIABLE(
			"variable", ImageFactory.ORACLE_10G_VARIABLE), ORACLE_10G_TRANSFORMATE("transformation", null, true), ORACLE_10G_EMAIL("email"), ORACLE_10G_PAGER("pager", ImageFactory.ORACLE_10G_PAGER), ORACLE_10G_FAX(
			"fax"), ORACLE_10G_SMS("sms"), ORACLE_10G_VOICE("voice"), ORACLE_10G_CASE("case"), ORACLE_10G_OTHERWISE("otherwise"), ORACLE_10G_HUMANTASK("workflow"), ORACLE_10G_FAULTHANDLERS(
			"faultHandlers"), ORACLE_10G_EVENTHANDLERS("eventHandlers"),
	// ----------OPEN ESB BPEL
	OPEN_ESB_BPEL_SEQUENCE("sequence", ImageFactory.OPEN_ESB_BPEL_SEQUENCE), OPEN_ESB_BPEL_VARIABLE("variable", ImageFactory.OPEN_ESB_BPEL_VARIABLE), OPEN_ESB_BPEL_FAULT_HANLDERS("faultHandlers",
			ImageFactory.OPEN_ESB_BPEL_FAULT_HANDLERS), OPEN_ESB_BPEL_CATCHALL("catchAll", ImageFactory.OPEN_ESB_BPEL_CATCHALL), OPEN_ESB_BPEL_EVENT_HANLDERS("eventHandlers",
			ImageFactory.OPEN_ESB_BPEL_EVENT_HANLDERS), OPEN_ESB_BPEL_TERMINATION_HANDLER("terminationHandler", ImageFactory.OPEN_ESB_BPEL_TERMINATION_HANDLER), OPEN_ESB_BPEL_SCOPE("scope",
			ImageFactory.OPEN_ESB_BPEL_SCOPE), OPEN_ESB_BPEL_FLOW("flow", ImageFactory.OPEN_ESB_BPEL_FLOW), OPEN_ESB_BPEL_COMPENSATIO_HANLDER("compensationHandler",
			ImageFactory.OPEN_ESB_BPEL_COMPENSATIO_HANLDER), OPEN_ESB_BPEL_PICK("pick", ImageFactory.OPEN_ESB_BPEL_PICK), OPEN_ESB_BPEL_PARTNERLINK("partnerLink"), OPEN_ESB_BPEL_RECEIVE("receive"), OPEN_ESB_BPEL_INVOKE(
			"invoke", ImageFactory.OPEN_ESB_BPEL_INVOKE), OPEN_ESB_BPEL_REPLY("reply"), OPEN_ESB_BPEL_CATCH("catch", ImageFactory.OPEN_ESB_BPEL_CATCH), OPEN_ESB_BPEL_THROW("throw"), OPEN_ESB_BPEL_ONEVENT(
			"onEvent"), OPEN_ESB_BPEL_ONALARM("onAlarm"), OPEN_ESB_BPEL_WAIT("wait", ImageFactory.OPEN_ESB_BPEL_WAIT), OPEN_ESB_BPEL_RETHROW("rethrow"), OPEN_ESB_BPEL_EMPTY("empty"), OPEN_ESB_BPEL_COMPENSATESCOPE(
			"compensateScope", ImageFactory.OPEN_ESB_BPEL_COMPENSATESCOPE), OPEN_ESB_BPEL_COMPENSATE("compensate", ImageFactory.OPEN_ESB_BPEL_COMPENSATE), OPEN_ESB_BPEL_WHILE("while",
			ImageFactory.OPEN_ESB_BPEL_WHILE), OPEN_ESB_BPEL_ONMESSAGE("onMessage", ImageFactory.OPEN_ESB_BPEL_ONMESSAGE), OPEN_ESB_BPEL_FOREACH("forEach", ImageFactory.OPEN_ESB_BPEL_FOREACH), OPEN_ESB_BPEL_REPEAT_UNTIL(
			"repeatUntil"), OPEN_ESB_BPEL_IF("if"), OPEN_ESB_BPEL_ELSEIF("elseif"), OPEN_ESB_BPEL_ELSE("else"), OPEN_ESB_BPEL_ASSIGN("assign", ImageFactory.OPEN_ESB_BPEL_ASSIGN), OPEN_ESB_BPEL_EXIT(
			"exit", ImageFactory.OPEN_ESB_BPEL_EXIT);

	private final String name;
	private final Image imageIcon;

	private final boolean containsVariable;

	private static final ActivityType[] ORA10G_BPEL = new ActivityType[] { ORACLE_10G_SCOPE, ORACLE_10G_WAIT, ORACLE_10G_WHILE, ORACLE_10G_SEQUENCE, ASSIGN, ORACLE_10G_EMPTY, ORACLE_10G_RECEIVE,
			ORACLE_10G_INVOKE, ORACLE_10G_REPLY, SWITCH, ORACLE_10G_CATCH, CATCHALL, ORACLE_10G_ONALARM, ORACLE_10G_ONMESSAGE, ORACLE_10G_COMPENSATIONHANDLER, ORACLE_10G_JAVA_EMBEDDING,
			ORACLE_10G_PICK, ORACLE_10G_FLOW, ORACLE_10G_FLOWN, ORACLE_10G_COMPENSATE, ORACLE_10G_TERMINATE, ORACLE_10G_THROW, ORACLE_10G_PARTNERLINK, ORACLE_10G_VARIABLE, ORACLE_10G_TRANSFORMATE,
			ORACLE_10G_EMAIL, ORACLE_10G_PAGER, ORACLE_10G_FAX, ORACLE_10G_SMS, ORACLE_10G_VOICE, ORACLE_10G_CASE, ORACLE_10G_OTHERWISE, ORACLE_10G_HUMANTASK

	};

	private static final ActivityType[] OPEN_ESB_BPEL = new ActivityType[] { OPEN_ESB_BPEL_SEQUENCE, OPEN_ESB_BPEL_VARIABLE, OPEN_ESB_BPEL_CATCHALL, OPEN_ESB_BPEL_EVENT_HANLDERS,
			OPEN_ESB_BPEL_TERMINATION_HANDLER, OPEN_ESB_BPEL_SCOPE, OPEN_ESB_BPEL_FLOW, OPEN_ESB_BPEL_COMPENSATIO_HANLDER, OPEN_ESB_BPEL_PICK, OPEN_ESB_BPEL_PARTNERLINK, OPEN_ESB_BPEL_RECEIVE,
			OPEN_ESB_BPEL_INVOKE, OPEN_ESB_BPEL_REPLY, OPEN_ESB_BPEL_CATCH, OPEN_ESB_BPEL_THROW, OPEN_ESB_BPEL_ONEVENT, OPEN_ESB_BPEL_ONALARM, OPEN_ESB_BPEL_WAIT, OPEN_ESB_BPEL_RETHROW,
			OPEN_ESB_BPEL_EMPTY, OPEN_ESB_BPEL_COMPENSATESCOPE, OPEN_ESB_BPEL_COMPENSATE, OPEN_ESB_BPEL_WHILE, OPEN_ESB_BPEL_ONMESSAGE, OPEN_ESB_BPEL_FOREACH, OPEN_ESB_BPEL_REPEAT_UNTIL,
			OPEN_ESB_BPEL_IF, OPEN_ESB_BPEL_ELSEIF, OPEN_ESB_BPEL_ELSE, OPEN_ESB_BPEL_ASSIGN, OPEN_ESB_BPEL_EXIT };

	/**
	 * Constructor
	 * 
	 * @param name
	 *            activity name in *.bpel file
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, Image imageIcon) {
		this(name, imageIcon, false);
	}

	private ActivityType(String name) {
		this(name, null, false);
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            XML element name
	 * @param imageIcon
	 *            icon
	 */
	private ActivityType(String name, Image imageIcon, boolean containsVariable) {
		this.name = name;
		this.imageIcon = imageIcon;
		this.containsVariable = containsVariable;
	}

	public final String getName() {
		return name;
	}

	public final Image getImage() {
		return imageIcon;
	}

	public final boolean isContainsVariable() {
		return containsVariable;
	}

	/**
	 * parse {@link ActivityType} for Oracle 10g SOA SUITE
	 * 
	 * @param name
	 * @return
	 */
	public static final ActivityType parseOra10gBpelActivityType(String name) {
		for (ActivityType activtyType : ORA10G_BPEL) {
			if (activtyType.getName().equals(name)) {
				return activtyType;
			}
		}
		return parseNeutralTypes(name);
	}

	/**
	 * parse {@link ActivityType} for Open ESB - BPEL
	 * 
	 * @param name
	 * @return
	 */
	public static final ActivityType parseOpenEsbBpelActivtyType(String name) {
		for (ActivityType activityType : OPEN_ESB_BPEL) {
			if (activityType.getName().equals(name)) {
				return activityType;
			}
		}
		return parseNeutralTypes(name);
	}

	private static final ActivityType parseNeutralTypes(String name) {
		if (PARTNERLINKS.getName().equals(name)) {
			return PARTNERLINKS;
		} else if (VARIABLES.getName().equals(name)) {
			return VARIABLES;
		}
		return null;
	}

}
