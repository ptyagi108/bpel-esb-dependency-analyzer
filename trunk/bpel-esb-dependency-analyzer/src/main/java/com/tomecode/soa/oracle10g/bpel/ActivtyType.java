package com.tomecode.soa.oracle10g.bpel;

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

	SCOPE("scope", IconFactory.SCOPE), SEQUENCE("sequence", IconFactory.SEQUENCE), ASSIGN("assign", IconFactory.ASSIGN), EMPTY("empty", IconFactory.EMPTY), RECEIVE("receive", IconFactory.RECEIVE), INVOKE("invoke", IconFactory.INVOKE), REPLY("reply", IconFactory.REPLY), SWITCH(
			"switch", IconFactory.SWITCH), CATCH("catch", IconFactory.CATCH), CATCHALL("catchAll", IconFactory.CATCHALL), ONALARM("onAlarm", IconFactory.ONALARM), ONMESSAGE("onMessage", IconFactory.ONMESSAGE), COMPENSATIONHANDLER("compensationHandler",
			IconFactory.COMPENSATIONHANDLER), JAVA_EMBEDDING("exec", IconFactory.BPELX_EXEC), PICK("pick", IconFactory.PICK), FLOW("flow", IconFactory.FLOW), FLOWN("flowN", IconFactory.FLOWN), COMPENSATE("compensate", IconFactory.COMPENSTATE), TERMINATE("terminate",
			IconFactory.TERMINATE),	THROW("throw", IconFactory.THROW);

	private final String name;
	private final ImageIcon imageIcon;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            xml element name
	 * @param imageIcon
	 *            icon
	 */
	private ActivtyType(String name, ImageIcon imageIcon) {
		this.name = name;
		this.imageIcon = imageIcon;
	}

	public final String getName() {
		return name;
	}

	public final ImageIcon getImageIcon() {
		return imageIcon;
	}

	public static final ActivtyType parseActivtyType(String name) {
		for (ActivtyType activtyType : values()) {
			if (activtyType.getName().equals(name)) {
				return activtyType;
			}
		}
		return null;
	}
}
