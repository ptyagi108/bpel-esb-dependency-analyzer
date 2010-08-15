package com.tomecode.soa.bpel.activity;

/**
 * 
 * 
 * Receive activity in BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Receive extends Activity {

	private static final long serialVersionUID = 1068491215667638987L;

	private String variable;

	private String partnerLink;

	private String operation;

	/**
	 * * Constructor
	 * 
	 * @param name
	 *            activity name
	 * @param variable
	 * @param partnerLink
	 * @param operation
	 */
	public Receive(ActivityType type, String name, String variable, String partnerLink, String operation) {
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

	//
	// /**
	// * find partnerLink in activity
	// */
	// public final void findPartnerLink(FindUsagePartnerLinkResult usage) {
	// if (partnerLink != null &&
	// usage.getPartnerLink().getName().equals(partnerLink)) {
	// usage.addUsage(this);
	// }
	// }

	public final boolean compare(Activity activity) {
		if (super.compare(activity)) {
			if (activity instanceof Receive) {
				Receive receive = (Receive) activity;
				if (partnerLink != null && receive.getPartnerLink() != null) {
					if (partnerLink.equals(receive.getPartnerLink())) {
						if (operation != null && receive.getOperation() != null) {
							return operation.equals(receive.getOperation());
						}
					}
				}
				if (operation != null && receive.getOperation() != null) {
					return operation.equals(receive.getOperation());
				}
			}
		}
		return false;
	}
}
