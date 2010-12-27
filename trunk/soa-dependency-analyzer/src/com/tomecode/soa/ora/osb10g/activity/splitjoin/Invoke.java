package com.tomecode.soa.ora.osb10g.activity.splitjoin;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;

/**
 * element: invoke in SplitJoin flow
 * 
 * @author Tomas Frastia
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
public final class Invoke extends OsbActivity {

	private String operation;
	private String partnerLink;
	private String inputVariable;
	private String outputVariable;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param operation
	 * @param partnerLink
	 * @param inputVariable
	 * @param outputVariable
	 */
	public Invoke(String name, String operation, String partnerLink, String inputVariable, String outputVariable) {
		super(name, null);
		this.operation = operation;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
	}

	/**
	 * @return the operation
	 */
	public final String getOperation() {
		return operation;
	}

	/**
	 * @return the partnerLink
	 */
	public final String getPartnerLink() {
		return partnerLink;
	}

	/**
	 * @return the inputVariable
	 */
	public final String getInputVariable() {
		return inputVariable;
	}

	/**
	 * @return the outputVariable
	 */
	public final String getOutputVariable() {
		return outputVariable;
	}

	public final String toString() {
		return "Invoke Service";
	}

	@Override
	public final Image getImage() {
		return ImageFactory.OSB_10G_SPLITJOIN_INVOKE;
	}

}
