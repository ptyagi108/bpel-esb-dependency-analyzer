package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * Invoke bpel activity
 * 
 * @author Tomas Frastia
 * 
 */
public final class Invoke extends Activity {

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
		super(ActivityType.INVOKE, name);
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

	public final String toString() {
		return (name != null ? name : super.toString());
	}

	public boolean abstractHasVariable(Variable variable) {
		return false;
	}
}
