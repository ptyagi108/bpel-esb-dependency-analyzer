package com.tomecode.soa.oracle10g.bpel.activity;

/**
 * reply activity in bpel process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Reply extends Activity {

	private String variable;

	private String partnerLink;

	private String operation;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 */
	public Reply(String name, String variable, String partnerLink, String operation) {
		super(ActivityType.REPLY, name);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
	}

	public final String getOperation() {
		return operation;
	}

	public final String getPartnerLink() {
		return partnerLink;
	}

	public final String getVariable() {
		return variable;
	}

	public final String toString() {
		return (name != null ? name : super.toString());
	}
}
