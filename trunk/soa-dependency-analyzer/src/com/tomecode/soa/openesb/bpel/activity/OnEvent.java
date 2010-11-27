package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * 
 * onEvenet element in Open ESB - BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
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
	public OnEvent(String partnerLink, String operation, String portType, String variable, String messageType) {
		super(ActivityType.OPEN_ESB_BPEL_ONEVENT, null);
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.portType = portType;
		this.variable = variable;
		this.messageType = messageType;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_ONEVENT;
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
