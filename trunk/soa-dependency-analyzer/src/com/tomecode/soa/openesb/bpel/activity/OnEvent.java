package com.tomecode.soa.openesb.bpel.activity;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;

/**
 * 
 * onEvenet element in Open ESB - BPEL process
 * 
 * @author Frastia Tomas
 * 
 */
public final class OnEvent extends Activity {

	private static final long serialVersionUID = -8702377407069055347L;

	private String partnerLink;
	private String operation;
	private String portType;
	private String variable;
	private String messageType;

	/**
	 * Constructor
	 * 
	 * @param partnerLink
	 * @param operation
	 * @param portType
	 * @param variable
	 * @param messageType
	 */
	public OnEvent(ActivityType type, String partnerLink, String operation, String portType, String variable, String messageType) {
		super(type, null);
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.portType = portType;
		this.variable = variable;
		this.messageType = messageType;
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
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	/**
	 * @return the messageType
	 */
	public final String getMessageType() {
		return messageType;
	}

}
