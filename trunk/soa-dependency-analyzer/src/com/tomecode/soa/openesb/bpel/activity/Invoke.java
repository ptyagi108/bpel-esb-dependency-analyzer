package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * Invoke BPEL activity
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Invoke extends Activity {

	private static final long serialVersionUID = 4576862167470771828L;

	private String inputVariable;

	private String outputVariable;

	private String partnerLink;

	private String operation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param inputVariable
	 * @param outputVariable
	 * @param partnerLink
	 * @param operation
	 */
	public Invoke(String name, String inputVariable, String outputVariable, String partnerLink, String operation) {
		super(ActivityType.OPEN_ESB_BPEL_INVOKE, name);
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.partnerLink = partnerLink;
		this.operation = operation;
	}

	public final String getOperation() {
		return operation;
	}

	public final String getPartnerLink() {
		return partnerLink;
	}

	public final String getInputVariable() {
		return inputVariable;
	}

	public final String getOutputVariable() {
		return outputVariable;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_INVOKE;
	}

}