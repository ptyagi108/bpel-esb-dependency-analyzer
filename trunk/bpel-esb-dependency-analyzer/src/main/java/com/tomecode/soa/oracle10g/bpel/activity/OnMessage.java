package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * OnMessage actvity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class OnMessage extends Activity {

	private String variable;
	private String partnerLink;
	private String operation;

	private String headerVariable;

	/**
	 * * Constructor
	 * 
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 * @param headerVariable
	 */
	public OnMessage(String variable, String partnerLink, String operation, String headerVariable) {
		super(ActivtyType.ONMESSAGE, null);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
		this.headerVariable = headerVariable;
	}

	public final String getHeaderVariable() {
		return headerVariable;
	}

	public final String getVariable() {
		return variable;
	}

	public final String getPartnerLink() {
		return partnerLink;
	}

	public final String getOperation() {
		return operation;
	}

}
