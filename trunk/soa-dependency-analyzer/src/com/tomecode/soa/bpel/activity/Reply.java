package com.tomecode.soa.bpel.activity;

/**
 * reply activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Reply extends Activity {

	private static final long serialVersionUID = 5210081469434718236L;

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
	public Reply(ActivityType type, String name, String variable, String partnerLink, String operation) {
		super(type, name);
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

	public final boolean compare(Activity activity) {
		if (super.compare(activity)) {
			if (activity instanceof Reply) {
				Reply reply = (Reply) activity;
				if (partnerLink != null && reply.getPartnerLink() != null) {
					if (partnerLink.equals(reply.getPartnerLink())) {
						if (operation != null && reply.getOperation() != null) {
							return operation.equals(reply.getOperation());
						}
					}
				}
				if (operation != null && reply.getOperation() != null) {
					return operation.equals(reply.getOperation());
				}
			}
		}
		return false;
	}
}
