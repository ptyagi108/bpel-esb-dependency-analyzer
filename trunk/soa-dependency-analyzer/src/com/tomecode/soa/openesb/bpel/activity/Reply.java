package com.tomecode.soa.openesb.bpel.activity;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.ActivityType;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * reply activity in BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
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
	public Reply(String name, String variable, String partnerLink, String operation) {
		super(ActivityType.OPEN_ESB_BPEL_REPLY, name);
		this.variable = variable;
		this.partnerLink = partnerLink;
		this.operation = operation;
	}

	public final Image getImage() {
		return ImageFactory.OPEN_ESB_BPEL_REPLY;
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
