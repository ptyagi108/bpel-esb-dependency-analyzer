package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: reply in SplitJoin flow
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Reply extends OsbActivity {

	private String partnerLink;
	private String operation;
	private String variable;

	/**
	 * Constructor
	 * 
	 * @param partnerLink
	 * @param operation
	 * @param variable
	 */
	public Reply(String partnerLink, String operation, String variable) {
		super();
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.variable = variable;
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
	 * @return the variable
	 */
	public final String getVariable() {
		return variable;
	}

	public final String toString() {
		return "Reply";
	}

	@Override
	public Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_REPLY;
	}

}
