package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;

/**
 * 
 * 
 * onMessage activity in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class OnMessage extends Activity {

	private static final long serialVersionUID = 7676149667351176086L;

	private String variable;
	private String partnerLink;
	private String operation;

	private String portType;

	private String messageExchange;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 * @param portType
	 * @param messageExchange
	 */
	public OnMessage(ActivityType type, String variable, String partnerLink, String operation, String portType, String messageExchange) {
		super(type, null);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.portType = portType;
		this.messageExchange = messageExchange;
	}

	/**
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	/**
	 * @return the partnerLink
	 */
	public final String getPartnerLink() {
		return partnerLink;
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	/**
	 * @return the portType
	 */
	public final String getPortType() {
		return portType;
	}

	/**
	 * @return the messageExchange
	 */
	public final String getMessageExchange() {
		return messageExchange;
	}

}
